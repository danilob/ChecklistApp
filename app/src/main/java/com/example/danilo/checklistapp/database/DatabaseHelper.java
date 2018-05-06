package com.example.danilo.checklistapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danilo on 03/05/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String mDatabaseName = "banco.db";
    private static final int mVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, mDatabaseName, null, mVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ScriptDLL.getCreateTableChecklist());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
