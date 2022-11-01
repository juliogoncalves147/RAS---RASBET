package Entidades;


import java.util.*;


public class Tecnico extends Utilizador{
    private boolean aTrabalhar;

    public Tecnico(String nome, String nomeutilizador, String email, String password, boolean isLogged, Date dataNascimento, String numeroidfical, String numeroidcivil, Boolean aTrabalhar) {
        super(nome, nomeutilizador, email, password, isLogged, dataNascimento, numeroidfical, numeroidcivil);
        this.aTrabalhar = aTrabalhar;
    }

    public boolean isaTrabalhar() {
        return aTrabalhar;
    }

}