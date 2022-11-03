package Controller;

import Model.*;
import View.Menu;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Entidades.PedidoAjuda;


public class Controller {
    String currency;
    BaseDados db;
    private RasBetModel model;
    Menu view;
    Scanner scan;
    public Controller() {
        this.db = new BaseDados();
        this.model = new RasBetModel();
        this.view = new Menu();
        this.scan = new Scanner(System.in);
        this.currency = "€";
    }

   


    private Utilizador login() {
        //TODO: alterar isto
        /*
        this.scan.nextLine();
        this.view.line("Username: ");
        String username = this.scan.nextLine();
        this.view.line("Password: ");
        String password = this.scan.nextLine();*/
        String username = "Tuberacher";
        String password = "tuberacher";
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
        return new SimpleDateFormat("dd/MM/yyyy").parse(s);
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
                        new ControllerTecnico((Tecnico) user, this).run();
                    else if (user instanceof Especialista)
                        new ControllerEspecialista((Especialista) user, this).run();
                    else if (user instanceof Administrador)
                        new ControllerAdmin((Administrador) user, this).run();
                }
                else this.run();
                //this.connect(); //as promocoes/desportos são inicializados
                break;
            case 0:
                break;
        }
    }

    public List<Jogo> getJogos() {
        ResultSet sports = this.db.query("SELECT desporto FROM Desporto");
        List<String> desportos = new ArrayList<>();
        try {
            for (int i = 1; sports.next(); i++) {
                desportos.add(i + " - " + sports.getString("desporto"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        desportos.add("0 - Voltar");
        this.view.optionsMenu(desportos.toArray(new String[0]));

        this.view.line("Insira o id do desporto: ");
        int opcao = this.scan.nextInt();
        List<Jogo> jogos = new ArrayList<>();

        if (opcao == 0) return jogos;

        ResultSet rs = this.db.query("SELECT * FROM Jogo WHERE desporto = '" +
                desportos.get(opcao - 1).split(" - ")[1] + "'" + "AND estado = 0 OR estado = 1");

        try {
            while (rs.next())
                jogos.add(new Jogo(rs.getString("id"), EstadoJogo.values()[rs.getInt("estado")],
                        rs.getDate("data"), null));

            for (Jogo j : jogos) {
                ResultSet odds = this.db.query("SELECT * FROM Odds WHERE idJogo = '" + j.getId() + "'");
                LinkedHashMap<String, Double> oddsMap = new LinkedHashMap<>();
                while (odds != null && odds.next()) {
                    oddsMap.put(odds.getString("prognostico"), odds.getDouble("valor"));
                }
                j.setOdds(oddsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> jogosString = new ArrayList<>();

        for (int i = 0; i < jogos.size(); i++) {
            jogosString.add(i + 1 + " - " + jogos.get(i).toString());
        }

        jogosString.add("0 - Voltar");
        this.view.optionsMenu(jogosString.toArray(new String[0]));
        return jogos;
    }

    public List<PedidoAjuda> getPedidos(){
        ResultSet rs = this.db.query("SELECT * FROM PedidoAjuda WHERE estado = 0");
        List<PedidoAjuda> pedidos = new ArrayList<>();
        try {
            while (rs.next()) {
                pedidos.add(new PedidoAjuda(rs.getString("id"), rs.getString("userApostador"),
                        rs.getString("userTecnico"), rs.getString("texto"), rs.getString("resposta"),
                        EstadoPedido.values()[rs.getInt("estado")]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;

    }

    public void enviarNotificacoes(String notificacao, String destinatarios) {
        if (this.db.update("INSERT INTO Notificacao (text, idUser) VALUES ('" + notificacao + "', '" + destinatarios + "')")){
            this.view.line("Notificação enviada com sucesso!");
        } else {
            this.view.line("Erro ao enviar notificação!");
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
