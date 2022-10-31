package Entidades;

import java.util.*;

public class Administrador extends Utilizador{
    private String aTrabalhar;

    public Administrador(String nome, String nomeutilizador, String email, String password, boolean isLogged, Date dataNascimento, String numeroidfical, String numeroidcivil, String aTrabalhar) {
        super(nome, nomeutilizador, email, password, isLogged, dataNascimento, numeroidfical, numeroidcivil);
        this.aTrabalhar = aTrabalhar;
    }

    public String getaTrabalhar() {
        return aTrabalhar;
    }

    public void setaTrabalhar(String aTrabalhar) {
        this.aTrabalhar = aTrabalhar;
    }
}