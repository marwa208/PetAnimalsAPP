package com.example.marwa.petanimalsapp;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.support.design.widget.FloatingActionButton;

import android.support.v4.content.CursorLoader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;



import com.example.marwa.petanimalsapp.data.PetContract;
import com.example.marwa.petanimalsapp.data.PetDbHelper;

public class CatalogeActivity extends AppCompatActivity implements  android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PET_LOADER=0;
    PetCursorAdapter petCursorAdapter;
        FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cataloge);

        floatingActionButton=findViewById(R.id.floatbut);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CatalogeActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView emptylist=findViewById(R.id.list1);
        View view=findViewById(R.id.empty_view);
        emptylist.setEmptyView(view);

        getLoaderManager().initLoader(PET_LOADER,null,  this);
        petCursorAdapter=new PetCursorAdapter(this,null);


        emptylist.setAdapter(petCursorAdapter);
        emptylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(CatalogeActivity.this,EditorActivity.class);
              Uri uri= ContentUris.withAppendedId(PetContract.PetEntry.CONTENT_URI,id);
              intent.setData(uri);
              startActivity(intent);

            }
        });

    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete All Pets?");
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteAllPets();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PetContract.PetEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    public void Insert(){

        ContentValues contentValues= new ContentValues();
        contentValues.put(PetContract.PetEntry.COLUMN_PET_NAME,"toto");
        contentValues.put(PetContract.PetEntry.COLUMN_PET_BREED,"terrir");
        contentValues.put(PetContract.PetEntry.COLUMN_PET_WEIGHT,7);
        contentValues.put(PetContract.PetEntry.COLUMN_PET_GENDER,PetContract.PetEntry.GENDER_MALE);
        Uri newUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, contentValues);


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                Insert();


                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String Projection[] = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED

        };
        return new android.content.CursorLoader(this,PetContract.PetEntry.CONTENT_URI,Projection,null,null,null);
    }
    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        petCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        petCursorAdapter.swapCursor(null);
    }
}
