package Controller;

import Model.*;
import View.Menu;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Controller {
    BaseDados db;
    Menu view;
    Scanner scan;
    public Controller(){
        this.db = new BaseDados();
        this.view = new Menu();
        this.scan = new Scanner(System.in);
    }

    public void connect(){

    }


    private Utilizador login() {
        //TODO: alterar isto
        /*
        this.scan.nextLine();
        this.view.line("Username: ");
        String username = this.scan.nextLine();
        this.view.line("Password: ");
        String password = this.scan.nextLine();*/
        String username = "henrique";
        String password = "Henrique";
        ResultSet userRS = this.db.query("SELECT * FROM User WHERE id = '" + username +
                "' AND password = '" + password + "'");
        if (userRS != null) {
            try {
                userRS.next();
                int tipo = userRS.getInt("tipo");
                Utilizador user = null;
                if (tipo == 0)
                    user = new Apostador(userRS.getString("nome"),
                            userRS.getString("id"), userRS.getString("email"),
                            userRS.getString("password"), true,
                            userRS.getDate("dataNascimento"), userRS.getString("idFiscal"),
                            userRS.getString("idCivil"), userRS.getDouble("saldo"));
                else if (tipo == 1)
                    user = new Tecnico(userRS.getString("nome"), userRS.getString("id"),
                            userRS.getString("email"), userRS.getString("password"),
                            userRS.getBoolean("isLogged"), userRS.getDate("dataNascimento"),
                            userRS.getString("numeroidfiscal"), userRS.getString("numeroidcivil"),
                            false);
                else if (tipo == 2)
                    user = new Especialista(userRS.getString("nome"), userRS.getString("id"),
                            userRS.getString("email"), userRS.getString("password"),
                            userRS.getBoolean("isLogged"), userRS.getDate("dataNascimento"),
                            userRS.getString("numeroidfiscal"), userRS.getString("numeroidcivil"),
                            false);
                else if (tipo == 3)
                    user = new Administrador(userRS.getString("nome"), userRS.getString("id"),
                            userRS.getString("email"), userRS.getString("password"),
                            userRS.getBoolean("isLogged"), userRS.getDate("dataNascimento"),
                            userRS.getString("numeroidfiscal"), userRS.getString("numeroidcivil"),
                            false);

                this.db.update("UPDATE User SET logado = 1 WHERE id = '" + username + "'");
                this.view.line("Login efetuado com sucesso!");
                return user;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                this.view.line("Username ou password errados");
                return null;
            }
        } else {
            this.view.line("Login falhou!");
            return null;
        }
    }

    private Date stringToDate(String s) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        return date;
    }

    private boolean registar() {
        this.scan.nextLine();
        this.view.line("Nome: ");
        String nome = this.scan.nextLine();
        this.view.line("Username: ");
        String username = this.scan.nextLine();
        this.view.line("Email: ");
        String email = this.scan.nextLine();
        this.view.line("Password: ");
        String password = this.scan.nextLine();
        boolean valida = false;
        Date dataNascimento = null;
        while (!valida) {
            this.view.line("Data de Nascimento (dd/MM/yyyy): ");
            String data = this.scan.nextLine();
            try {
                dataNascimento = this.stringToDate(data);
                valida = true;
            } catch (ParseException e) {
                this.view.line("Data inválida!");
            }
        }
        this.view.line("Numero de Identificacao Fiscal: ");
        String nif = this.scan.nextLine();
        this.view.line("Numero de Identificacao Civil: ");
        String nic = this.scan.nextLine();

        if (this.db.update("INSERT INTO User (nome, id, email, password, dataNascimento, " +
                "idFiscal, idCivil, tipo, logado, saldo) VALUES ('" + nome + "','" + username + "','" + email + "','" +
                password + "'," + "'" + new SimpleDateFormat("yyyy-MM-dd").format(dataNascimento) + "','" +
                nif + "','" + nic + "', '0', '0', '0')")) {
            this.view.line("Registo efetuado com sucesso!\n");
            return true;
        } else {
            this.view.line("Erro ao registar!");
            return false;
        }
    }

    public int scanOption(int min, int max) {
        int opt = -1;
        while (opt < min || opt > max) {
            try {
                opt = this.scan.nextInt();
                if (opt < min || opt > max) this.view.line("Opção inválida, tente novamente.");
            } catch (InputMismatchException e) {
                this.view.line("Opção inválida, tente novamente.");
                this.scan.nextLine();
            }
        }
        return opt;
    }


    public void run() {
        this.view.mainMenu();
        int opt = this.scanOption(0, 2);
        switch (opt) {
            case 1:
                if(this.registar()) this.login();
                else this.run();
                break;
            case 2:
                Utilizador user = this.login();
                if (user != null) {
                    if (user instanceof Apostador)
                        new ControllerApostador((Apostador) user, this).run();
                    else if (user instanceof Tecnico)
                        new ControllerTecnico((Tecnico) user).run();
                    else if (user instanceof Especialista)
                        new ControllerEspecialista((Especialista) user).run();
                    else if (user instanceof Administrador)
                        new ControllerAdmin((Administrador) user).run();
                }
                else this.run();
                this.connect(); //as promocoes/desportos são inicializados
                break;
            case 0:
                break;
        }
    }

    public BaseDados getDb() {
        return db;
    }

    public Menu getView() {
        return view;
    }

    public Scanner getScan() {
        return scan;
    }
}
