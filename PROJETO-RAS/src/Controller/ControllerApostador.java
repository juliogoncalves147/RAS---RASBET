package Controller;

import Model.Apostador;
import Model.EstadoJogo;
import Model.Jogo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ControllerApostador extends Controller {
    private final Apostador user;

    public ControllerApostador(Apostador user, Controller controller){
        this.user = user;
        this.view = controller.getView();
        this.scan = controller.getScan();
        this.db = controller.getDb();
    }

    public void run() {
        int opcao = -1;
        //TODO: ver as apostas do apostador e atualizar o saldo e a base de dados referente a isso. Se tiver fechada, atualizar o estado da aposta e colocar o valor como 0 caso a perca
        while (opcao != 0) {
            this.view.apostadorMainMenu(this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency()); //mudar de acordo com região
            opcao = this.scanOption(0,10); //TODO VER SE ESTÁ CERTO ESTE 10
            switch (opcao) {
                case 1:
                    this.view.subheader("Jogos", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    this.getJogos();
                    break;
               case 2:
                   //altera informações do perfil
                   this.view.subheader("Alterar informações", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                   this.view.optionsMenu(new String[]{"1 - Nome do Utilizador", "2 - Email", "3 - Password", "0 - Voltar"});
                   this.view.line("Insira a opção: ");
                   int alterar = this.scanOption(0,3);
                   this.changeInfo(alterar);

                   break;
               case 3:
                    //historico de transações
                    this.view.subheader("Histórico de transações", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    ResultSet transacoes = this.db.query("SELECT date, tipo, valor FROM Transacao WHERE idUser = '" + this.user.getNomeutilizador() + "'");
                    this.showTransacoes(transacoes);
                    break;
               case 4:
                    //historico de apostas
                    this.view.subheader("Histórico de apostas", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    this.showApostas();
                    break;
               case 5:
                    //depositar dinheiro
                    this.view.subheader("Depositar dinheiro", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    this.depositar();
                    break;
               case 6:
                   //fazer aposta
                   this.view.subheader("Apostas", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                   this.apostar(this.getJogos());
                   break;
                case 7:
                    //levantar dinheiro
                    this.view.subheader("Levantar dinheiro", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    this.levantar();
                    break;
                case 8:
                    //consultar promoções
                    this.view.subheader("Promoções", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    this.promocoes();
                    break;
                case 9:
                    //consultar notificações
                    this.view.subheader("Notificações", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    this.notificacoes();
                    break;
                case 10:
                    this.view.subheader("Pedir ajuda", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currencyTime.getCurrency());
                    this.pedirAjuda();
                    break;
               default:
                    this.view.line("Opção inválida!");
                    break;
            }
        }
    }

    private void notificacoes() {
        ResultSet notificacoes = this.db.query("SELECT text, idTrabalhador, (enviarTodos = '0') as Geral, (NotificacaoLida.id is not null) as Lida " +
                "FROM Notificacao " +
                "LEFT JOIN NotificacaoLida ON Notificacao.id = NotificacaoLida.id " +
                "WHERE Notificacao.idUser = '" + this.user.getNomeutilizador() + "' OR enviarTodos = 1 " +
                "UNION " +
                "SELECT text, idTrabalhador, (enviarTodos = '0') as Geral, (NotificacaoLida.id is not null) as Lida " +
                "FROM Notificacao " +
                "RIGHT JOIN NotificacaoLida ON Notificacao.id = NotificacaoLida.id " +
                "WHERE Notificacao.idUser = '" + this.user.getNomeutilizador() + "' OR enviarTodos = 1");

        List<String> lidas = new ArrayList<>();
        List<String> naoLidas = new ArrayList<>();

        try {
            while (notificacoes.next()) {
                String text = notificacoes.getString("text");
                String idTrabalhador = notificacoes.getString("idTrabalhador");
                boolean geral = notificacoes.getBoolean("Geral");
                boolean lida = notificacoes.getBoolean("Lida");
                String notificacao = (geral) ? "Geral - " + text : idTrabalhador + " - " + text ;
                if (lida) {
                    lidas.add(notificacao);
                } else {
                    naoLidas.add(notificacao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.view.line("Notificações não lidas:");
        for (String notificacao : naoLidas) {
            this.view.line(notificacao);
        }

        this.view.line("\nNotificações lidas:");
        for (String notificacao : lidas) {
            this.view.line(notificacao);
        }
    }

    private void pedirAjuda() {
        this.view.line("Insira o seu pedido de ajuda:");
        String pedido = this.scan.nextLine();
        if (this.db.update("INSERT INTO PedidoAjuda(userApostador, data, texto) VALUES ('" + this.user.getNomeutilizador()
                + "', '" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "' , '" + pedido + "')"))
            this.view.line("Pedido de ajuda enviado com sucesso!");
        else this.view.line("Erro ao enviar pedido de ajuda.");
    }

    private void promocoes() {
        //ver s
        ResultSet promocoes = this.db.query("select prognostico, oddMelhorada from Promocao INNER JOIN Jogo on Promocao.idJogo = Jogo.id WHERE Jogo.estado = 0;");
        try {
            while (promocoes.next()) {
                this.view.line(promocoes.getString("prognostico") + " - " + promocoes.getString("oddMelhorada") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void levantar() {
        this.view.line("Insira o valor a levantar:");
        double valor = this.scan.nextDouble();
        if (valor > this.user.getSaldo()) {
            this.view.line("Não tem saldo suficiente!");
        } else {
            if(this.db.update("UPDATE User SET saldo = " + (this.user.getSaldo() + valor) + " WHERE id = '" + this.user.getNomeutilizador() + "'") &&
                    this.db.update("INSERT INTO Transacao VALUES ('" + UUID.randomUUID().toString() + "', '"  + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "', '" + this.user.getNomeutilizador() + "', " + 1 + ", " + valor + ")")){
                this.user.setSaldo(this.user.getSaldo() - valor);
                this.view.line("Levantamento efetuado com sucesso!");
            } else {
                this.view.line("Ocorreu um erro ao levantar o dinheiro!");
            }
        }
    }

    private void depositar() {
        this.view.line("Insira o valor a depositar:");
        double valor = this.scan.nextDouble();
        if(this.db.update("UPDATE User SET saldo = " + (this.user.getSaldo() + valor) + " WHERE id = '" + this.user.getNomeutilizador() + "'") &&
        this.db.update("INSERT INTO Transacao VALUES ('" + UUID.randomUUID().toString() + "', '"  + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "', '" + this.user.getNomeutilizador() + "', " + 0 + ", " + valor + ")")){
            this.user.setSaldo(this.user.getSaldo() + valor);
            this.view.line("Depósito efetuado com sucesso!");
        }
        else this.view.line("Erro ao efetuar o depósito!");
    }

    private void showApostas() {
        ResultSet apostas = this.db.query("SELECT (id, estado, data, ) FROM Boletim WHERE idUser = '" + this.user.getNomeutilizador() + "'");
        this.view.line("Estado\tData\tValor");

        List<String> ids = new ArrayList<>();
        try {
            int j = 0;
            for (int i = 0; apostas.next() && i < 20; i++){
                ids.add(apostas.getString("id"));
                this.view.line((j * 20 + i+1) + " - " + apostas.getString("estado") + " - " + apostas.getDate("data"));
                if (i == 19){
                    this.view.optionsMenu(new String[]{"1 - Ver mais apostas", "2 - Ver Boletim Completo", "0 - Voltar"});
                    int opcao = this.scanOption(0,2);
                    if (opcao == 1){
                        i = -1; //depois incrementa com i++
                        j++;
                    } else if (opcao == 2){
                        this.view.line("Qual o índice?\n");
                        int index = this.scanOption(1, j*20 + i) - 1;
                        ResultSet jogo = this.db.query("SELECT * FROM Aposta WHERE idBoletim = '" + ids.get(index) + "'");
                        this.view.line((j * 20 + i+1) + " - " + apostas.getString("estado") + " - " + apostas.getDate("data"));
                        while(jogo.next()){
                            this.view.line(jogo.getString("equipa1") + " - " + jogo.getString("equipa2") + " - " + jogo.getString("data"));
                        }
                        //this.option
                    }
                    else if (opcao == 0){
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showTransacoes(ResultSet transacoes) {
        try {
            for (int i = 0; transacoes != null && transacoes.next() && i < 20; i++){
                String tipo = transacoes.getString("tipo").equals("0") ? "Depósito" : "Levantamento";
                this.view.line( transacoes.getString("date") + " : " +tipo + " - " + transacoes.getDouble("valor") + this.currencyTime.getCurrency() + "\n");
                if (i == 19){
                    this.view.optionsMenu(new String[]{"0 - Voltar", "1 - Mais"});
                    if (this.scanOption(0,1) == 1){
                        i = 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changeInfo(int opcao) {
        switch (opcao) {
            case 1:
                this.view.line("Username atual: " + this.user.getNomeutilizador() + "\n");
                this.view.line("Insira o novo nome de utilizador: ");
                String nome = this.scan.next();
                if(this.db.update("UPDATE User SET id = '" + nome + "' WHERE id = '" + this.user.getNomeutilizador() + "'")){
                    this.user.setNomeutilizador(nome);
                    this.view.line("Username alterado com sucesso!");
                }
                else{
                    this.view.line("Erro ao alterar nome!");
                }
                break;
            case 2:
                this.view.line("Email atual: " + this.user.getEmail() + "\n");
                this.view.line("Insira o novo email: ");
                String email = this.scan.next();
                if(this.db.update("UPDATE User SET email = '" + email + "' WHERE id = '" + this.user.getNomeutilizador() + "'")){
                    this.user.setEmail(email);
                    this.view.line("Email alterado com sucesso!");
                }
                else{
                    this.view.line("Erro ao alterar email!");
                }
                break;
            case 3:
                this.view.line("Password atual: " + this.user.getPassword() + "\n");
                this.view.line("Insira a nova password: ");
                String password = this.scan.next();
                if(this.db.update("UPDATE User SET password = '" + password + "' WHERE id = '" + this.user.getNomeutilizador() + "'")){
                    this.user.setPassword(password);
                    this.view.line("Password alterada com sucesso!");
                }
                else{
                    this.view.line("Erro ao alterar password!");
                }
                break;
            default:
                break;
        }
    }

    private void apostar(List<Jogo> jogos) {
        List<AbstractMap.SimpleEntry<String, String>> boletim = new ArrayList<>();
        boolean multipla = true;

        if (jogos.size() > 0) {
            while (multipla) {
                this.view.line("Insira o índice do jogo: ");

                int opcao = this.scanOption(0, jogos.size());
                if (opcao == 0) return;

                this.view.line("Insira o nome do prognóstico: ");
                String prognostico = this.scan.next();

                AbstractMap.SimpleEntry<String, String> aposta = new AbstractMap.SimpleEntry<>(jogos.get(opcao - 1).getId(), prognostico);

                if (!boletim.contains(aposta) && this.db.query("SELECT * FROM Odds WHERE idJogo = '" + jogos.get(opcao - 1).getId() + "' AND prognostico = '" + prognostico + "'") != null) {
                    boletim.add(new AbstractMap.SimpleEntry<>(jogos.get(opcao - 1).getId(), prognostico));
                    this.view.line("Deseja apostar em mais jogos? (S/N)");
                    String op = this.scan.next();
                    if (op.equals("N") || op.equals("n")) {
                        for (AbstractMap.SimpleEntry<String, String> a : boletim) {
                            ResultSet odd = this.db.query("SELECT * FROM Odds WHERE idJogo = '" + a.getKey() + "' AND prognostico = '" + a.getValue() + "'");
                            try {
                                odd.next();
                                this.view.line(a.getKey() + " - " + a.getValue() + " - " + odd.getDouble("valor") + "\n");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        this.view.line("Deseja confirmar a aposta? (S/N)");
                        String op2 = this.scan.next();
                        if (op2.equals("S") || op2.equals("s")) {
                            this.view.line("Insira o montante: ");
                            Double valor = this.scan.nextDouble();
                            if (this.user.levantar(valor)) {
                                Jogo jogo = jogos.get(opcao - 1);
                                if (jogo.getEstado() == EstadoJogo.ABERTO) {
                                } else {
                                    this.view.line("Não é possível apostar neste jogo!");
                                }

                                if (this.db.update("UPDATE User SET saldo = " + this.user.getSaldo() +
                                        " WHERE id = '" + this.user.getNomeutilizador() + "'") &&
                                        this.db.update("INSERT INTO Apostas (id, idJogo, idApostador, prognostico, valor) VALUES ('" +
                                                jogo.getId() + "', '" + this.user.getNomeutilizador() + "', '" + prognostico + "', " + valor + ")")) {
                                    this.view.line("Aposta registada com sucesso!");
                                }
                            } else {
                                this.view.line("Saldo insuficiente!");
                            }
                        } else {
                            boletim.clear();
                        }
                        multipla = false;
                    }
                }
            }
        }
    }
}