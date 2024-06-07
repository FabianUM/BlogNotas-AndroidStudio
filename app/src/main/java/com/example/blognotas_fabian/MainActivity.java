package com.example.blognotas_fabian;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.blognotas_fabian.dao.ProductoDAO;
import com.example.blognotas_fabian.dto.Producto;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView listado;
    ProductoDAO productoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productoDAO = new ProductoDAO(this);

        listado = findViewById(R.id.listado);
        listado.setOnItemClickListener(this);
        listado.setOnItemLongClickListener(this);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditProductActivity.class);
                startActivity(intent);
            }
        });

        listar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listar();
    }

    private void listar() {
        List<Producto> lista = productoDAO.getList();
        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listado.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adp, View view, int position, long id) {
        Producto p = (Producto) adp.getAdapter().getItem(position);
        Intent intent = new Intent(MainActivity.this, AddEditProductActivity.class);
        intent.putExtra("codigo", p.getCodigo());
        intent.putExtra("nombre", p.getNombre());
        intent.putExtra("descripcion", p.getDescripcion());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adp, View view, int position, long id) {
        Producto p = (Producto) adp.getAdapter().getItem(position);
        productoDAO.delete(p);
        listar();
        return true;
    }
}
