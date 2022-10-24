package com.example.foodloot.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data implements Parcelable {
    String Type;

    String Note;
    String Date;
    String id;
    List<String> Items;

    public Data()
    {

    }

    public Data(String type, int amount, String note, String date, String id,List<String> items) {
        this.Type = type;

        this.Note = note;
        this.Date = date;
        this.id = id;
        this.Items = items;
    }

    protected Data(Parcel in) {
        Type = in.readString();
        Note = in.readString();
        Date = in.readString();
        id = in.readString();
        Items = in.createStringArrayList();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }



    public List<String> getItems() {
        return Items;
    }

    public void setItems(List<String> items) {
        Items = items;
    }



    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> myObjectAsDict = new HashMap<>();
        myObjectAsDict.put("Type",this.Type);
        myObjectAsDict.put("Note",this.Note);
        myObjectAsDict.put("Date",this.Date);
        myObjectAsDict.put("ID",this.id);
        myObjectAsDict.put("Items",this.Items);
        return myObjectAsDict;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Type);
        parcel.writeString(Note);
        parcel.writeString(Date);
        parcel.writeString(id);
        parcel.writeStringList(Items);
    }
}
