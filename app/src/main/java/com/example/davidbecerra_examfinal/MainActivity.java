package com.example.davidbecerra_examfinal;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnCrear, btnActualizar, btnVer, btnEliminar;

    EditText txtCedula, txtNombre, txtApellidos, txtDireccion, txtTelefono, txtEmail, txtFechaNacimiento;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCrear=findViewById(R.id.btnCrear);
        btnEliminar =findViewById(R.id.btnEliminar);
        btnVer=findViewById(R.id.btnVer);
        btnActualizar=findViewById(R.id.btnActualizar);
        DB = new DBHelper(this);

        ImageButton imgButton = findViewById(R.id.buttonCamara);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intentCamara.resolveActivity(getPackageManager()) != null) {
                    Toast.makeText(getApplicationContext(),"camara Entrar",Toast.LENGTH_LONG).show();
                    startActivityForResult(intentCamara, 1);

                }
            }

        });

        txtCedula= findViewById(R.id.txtCedula);
        txtNombre= findViewById(R.id.txtNombre);
        txtApellidos= findViewById(R.id.txtApellidos);
        txtDireccion= findViewById(R.id.txtDireccion);
        txtTelefono= findViewById(R.id.txtTelefono);
        txtEmail= findViewById(R.id.txtEmail);
        txtFechaNacimiento= findViewById(R.id.txtFechaNacimiento);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cedula = txtCedula.getText().toString();
                String nombre = txtNombre.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String email = txtEmail.getText().toString();
                String fechanacimiento = txtFechaNacimiento.getText().toString();


                Boolean checkinsertdata = DB.insertuserdata(cedula,nombre, apellidos, direccion, telefono, email, fechanacimiento);
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "Nuevos Datos Ingresados", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Datos no Ingresados", Toast.LENGTH_SHORT).show();
            }        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cedula = txtCedula.getText().toString();
                String nombre = txtNombre.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String email = txtEmail.getText().toString();
                String fechanacimiento = txtFechaNacimiento.getText().toString();

                Boolean update = DB.updateuserdata(cedula,nombre, apellidos, direccion, telefono, email, fechanacimiento);
                if(update==true)
                    Toast.makeText(MainActivity.this, "Datos Actualizados", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Datos no Actualizados", Toast.LENGTH_SHORT).show();
            }        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cedula = txtCedula.getText().toString();
                Boolean delete = DB.deletedata(cedula);
                if(delete==true)
                    Toast.makeText(MainActivity.this, "Datos Eliminados", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Datos no eliminados", Toast.LENGTH_SHORT).show();
            }        });

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.query();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No existen datos", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("cedula :"+res.getString(0)+"\n");
                    buffer.append("Nombres :"+res.getString(1)+"\n");
                    buffer.append("apelldios :"+res.getString(2)+"\n");
                    buffer.append("direccion :"+res.getString(3)+"\n");
                    buffer.append("telefono :"+res.getString(4)+"\n");
                    buffer.append("email :"+res.getString(5)+"\n");
                    buffer.append("fechanacimiento :"+res.getString(6)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Datos de los Usuarios");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }
}