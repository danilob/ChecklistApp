package com.example.danilo.checklistapp.model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.danilo.checklistapp.database.ScriptDLL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilo on 03/05/18.
 */

public class ChecklistDAO {

    private SQLiteDatabase mConnection;

    public ChecklistDAO(SQLiteDatabase conection){

        mConnection = conection;
    }

    public void insert(Checklist chk){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Description",chk.getDescription());
        contentValues.put("Active",chk.isActive());

        mConnection.insertOrThrow("CHecklist",null, contentValues);
    }

    public void remove(int id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);

        mConnection.delete("Checklist","ID = ?",params);

    }

    public void alter(Checklist chk){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Description",chk.getDescription());
        contentValues.put("Active",chk.isActive());

        String[] params = new String[1];
        params[0] = String.valueOf(chk.getId());

        mConnection.update("Checklist",contentValues,"ID = ?",params);

    }

    public List<Checklist> listChecklists(){
        List<Checklist> checklists = new ArrayList<Checklist>();
        //mConnection.q
        Cursor result = mConnection.rawQuery(ScriptDLL.getChecklists(),null);



        if(result.getCount()>0){
            result.moveToFirst();
            do{
                Checklist chk = new Checklist();
                chk.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                chk.setDescription(result.getString(result.getColumnIndexOrThrow("Description")));
                boolean value = result.getInt(result.getColumnIndexOrThrow("Active")) > 0;
                chk.setActive(value);

                checklists.add(chk);

            }while(result.moveToNext());

        }

        return checklists;
    }

    public Checklist getChecklist(int id){

        Checklist chk = new Checklist();

        String[] params = new String[1];
        params[0] = String.valueOf(id);

        Cursor result = mConnection.rawQuery(ScriptDLL.getChecklist(),params);

        if(result.getCount()>0){
            result.moveToFirst();
            chk.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
            chk.setDescription(result.getString(result.getColumnIndexOrThrow("Description")));
            boolean value = result.getInt(result.getColumnIndexOrThrow("Active")) > 0;
            chk.setActive(value);
            return chk;
        }
        return null;
    }

}
