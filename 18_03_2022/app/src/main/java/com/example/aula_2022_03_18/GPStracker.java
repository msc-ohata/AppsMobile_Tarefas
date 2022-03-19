package com.example.aula_2022_03_18;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GPStracker implements LocationListener {

    Context context;

    //Método que utiliza a variável context
    public GPStracker(Context c){
        context = c;
    }

    //Método que busca pegar a localização com permissão
    public Location getLocation(){

        //Se a permissão para acessar a localização for negada, ele retorna nulo
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            //Um texto informando sobre a permissão negada
            Toast.makeText(context, "Não foi permitir", Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        //Se o GPS habilitado, pede a atualização sobre a localização e retorna o posicionamento
        if (isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }

        else {
            //Texto que pede o GPS ligado
            Toast.makeText(context, "Por favor, habitar o GPS!", Toast.LENGTH_LONG).show();
        }
        //Depois desse processo, ele retorna nulo
        return null;
    }

    //Sobrescreve para o método poder funcionar com o provedor desabilitado
    @Override
    public void onProviderDisabled(@NonNull String provider) {    }
    //Sobrescreve para o método poder funcionar com a localização alterada
    @Override
    public void onLocationChanged(@NonNull Location location) {    }
    //Sobrescreve para o método poder funcionar com o status alterado
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {    }
}

