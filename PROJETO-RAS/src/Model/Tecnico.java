package Model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class Tecnico extends Utilizador{
    private boolean aTrabalhar;

    public Tecnico(ResultSet userRS) throws SQLException {

        this(userRS.getString("nome"), userRS.getString("id"),
                userRS.getString("email"), userRS.getString("password"),
                userRS.getBoolean("logado"), userRS.getDate("dataNascimento"),
                userRS.getString("idFiscal"), userRS.getString("idCivil"),
                false);

    }

    public Tecnico(String nome, String nomeutilizador, String email, String password, boolean isLogged, Date dataNascimento, String numeroidfical, String numeroidcivil, Boolean aTrabalhar) {
        super(nome, nomeutilizador, email, password, isLogged, dataNascimento, numeroidfical, numeroidcivil);
        this.aTrabalhar = aTrabalhar;
    }

    public boolean isaTrabalhar() {
        return aTrabalhar;
    }

    public void responderPedido(String resposta, int idPedido) {
        // TODO Auto-generated method stub
        
    }
    

    
}