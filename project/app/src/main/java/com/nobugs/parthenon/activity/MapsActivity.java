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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Permissoes.checarPermissao(permissoes, this, 0);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                LatLng pessoa = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(pessoa).title("Você.").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pessoa, 18));     }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {   }
            @Override
            public void onProviderEnabled(String s) {   }
            @Override
            public void onProviderDisabled(String s) {      }       }; //fim do location listener

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 8000, 10, locationListener); }


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permResult: grantResults){
            if (permResult == PackageManager.PERMISSION_DENIED){
                alertaPermissao();      }       }     } //fim do método para chegagem de permissão

    public void alertaPermissao(){ // criação do alerta de ausência de permissão

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissão negada");
        builder.setMessage("Para utilizar o mapa é necessário fornecer as permissões exigidas.");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish(); }
        }); //fim dos métodos do builder
        AlertDialog mensagem = builder.create();
        mensagem.show();    } // fim do alerta de permissão

}
