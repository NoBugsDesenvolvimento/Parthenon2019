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
        LatLng DER = new LatLng(-20.761281, -42.866488);
        mMap.addMarker(new MarkerOptions().position(DER).title("DER - Departamento de Economia Rural"));
        LatLng DEF = new LatLng(-20.759550, -42.868441);
        mMap.addMarker(new MarkerOptions().position(DEF).title("DEF - Departamento de Engenharia Florestal"));
        LatLng DAU = new LatLng(-20.764469, -42.870461);
        mMap.addMarker(new MarkerOptions().position(DAU).title("DAU - Departamento de Arquitetura e Urbanismo"));
        LatLng DBB = new LatLng(-20.757771, -42.871962);
        mMap.addMarker(new MarkerOptions().position(DBB).title("DBB - Departamento de Bioquímica e Biologia"));
        LatLng DTAIII = new LatLng(-20.761156, -42.865050);
        mMap.addMarker(new MarkerOptions().position(DTAIII).title("DTA3 - Departamento de Tecnologia de Alimentos 3."));
        LatLng Dbv = new LatLng(-20.756947, -42.873145);
        mMap.addMarker(new MarkerOptions().position(Dbv).title("DBV - Horto Botânico"));
        LatLng Deco = new LatLng(-20.759391, -42.868276);
        mMap.addMarker(new MarkerOptions().position(Deco).title(" Departamento de Economia."));
        LatLng Dartes = new LatLng(-20.760622, -42.863746);
        mMap.addMarker(new MarkerOptions().position(Dartes).title("Departamento de Artes e Humanidades."));
        LatLng Direito = new LatLng(-20.760799, -42.864063);
        mMap.addMarker(new MarkerOptions().position(Direito).title("Departamento de Direito"));
        LatLng DEDFIS = new LatLng(-20.767189, -42.862215);
        mMap.addMarker(new MarkerOptions().position(DEDFIS).title("Departamento de Educação Física"));
        LatLng DMEDENF = new LatLng(-20.762208, -42.860545);
        mMap.addMarker(new MarkerOptions().position(DMEDENF).title("Departamento de Medicina e Enfermagem."));
        LatLng DEA = new LatLng(-20.770720, -42.872161);
        mMap.addMarker(new MarkerOptions().position(DEA).title("DEA - Departamento de Engenharia Agrícola e Florestal."));
        LatLng labenge = new LatLng(-20.765343, -42.868417);
        mMap.addMarker(new MarkerOptions().position(labenge).title("Labenge - Laboratório das Engenharias"));
        LatLng labAgrimensura = new LatLng(-20.764529, -42.869367);
        mMap.addMarker(new MarkerOptions().position(labAgrimensura).title("Laboratório de Engenharia de Agrimensura"));
        LatLng labengproducao = new LatLng(-20.760762, -42.865149);
        mMap.addMarker(new MarkerOptions().position(labengproducao).title("Laboratório da Engenharia de Produção"));
        LatLng DepQuim = new LatLng(-20.764121, -42.867534);
        mMap.addMarker(new MarkerOptions().position(DepQuim).title("Departamento de Engenharia Química"));
        LatLng edgeohis = new LatLng(-20.761454, -42.864400);
        mMap.addMarker(new MarkerOptions().position(edgeohis).title("Edificio de Geografia e História."));
        LatLng DLA = new LatLng(-20.760686, -42.864640);
        mMap.addMarker(new MarkerOptions().position(DLA).title("DLA - Departamento de Letras"));
        LatLng DED = new LatLng(-20.759722, -42.869565);
        mMap.addMarker(new MarkerOptions().position(DED).title("DED - Departamento de Economia Rural"));
        LatLng DZO = new LatLng(-20.766831, -42.860326);
        mMap.addMarker(new MarkerOptions().position(DZO).title("DZO - Departamento de Zootecnia"));
        LatLng DFIT = new LatLng(-20.758601, -42.871213);
        mMap.addMarker(new MarkerOptions().position(DFIT).title("Departamento de Fititotecnia"));


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
