package com.nobugs.parthenon.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.Permissoes;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION    };
    private LocationManager locationManager;
    private LocationListener locationListener;
    private androidx.appcompat.app.AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Permissoes.checarPermissao(permissoes, this, 0);
        avisoEntrada();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        LatLng pvb = new LatLng(-20.763456, -42.866379);
        LatLng pva = new LatLng(-20.760406, -42.867570);
        LatLng cce = new LatLng(-20.764780, -42.868439);
        LatLng bbt = new LatLng(-20.761319, -42.867808);
        LatLng bernadao = new LatLng(-20.761820, -42.869289);
        LatLng noBugs = new LatLng(-20.762974, -42.866979);

        mMap.addMarker(new MarkerOptions().position(pvb).title("PVB - Pavilhão de Aulas B."));
        mMap.addMarker(new MarkerOptions().position(pva).title("PVA - Pavilhão de Aulas A."));
        mMap.addMarker(new MarkerOptions().position(cce).title("CCE - Centro de Ciências Exatas."));
        mMap.addMarker(new MarkerOptions().position(bbt).title("BBT - Biblioteca Central"));
        mMap.addMarker(new MarkerOptions().position(pvb).title("PVB - Pavilhão de Aulas B."));
        mMap.addMarker(new MarkerOptions().position(bernadao).title("Edificio Arthur Bernades."));
        mMap.addMarker(new MarkerOptions().position(noBugs).title("No Bugs - Empresa Júnior de Informática.").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(noBugs, 18));
    }


    private void avisoEntrada() {
        //Cria o gerador do AlertDialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Em Rede: Conheça a UFV.");

        builder.setMessage("É necessário saber algumas informações básicas sobre o Mapa. " +
                " \n Primeiro: Os marcadores vermelhos correspondem aos principais prédios da UFV cadastrados no evento." +
                " \n Segundo: O marcador azul corresponde a No Bugs." +
                " \n Terceiro: Caso esteja perdido, basta clicar em um marcador e ele revelará seu nome." +
                " \n Quinto: Ao clicar no marcador é possível abrir o Google Maps usando um de seus ícones.");
        builder.setPositiveButton("Entendido!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }



}
