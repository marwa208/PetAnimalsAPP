package com.example.marwa.petanimalsapp;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.marwa.petanimalsapp.data.PetContract;


public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView= view.findViewById(R.id.name);
        TextView textView1=view.findViewById(R.id.summary);
        int namecolumnindex= cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
        int breedcolumnindex=cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
        String petname=cursor.getString(namecolumnindex);
        String petbreed=cursor.getString(breedcolumnindex);
        if (TextUtils.isEmpty(petbreed)) {
            petbreed = context.getString(R.string.unknown_breed);
        }

        textView.setText(petname);
        textView1.setText(petbreed);


    }
}
