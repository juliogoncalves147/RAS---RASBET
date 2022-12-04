package Entidades.USER;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Apostador extends Utilizador{
    private double saldo;

    public Apostador(ResultSet userRS) throws SQLException {

        this(userRS.getString("nome"),
                userRS.getString("id"), userRS.getString("email"),
                userRS.getString("password"), true,
                userRS.getDate("dataNascimento"), userRS.getString("idFiscal"),
                userRS.getString("idCivil"), userRS.getDouble("saldo"));

    }

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
}