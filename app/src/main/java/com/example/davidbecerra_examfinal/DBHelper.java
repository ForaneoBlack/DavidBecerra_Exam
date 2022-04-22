package com.example.davidbecerra_examfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "bdDavid.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Clientes(Cedula TEXT primary key, nombres TEXT, apellidos TEXT," +
                "direccion TEXT, telefono TEXT, email TEXT, fechanacimiento TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Clientes");
    }
    public Boolean insertuserdata(String cedula,String nombres, String apellidos, String direccion, String telefono, String email, String fechanacimiento )
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cedula", cedula);
        contentValues.put("nombres",nombres);
        contentValues.put("apellidos", apellidos);
        contentValues.put("direccion", direccion);
        contentValues.put("telefono", telefono);
        contentValues.put("email", email);
        contentValues.put("fechanacimiento",fechanacimiento);
        long result=DB.insert("Clientes", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateuserdata(String cedula,String nombres,String apellidos, String direccion, String telefono, String email, String fechanacimiento )
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombres", nombres);
        contentValues.put("apellidos", apellidos);
        contentValues.put("direccion", direccion);
        contentValues.put("telefono", telefono);
        contentValues.put("email", email);
        contentValues.put("fechanacimiento", fechanacimiento);
        Cursor cursor = DB.rawQuery("Select * from Clientes where cedula = ?", new String[]{cedula});
        if (cursor.getCount() > 0) {
            long result = DB.update("Clientes", contentValues, "cedula=?", new String[]{cedula});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Boolean deletedata (String cedula)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Clientes where cedula = ?", new String[]{cedula});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Clientes", "cedula=?", new String[]{cedula});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Cursor query() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Clientes", null);
        return cursor;
    }
}
