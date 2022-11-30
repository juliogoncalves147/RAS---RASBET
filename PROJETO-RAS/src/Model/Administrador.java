package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Administrador extends Utilizador{
    private boolean aTrabalhar;

    public Administrador(ResultSet userRS) throws SQLException {

        this(userRS.getString("nome"), userRS.getString("id"),
                userRS.getString("email"), userRS.getString("password"),
                userRS.getBoolean("logado"), userRS.getDate("dataNascimento"),
                userRS.getString("idFiscal"), userRS.getString("idCivil"),
                false);

    }
    public Administrador(String nome, String nomeutilizador, String email, String password, boolean isLogged, Date dataNascimento, String numeroidfical, String numeroidcivil, boolean aTrabalhar) {
        super(nome, nomeutilizador, email, password, isLogged, dataNascimento, numeroidfical, numeroidcivil);
        this.aTrabalhar = aTrabalhar;
    }

    public boolean getaTrabalhar() {
        return aTrabalhar;
    }

    public void setaTrabalhar(boolean aTrabalhar) {
        this.aTrabalhar = aTrabalhar;
    }

    public void alterarEstadoApostas(String estado, int idAposta) {

    }
    public void criarPromocao(String texto, String tipo, String dataInicio, String dataFim, String valor) {
        // TODO Auto-generated method stub
        
    }

    public void enviarNotificacoes(String texto, String destinatarios) {
        // TODO Auto-generated method stub
        
    }
}