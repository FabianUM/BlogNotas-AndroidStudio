package com.example.blognotas_fabian.interfaces;

public interface ConstantesApp {
    String DDL_PRODUCTO = "CREATE TABLE producto (\n" +
            "    codigo INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    nombre VARCHAR (40) UNIQUE,\n" +
            "    descripcion VARCHAR (900)\n" +
            ");\n";

    String TBL_PRODUCTO = "producto";
    String BDD = "negocio.db";
    int VERSION = 1; // Incrementa la versión de la base de datos
}
