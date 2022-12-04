package Entidades.USER;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Administrador extends Utilizador{
    public Administrador(String nome, String nomeutilizador, String email, String password, boolean isLogged, Date dataNascimento, String numeroidfical, String numeroidcivil) {
        super(nome, nomeutilizador, email, password, isLogged, dataNascimento, numeroidfical, numeroidcivil);
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