package com.example.aula_2022_03_18;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Criando duas variaveis para imagem e botões
    private ImageView imageViewFoto;
    private Button btnGeo;

    //Cria visualização do app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variavel buscando a id do botão do xml e requirindo a permissão da localização
        btnGeo = (Button) findViewById(R.id.btn_gps);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        //Ação ao clicar no botão
        btnGeo.setOnClickListener(new View.OnClickListener() {
            //Utilizando outra classe e pegando a localização
            @Override
            public void onClick(View view) {
                GPStracker g = new GPStracker(getApplication());
                Location l = g.getLocation();
                //Se I estiver vazio, ele vai pegar a latitude e a longitude e mostrará ao usuário
                if(l != null) {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LATITUDE: " + lat +"\n LONGITUDE: " + lon, Toast.LENGTH_LONG).show();
                }
            }
        });
        //Utilizando uma superclasse para checar a permissão da câmera no manifest
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }

        //Guarda a foto ao clicar no botão
        imageViewFoto = (ImageView) findViewById(R.id.image_foto);
        findViewById(R.id.btn_pic).setOnClickListener(new View.OnClickListener() {
            //Utilizando outro método
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });
    }

    //Permissão para capturar a imagem com intenção de um resultado
    private void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
    //Pegando um método de uma superclasse
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Se tiver uma imagem e tudo estiver ok, pega essa imagem e mostra ela
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imageViewFoto.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}