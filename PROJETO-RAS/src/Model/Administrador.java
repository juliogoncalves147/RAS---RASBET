package Model;

import java.util.*;

public class Administrador extends Utilizador implements IAdministradorFacade {
    private boolean aTrabalhar;

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

    @Override
    public void alterarEstadoApostas(String estado, int idAposta) {
      
        
        
    }

    @Override
    public void criarPromocao(String texto, String tipo, String dataInicio, String dataFim, String valor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void enviarNotificacoes(String texto, String destinatarios) {
        // TODO Auto-generated method stub
        
    }
}