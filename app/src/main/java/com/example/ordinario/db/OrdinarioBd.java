package com.example.ordinario.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class OrdinarioBd extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OrdinarioBD";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTOS = "productos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PRECIO = "precio";
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_FECHA = "fecha";

    public static final String TABLE_VENTAS = "ventas";
    public static final String COLUMN_ID_PRODUCTO = "idProducto";
    public static final String COLUMN_CANTIDAD_VENTA = "cantidad";
    public static final String COLUMN_PRECIO_VENTA = "precio";
    public static final String COLUMN_IMPORTE_VENTA = "importe";

    private static final String TABLE_CREATE_PRODUCTOS =
            "CREATE TABLE " + TABLE_PRODUCTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_PRECIO + " REAL, " +
                    COLUMN_CANTIDAD + " INTEGER, " +
                    COLUMN_IMAGEN + " TEXT, " +
                    COLUMN_FECHA + " TEXT " + ");";

    private static final String TABLE_CREATE_VENTAS =
            "CREATE TABLE " + TABLE_VENTAS + " (" +
                    COLUMN_ID_PRODUCTO + " INTEGER, " +
                    COLUMN_CANTIDAD_VENTA + " INTEGER, " +
                    COLUMN_PRECIO_VENTA + " REAL, " +
                    COLUMN_IMPORTE_VENTA + " REAL" + ");";

    public OrdinarioBd(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_PRODUCTOS);
        db.execSQL(TABLE_CREATE_VENTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENTAS);
        onCreate(db);
    }

    // Método para insertar un producto
    public long insertarProducto(String nombre, double precio, int cantidad, String imagen, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_IMAGEN, imagen);
        values.put(COLUMN_FECHA, fecha);
        return db.insert(TABLE_PRODUCTOS, null, values);
    }

    // Método para eliminar un producto
    public int eliminarProducto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PRODUCTOS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Método para actualizar un producto
    public int actualizarProducto(int id, double precio, int cantidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_CANTIDAD, cantidad);
        return db.update(TABLE_PRODUCTOS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTOS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO));
                int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));
                String imagen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA));

                Producto producto = new Producto(id, nombre, precio, cantidad, imagen, fecha);
                productos.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productos;
    }

    // Método para insertar una venta
    public void insertarVenta(int idProducto, int cantidad, double precio, double importe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PRODUCTO, idProducto);
        values.put(COLUMN_CANTIDAD_VENTA, cantidad);
        values.put(COLUMN_PRECIO_VENTA, precio);
        values.put(COLUMN_IMPORTE_VENTA, importe);
        db.insert(TABLE_VENTAS, null, values);
    }

    // Método para obtener todas las ventas
    public List<VentaItem> obtenerVentas() {
        List<VentaItem> ventas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VENTAS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idProducto = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_PRODUCTO));
                int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD_VENTA));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO_VENTA));
                double importe = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_IMPORTE_VENTA));

                VentaItem ventaItem = new VentaItem(idProducto, cantidad, precio, importe);
                ventas.add(ventaItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ventas;
    }

    // Método para obtener el total de ventas
    public double obtenerTotalVenta() {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_IMPORTE_VENTA + ") as total FROM " + TABLE_VENTAS, null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        }
        cursor.close();
        return total;
    }

    // Clase Producto
    public static class Producto {
        private int id;
        private String nombre;
        private double precio;
        private int cantidad;
        private String imagen;
        private String fecha;

        public Producto(int id, String nombre, double precio, int cantidad, String imagen, String fecha) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
            this.imagen = imagen;
            this.fecha = fecha;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }

        public int getCantidad() {
            return cantidad;
        }

        public String getImagen() {
            return imagen;
        }

        public String getFecha() {
            return fecha;
        }
    }

    // Clase VentaItem
    public static class VentaItem {
        private int idProducto;
        private int cantidad;
        private double precio;
        private double importe;

        public VentaItem(int idProducto, int cantidad, double precio, double importe) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
            this.precio = precio;
            this.importe = importe;
        }

        // Getters
        public int getIdProducto() {
            return idProducto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double getPrecio() {
            return precio;
        }

        public double getImporte() {
            return importe;
        }
    }
}
