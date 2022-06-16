package com.usil.proyectomoviles.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.usil.proyectomoviles.R;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CambiarPerfil extends AppCompatActivity {
    ImageView imgFotoDePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_cambiar_perfil);
        asignarReferencia();
    }

    private void asignarReferencia() {
        imgFotoDePerfil = findViewById(R.id.imgFotoDePerfil);
    }

    public void tomarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            Uri uri = data.getData();
            imgFotoDePerfil.setImageURI(uri);
        }else if (requestCode==2 && resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imgFotoDePerfil.setImageBitmap(bitmap);
            try {
                FileOutputStream fileOutputStream = openFileOutput(crearNombreArchivoJPG(), Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String crearNombreArchivoJPG() {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fecha+".jpg";
    }

    public void seleccionarDeGaleria(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /*intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);*/
        startActivityForResult(intent, 1);
    }



    public void menuPrincipal(View view){
        Intent intent = new Intent(CambiarPerfil.this, Home.class);
        startActivity(intent);
    }
}