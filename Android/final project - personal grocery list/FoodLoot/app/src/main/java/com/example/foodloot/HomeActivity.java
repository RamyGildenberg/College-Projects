package com.example.foodloot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodloot.Model.Data;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab_btn;
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    private  List<Data> data;
    private  OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




        Query query = db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Shopping Lists").orderBy("Date");
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("tag", document.getId() + " => " + document.getData());
                }
            } else {
                Log.d("tag", "Error getting documents: ", task.getException());
            }
        });


        FirestoreRecyclerOptions<Data> response = new FirestoreRecyclerOptions.Builder<Data>()
                .setQuery(query, Data.class)
                .build();

        adapter=new FirestoreRecyclerAdapter<Data,MyViewHolder>(response) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position,@NonNull final Data model) {
                    holder.setDate(model.getDate());
                    Log.d("tag",""+model.getDate());
                    holder.setType(model.getType());
                    Log.d("tag",""+model.getType());
                    holder.setNote(model.getNote());
                    Log.d("tag",""+model.getNote());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(HomeActivity.this, "clicked on " +position, Toast.LENGTH_SHORT).show();
                            DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                            String listUid=snapshot.getId();
                            Log.d("ListUID tag",""+listUid);
                            Intent intent=new Intent(getApplicationContext(),ListActivity.class);
                            intent.putExtra("ListID",listUid);
                            intent.putExtra("UserID",mAuth.getCurrentUser().getUid());
                            intent.putExtra("ListName",(String)snapshot.get("Type"));
                            startActivity(intent);
                        }


                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                        @Override
                        public boolean onLongClick(View view) {
                            DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                            longClickDialogue(snapshot,position,holder);
                            adapter.notifyDataSetChanged();

                            return false;
                        }

                    });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_data, parent, false);


                return new MyViewHolder(view);
            }

        };

        setContentView(R.layout.activity_home);

        toolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shopping Lists");

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uId=mUser.getUid();


        recyclerView=findViewById(R.id.recycler_home);
        LinearLayoutManager layoutManager=new LinearLayoutManager((this));

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        fab_btn=findViewById(R.id.fab);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });
        setGreetingText();
    }

    private void setGreetingText() {
        TextView timeGreeting = findViewById(R.id.time_greeting);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour>= 12 && hour < 17){
            timeGreeting.setText("Good Afternoon");
        } else if(hour >= 17 && hour < 21){
            timeGreeting.setText("Good Evening");
        } else if(hour >= 21 && hour < 24){
            timeGreeting.setText("Good Night");
        } else {
            timeGreeting.setText("Good Morning");
        }
    }

    private void customDialog(){
        AlertDialog.Builder myDialog=new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
        View myView=inflater.inflate(R.layout.input_data,null);
        AlertDialog dialog= myDialog.create();
        dialog.setView(myView);

        EditText type=myView.findViewById(R.id.edt_type);
        EditText amount=myView.findViewById(R.id.edt_amount);
        EditText note=myView.findViewById(R.id.edt_note);
        android.widget.Button btnCreate=myView.findViewById(R.id.btn_createList);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mType=type.getText().toString().trim();
                String mAmount=amount.getText().toString().trim();
                String mNote=note.getText().toString().trim();

                if(TextUtils.isEmpty(mType))
                {
                    type.setError("Required Field");
                    return;
                }
                if(TextUtils.isEmpty(mAmount)|| !mAmount.matches("[0-9]+")){
                    amount.setError("Required Field, must be a number");
                    return;
                }
                int amountInt = !mAmount.equals("")?Integer.parseInt(mAmount):0;

                String id= mAuth.getCurrentUser().getUid();
                String date= DateFormat.getDateInstance().format(new Date());
                HashMap<String,Object> dataHash=null;
                List<String> items=new ArrayList<>();
                Data data=new Data(mType,amountInt,mNote,date,id,items);

                dataHash= data.toHashMap();

                db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Shopping Lists").document().set(dataHash);
                Toast.makeText(getApplicationContext(), "List created",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    private void longClickDialogue(DocumentSnapshot snapshot,int position,MyViewHolder holder)
    {
        AlertDialog.Builder myDialog=new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
        View myView=inflater.inflate(R.layout.list_long_click_dialogue,null);
        AlertDialog dialog= myDialog.create();
        dialog.setView(myView);
        EditText listName=myView.findViewById(R.id.edt_nameText);
        android.widget.Button btnEditList=myView.findViewById(R.id.btn_editName);
        android.widget.Button btndeleteList=myView.findViewById(R.id.btn_delete);
        btnEditList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mListName=listName.getText().toString().trim();


                if(TextUtils.isEmpty(mListName))
                {
                    listName.setError("Required Field");
                    return;
                }
                String listUid=snapshot.getId();
                String name=(String)snapshot.get("Type");

                db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Shopping Lists").document(listUid).update("Type",mListName);
                Toast.makeText(getApplicationContext(), "List updated",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btndeleteList.setOnClickListener(new View.OnClickListener() {
            String listUid=snapshot.getId();
            @Override
            public void onClick(View view) {
                db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Shopping Lists").document(listUid).delete();
                Toast.makeText(getApplicationContext(), "List deleted",Toast.LENGTH_SHORT).show();

                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onStart()
    {

        super.onStart();
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();

    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        View myview;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            myview=itemView;
        }
        public void setType(String type){
            TextView myType=myview.findViewById(R.id.type);
            myType.setText(type);
        }
        public void setNote(String note){
            TextView myNote=myview.findViewById(R.id.note);
            myNote.setText(note);
        }
        public void setDate(String date){
            TextView myDate=myview.findViewById(R.id.date);
            myDate.setText(date);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Data data);
    }


}

