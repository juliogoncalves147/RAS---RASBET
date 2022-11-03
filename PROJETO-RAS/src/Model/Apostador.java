package Model;

import java.util.Date;

public class Apostador extends Utilizador{
    private double saldo;

    public Apostador(String nome, String nomeutilizador, String email, String password, boolean isLogged, Date dataNascimento, String numeroidfical, String numeroidcivil, double saldo) {
        super(nome, nomeutilizador, email, password, isLogged, dataNascimento, numeroidfical, numeroidcivil);
        this.saldo = saldo;
    }

    public Apostador(){
        super();
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void depositar(double valor){
        this.saldo += valor;
    }

    public boolean levantar(double valor) {
        if (valor <= this.saldo) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }
    public void alterarInformacoesPerfil(String nome, String email, String password, String numeroidfical, String numeroidcivil) {
        // 5TODO Auto-generated method stub

    }
    public void fazerAposta(int idAposta, double valor) {

    }
    public void consultarHistoricoApostas() {

    }
    public void consultarHistoricoTransacoes() {

    }
    public void depositarDinheiro(double valor) {

    }
    public boolean levantarDinheiro(double valor) {
        return false;
    }
    public void consultarPromocoes() {

    }
    public void consultarNotificacoes() {

    }
    public void fazerPedidoAjuda(String texto) {

    }
}