package com.example.foodloot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodloot.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.example.foodloot.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.valueOf;

public class ListActivity extends AppCompatActivity {
    private List<String> items;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;
    private Toolbar toolbar;
    private EditText itemName;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String mItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        LayoutInflater inflater=LayoutInflater.from(ListActivity.this);
        View myView=inflater.inflate(R.layout.activity_list,null);




        String docUid= getIntent().getStringExtra("ListID");
        String userUid= getIntent().getStringExtra("UserID");
        Data data= (Data) getIntent().getSerializableExtra("List");


        //###########################################################################################################################################################
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Shopping Lists").document(docUid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {

                    listView = (ListView) findViewById(R.id.id_list_view);
                    DocumentSnapshot snapshot=task.getResult();

                    items=(ArrayList)snapshot.get("Items");
                    Log.d("items tag",""+items);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(),
                            R.layout.item_inside_list,
                            items );



                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();

                }
            }
        });
        toolbar=findViewById(R.id.list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("ListName"));



        items=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(this,R.layout.list_view_layout,items);
        listView= findViewById(R.id.id_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int i ,long l){
                TextView textView = (TextView) view;
                if(!textView.getPaint().isStrikeThruText()){
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else{
                    textView.setPaintFlags(textView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                longClickDialogue(i);
                arrayAdapter.notifyDataSetChanged();

                return false;
            }
        });

        listView.setAdapter(arrayAdapter);
        itemName=findViewById(R.id.item_name);
        Button add_btn=findViewById(R.id.add_item);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(itemName.getText().toString().trim()))
                {
                    itemName.setError("Required Field");
                    return;
                }

                items.add(itemName.getText().toString().trim());


                itemName.setText("");

                db.collection("Users").document(userUid).collection("Shopping Lists").document(docUid).update("Items",items);

                arrayAdapter.addAll(items);
                arrayAdapter.notifyDataSetChanged();
                /*arrayAdapter.clear();
                arrayAdapter.addAll(items);
                arrayAdapter.notifyDataSetChanged();
                listView.requestLayout();*/
            }
        });
    }
    private HashMap<String, Object> toHashMap(List list) {
        HashMap<String, Object> map = new HashMap<>();
        for(Object i:list) map.put(valueOf(list.indexOf(i)),i);
        return map;
    }
    private void longClickDialogue(int position)
    {
        AlertDialog.Builder myDialog=new AlertDialog.Builder(ListActivity.this);
        LayoutInflater inflater=LayoutInflater.from(ListActivity.this);
        View myView=inflater.inflate(R.layout.list_long_click_dialogue,null);
        AlertDialog dialog= myDialog.create();
        dialog.setView(myView);
        itemName=myView.findViewById(R.id.edt_nameText);
        android.widget.Button btnEditList=myView.findViewById(R.id.btn_editName);
        android.widget.Button btnDeleteList=myView.findViewById(R.id.btn_delete);
        btnEditList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(TextUtils.isEmpty(itemName.getText().toString().trim()))
                {
                    itemName.setError("Required Field");
                    return;
                }

                String listUid=getIntent().getStringExtra("ListID");

                items.set(position,(itemName.getText().toString().trim()));

                db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Shopping Lists").document(listUid).update("Items",items);


                Toast.makeText(getApplicationContext(), "Item updated",Toast.LENGTH_SHORT).show();
                arrayAdapter.addAll(items);
                arrayAdapter.notifyDataSetChanged();
                /*adapter.clear();
                adapter.addAll(items);
                adapter.notifyDataSetChanged();
                listView.requestLayout();*/

                dialog.dismiss();
            }
        });
        btnDeleteList.setOnClickListener(new View.OnClickListener() {
            String listUid=getIntent().getStringExtra("ListID");
            @Override
            public void onClick(View view) {
                items.remove(position);
                db.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Shopping Lists").document(listUid).update("Items",items);

                Toast.makeText(getApplicationContext(), "Item deleted",Toast.LENGTH_SHORT).show();
                arrayAdapter.addAll(items);
                arrayAdapter.notifyDataSetChanged();
                /*adapter.clear();
                adapter.addAll(items);
                arrayAdapter.notifyDataSetChanged();
                listView.requestLayout();*/


                dialog.dismiss();
            }
        });
        dialog.show();
    }

}