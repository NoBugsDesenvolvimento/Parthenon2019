package com.nobugs.parthenon.model.Usuários;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String caminhoFoto;
    private String nomePesquisa;
    private String CPF;
    private String nascimento;
    private String telefone;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child( getCPF() );
        usuariosRef.setValue( this );
    }


    public void atualizar() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosRef = firebaseRef.child("usuarios").child( getId() );
        Map<String, Object> valoresUsuario = converterParaMap();
        usuariosRef.updateChildren( valoresUsuario );
    }
    public Map<String, Object> converterParaMap() {

        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail());
        usuarioMap.put("caminhoFoto", getCaminhoFoto());
        usuarioMap.put("nome", getNome());
        usuarioMap.put("id", getId());
        usuarioMap.put("nomePesquisa", getNome().toUpperCase());
        return usuarioMap;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNomePesquisa() {
        return nomePesquisa;
    }

    public void setNomePesquisa(String nomePesquisa) {
        this.nomePesquisa = nomePesquisa.toUpperCase();
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

}
