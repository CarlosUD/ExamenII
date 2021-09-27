package com.itca.eval_practica_ii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText titulo, desc, autor;
    ConexionSQLite conexion = new ConexionSQLite(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titulo = findViewById(R.id.txtTitle);
        desc = findViewById(R.id.txtDescription);
        autor = findViewById(R.id.txtAutor);

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
        getMenuInflater().inflate(R.menu.menu_main1,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()){

          case R.id.action_settings1:
              Intent it = new Intent(this, registerNotes.class);
              startActivity(it);
              finish();
              break;
              case R.id.action_settings2:
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
                  AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                  builder.setIcon(R.drawable.ic_add);
                  builder.setTitle("Guardar");
                  builder.setMessage("En este momento se gardara la Información")
                          .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  Intent intent = new Intent(MainActivity.this, registerNotes.class);
                                  startActivity(intent);
                                  finish();

                                  try {
                                      SQLiteDatabase bd = conexion.getWritableDatabase();
                                      String t = titulo.getText().toString();
                                      String d = desc.getText().toString();
                                      String a = autor.getText().toString();
                                      ContentValues registro = new ContentValues();
                                      registro.put("id", (Integer) null);
                                      registro.put("titulo", t);
                                      registro.put("descripcion", d);
                                      registro.put("autor", a);
                                      int result = (int) bd.insert("tb_bloc", null, registro);
                                      bd.close();
                                      if (result > 0) {
                                          Toast.makeText(MainActivity.this, "Se guardo el registro", Toast.LENGTH_SHORT).show();
                                          titulo.setText("");
                                          desc.setText("");
                                          autor.setText("");
                                      } else {
                                          Toast.makeText(MainActivity.this, "No Se guardo el registro", Toast.LENGTH_SHORT).show();
                                      }
                                  } catch (Exception e) {
                                      String msg = e.toString();
                                      Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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