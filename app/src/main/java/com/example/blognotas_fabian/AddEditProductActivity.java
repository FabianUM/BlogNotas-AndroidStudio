package com.example.blognotas_fabian;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.blognotas_fabian.dao.ProductoDAO;
import com.example.blognotas_fabian.dto.Producto;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditProductActivity extends AppCompatActivity {

    private TextInputLayout txtNombre, txtDescripcion;
    private ProductoDAO productoDAO;
    private Producto producto;
    private boolean isEditMode = false;
    private Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);

        try {
            txtNombre = findViewById(R.id.txtNombre);
            txtDescripcion = findViewById(R.id.txtDescripcion);
            Button btnGuardar = findViewById(R.id.btnGuardar);
            btnEliminar = findViewById(R.id.btnEliminar);

            productoDAO = new ProductoDAO(this);

            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("codigo")) {
                isEditMode = true;
                producto = new Producto();
                producto.setCodigo(intent.getIntExtra("codigo", 0));
                producto.setNombre(intent.getStringExtra("nombre"));
                producto.setDescripcion(intent.getStringExtra("descripcion"));

                txtNombre.getEditText().setText(producto.getNombre());
                txtDescripcion.getEditText().setText(producto.getDescripcion());

                btnGuardar.setText("Actualizar");
                btnEliminar.setVisibility(View.VISIBLE); // Mostrar botón eliminar si es modo edición
            }

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guardarProducto();
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eliminarProducto();
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", "Error en onCreate: " + e.getMessage(), e);
        }
    }

    private void guardarProducto() {
        try {
            String nombre = txtNombre.getEditText().getText().toString();
            String descripcion = txtDescripcion.getEditText().getText().toString();

            if (isEditMode) {
                producto.setNombre(nombre);
                producto.setDescripcion(descripcion);
                productoDAO.update(producto);
            } else {
                producto = new Producto();
                producto.setNombre(nombre);
                producto.setDescripcion(descripcion);
                productoDAO.insert(producto);
            }

            Intent intent = new Intent(AddEditProductActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e("ERROR", "Error en guardarProducto: " + e.getMessage(), e);
            e.printStackTrace();  // Para asegurarnos de ver todos los detalles del error
        }
    }


    private void eliminarProducto() {
        try {
            if (isEditMode) {
                productoDAO.delete(producto);
            }

            Intent intent = new Intent(AddEditProductActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e("ERROR", "Error en eliminarProducto: " + e.getMessage(), e);
        }
    }
}
