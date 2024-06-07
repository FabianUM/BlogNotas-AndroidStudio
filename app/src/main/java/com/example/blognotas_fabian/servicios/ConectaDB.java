package com.example.blognotas_fabian.servicios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.blognotas_fabian.interfaces.ConstantesApp;

public class ConectaDB extends SQLiteOpenHelper {

    public ConectaDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstantesApp.DDL_PRODUCTO); // Crear la tabla producto
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConstantesApp.TBL_PRODUCTO);
        onCreate(db);
    }
}