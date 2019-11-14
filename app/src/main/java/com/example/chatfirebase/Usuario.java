package com.example.chatfirebase;

public class Usuario {
    private String id;
    private String nome;
    private String senha;
    private String email;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario(String id, String nome, String senha, String email, String token) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.token = token;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
