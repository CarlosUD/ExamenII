package com.itca.eval_practica_ii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class update extends AppCompatActivity {

    public String x;
    public EditText v;
    public EditText titulo, desc, autor;

    ConexionSQLite conexion = new ConexionSQLite(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        titulo = findViewById(R.id.txtTitle);
        desc = findViewById(R.id.txtDescription);
        autor = findViewById(R.id.txtAutor);
        Bundle bundle = new Bundle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String dato = getIntent().getStringExtra("valorTitle");

        v = findViewById(R.id.txtTitle);
        v.setText(dato);

        x = v.getText().toString();


        try {
            SQLiteDatabase bd = conexion.getWritableDatabase();

            Cursor fila = bd.rawQuery("select descripcion, autor from tb_bloc where titulo = '" + x + "'"  , null);
            if(fila.moveToFirst()) {
                desc.setText(fila.getString(0));
                autor.setText(fila.getString(1));
            }


        } catch (Exception e) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea Salir?")
                    .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(update.this, registerNotes.class);
                startActivity(intent);
                break;
            case R.id.action_settings3:
                String a=titulo.getText().toString();
                String b=desc.getText().toString();
                String c=autor.getText().toString();
                if (a.isEmpty()) {
                    titulo.setError("Campo Obligatorio");
                    return false;
                }
                if (b.isEmpty()) {
                    desc.setError("Campo Obligatorio");
                    return false;
                }
                if (c.isEmpty()) {
                    autor.setError("Campo Obligatorio");
                    return false;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(update.this);
                builder.setTitle("Eliminar");
                builder.setIcon(R.drawable.delete);
                builder.setMessage("¿Quieres Eliminar la nota?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(update.this, registerNotes.class);
                                startActivity(intent);
                                finish();
                                SQLiteDatabase bd = conexion.getWritableDatabase();
                                String t = titulo.getText().toString();
                                int cant = bd.delete("tb_bloc", "titulo = '" + x + "'", null);
                                bd.close();
                                titulo.setText("");
                                desc.setText("");
                                autor.setText("");

                                Toast.makeText(update.this, "Eliminado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.action_settings4:
                String d=titulo.getText().toString();
                String e=desc.getText().toString();
                String f=autor.getText().toString();
                if (d.isEmpty()) {
                    titulo.setError("Campo Obligatorio");
                    return false;
                }
                if (e.isEmpty()) {
                    desc.setError("Campo Obligatorio");
                    return false;
                }
                if (f.isEmpty()) {
                    autor.setError("Campo Obligatorio");
                    return false;
                }
                AlertDialog.Builder builder1=new AlertDialog.Builder(update.this);
                builder1.setTitle("Actualizar");
                builder1.setIcon(R.drawable.rotate);
                builder1.setMessage("¿Quieres Actualizar la nota?")
                        .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(update.this, registerNotes.class);
                                startActivity(intent);
                                finish();
                                        SQLiteDatabase bd = conexion.getWritableDatabase();
                                        String t = titulo.getText().toString();
                                        String d = desc.getText().toString();
                                        String a = autor.getText().toString();
                                        ContentValues registro = new ContentValues();
                                        registro.put("titulo", t);
                                        registro.put("descripcion", d);
                                        registro.put("autor", a);

                                        int cant = bd.update("tb_bloc", registro, "titulo = '" + x + "'", null);
                                        bd.close();
                                        if (cant == 1) {
                                            Toast.makeText(update.this, "Se ha Modificado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(update.this, "No se ha modiicado", Toast.LENGTH_SHORT).show();
                                        }
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
        return true;
    }
}