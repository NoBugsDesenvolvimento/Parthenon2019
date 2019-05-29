package com.nobugs.parthenon.helper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nobugs.parthenon.model.Usuários.Usuario;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual() {
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

    public static void atualizarNomeUsuario(String nome) {

        try{
            FirebaseUser user = getUsuarioAtual();

            UserProfileChangeRequest perfil = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();
            user.updateProfile(perfil).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar nome. Tente novamente.");

                    } }}); }

        catch (Exception e){
            e.printStackTrace(); }
    }

    public static void atualizarFotoUsuario(Uri uri) {

        try{
            FirebaseUser user = getUsuarioAtual();

            UserProfileChangeRequest perfil = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
            user.updateProfile(perfil).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar foto. Tente novamente.");
                    } }}); }
        catch (Exception e){
            e.printStackTrace(); }
    }

    public static Usuario getDadosUsuarioLogado() {

        FirebaseUser firebaseUser = getUsuarioAtual();
        Usuario usuario = new Usuario();
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(firebaseUser.getDisplayName());
        usuario.setId(firebaseUser.getUid());
        usuario.setNomePesquisa(firebaseUser.getDisplayName().toUpperCase());

        if ( firebaseUser.getPhotoUrl() == null ){
            usuario.setCaminhoFoto("");
        }else{
            usuario.setCaminhoFoto( firebaseUser.getPhotoUrl().toString() );
        }

        return usuario;

    }

}
