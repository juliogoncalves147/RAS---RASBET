package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Especialista extends Utilizador{
    private boolean aTrabalhar;

    public Especialista(ResultSet userRS) throws SQLException {

        this(userRS.getString("nome"), userRS.getString("id"),
                userRS.getString("email"), userRS.getString("password"),
                userRS.getBoolean("logado"), userRS.getDate("dataNascimento"),
                userRS.getString("idFiscal"), userRS.getString("idCivil"),
                false);
    }
    public Especialista(String nome, String nomeutilizador, String email, String password, boolean isLogged, Date dataNascimento, String numeroidfical, String numeroidcivil, boolean aTrabalhar) {
        super(nome, nomeutilizador, email, password, isLogged, dataNascimento, numeroidfical, numeroidcivil);
        this.aTrabalhar = aTrabalhar;
    }

}
