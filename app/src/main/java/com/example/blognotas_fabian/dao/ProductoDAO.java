package com.example.blognotas_fabian.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.blognotas_fabian.dto.Producto;
import com.example.blognotas_fabian.interfaces.ConstantesApp;
import com.example.blognotas_fabian.servicios.ConectaDB;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private SQLiteDatabase db;

    public ProductoDAO(Context context) {
        this.db = new ConectaDB(context, ConstantesApp.BDD, null, ConstantesApp.VERSION).getWritableDatabase();
    }

    public String insert(Producto p) {
        String resp = "";
        ContentValues registro = new ContentValues();
        registro.put("nombre", p.getNombre());
        registro.put("descripcion", p.getDescripcion()); // Asegúrate de que esto esté correcto
        long reg = db.insert(ConstantesApp.TBL_PRODUCTO, null, registro);
        Log.i("INFOX", "" + reg);
        return resp;
    }

    public String update(Producto p) {
        String resp = "";
        ContentValues registro = new ContentValues();
        registro.put("nombre", p.getNombre());
        registro.put("descripcion", p.getDescripcion()); // Asegúrate de que esto esté correcto
        int reg = db.update(ConstantesApp.TBL_PRODUCTO, registro, "codigo = ?", new String[]{String.valueOf(p.getCodigo())});
        Log.i("INFOX", "" + reg);
        return resp;
    }

    public void delete(Producto p) {
        db.delete(ConstantesApp.TBL_PRODUCTO, "codigo = ?", new String[]{String.valueOf(p.getCodigo())});
    }

    public List<Producto> getList() {
        List<Producto> lista = new ArrayList<>();
        String cadSQL = "SELECT codigo, nombre, descripcion FROM " + ConstantesApp.TBL_PRODUCTO + " ORDER BY codigo DESC ";
        Cursor c = db.rawQuery(cadSQL, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Producto p = new Producto();
                    p.setCodigo(c.getInt(c.getColumnIndexOrThrow("codigo")));
                    p.setNombre(c.getString(c.getColumnIndexOrThrow("nombre")));
                    p.setDescripcion(c.getString(c.getColumnIndexOrThrow("descripcion"))); // Asegúrate de que esto esté correcto
                    lista.add(p);
                } while (c.moveToNext());
            }
            c.close();
        }
        return lista;
    }
}
