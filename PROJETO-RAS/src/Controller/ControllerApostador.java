package Controller;

import Model.Apostador;
import Model.EstadoJogo;
import Model.Jogo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        int opcao = 0;
        //TODO: ver as apostas do apostador e atualizar o saldo e a base de dados referente a isso. Se tiver fechada, atualizar o estado da aposta e colocar o valor como 0 caso a perca
        while (opcao != 5) {
            this.view.apostadorMainMenu(this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currency); //mudar de acoroo com região
            opcao = this.scan.nextInt();
            switch (opcao) {
                case 1:
                    this.view.subheader("Jogos", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currency);
                    this.apostar(this.getJogos());
                    break;
               case 2:
                   //altera informações do perfil
                   this.view.subheader("Alterar informações", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currency);
                   this.view.optionsMenu(new String[]{"1 - Nome do Utilizador", "2 - Email", "3 - Password", "0 - Voltar"});
                   this.scanOption(0,3);
                   this.changeInfo(opcao);
                   break;
                case 3:
                    //historico de transações
                    this.view.subheader("Histórico de transações", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currency);
                    ResultSet transacoes = this.db.query("SELECT (tipo, valor) FROM Transacao WHERE idUser = '" + this.user.getNomeutilizador() + "'");
                    this.showTransacoes(transacoes);
                    break;
                case 4:
                    //historico de apostas
                    //TODO: Ver percentagem de acerto
                    this.view.subheader("Histórico de apostas", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currency);
                    this.showApostas();
                    break;
                case 5:
                    //depositar dinheiro
                    break;
                case 6:
                    //levantar dinheiro
                    break;

                default:
                    this.view.line("Opção inválida!");
                    break;
            }
        }
    }

    private void showApostas() {
        ResultSet apostas = this.db.query("SELECT (estado, data, ) FROM Boletim WHERE idUser = '" + this.user.getNomeutilizador() + "'");
        this.view.line("Estado\tData\tValor");

        //Fazer a classe boletim, com todos os detalhes dos prognosticos.
        List<String> ids = new ArrayList<>();
        try {
            int j = 0;
            for (int i = 0; apostas.next() && i < 20; i++){
                ids.add(apostas.getString("id"));
                this.view.line("j * 20 + i+1 - " + apostas.getString("tipo") + " - " + apostas.getDouble("valor"));
                if (i == 19){
                    this.view.optionsMenu(new String[]{"-1 - Ver mais apostas", "-2 - Boletim Completo", "0 - Voltar"});
                    int opcao = this.scanOption(0,2);
                    if (opcao == 1){
                        i = 0;
                        j++;
                    } else if (opcao == 2){
                        int index = j * 20 + i;
                        ResultSet jogo = this.db.query("SELECT * FROM Jogo WHERE id = '" + ids.get(index) + "'");
                        //this.option
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showTransacoes(ResultSet transacoes) {
        try {
            for (int i = 0; transacoes.next() && i < 20; i++){
                this.view.line(transacoes.getString("tipo") + " - " + transacoes.getDouble("valor"));
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
                this.view.line("Username atual: " + this.user.getNomeutilizador());
                this.view.line("Insira o novo nome de utilizador: ");
                String nome = this.scan.next();
                if(this.db.update("UPDATE User SET nome = '" + nome + "' WHERE id = '" + this.user.getNomeutilizador() + "'")){
                    this.user.setNome(nome);
                    this.view.line("Nome alterado com sucesso!");
                }
                else{
                    this.view.line("Erro ao alterar nome!");
                }
                break;
            case 2:
                this.view.line("Email atual: " + this.user.getEmail());
                this.view.line("Insira o novo email: ");
                String email = this.scan.next();
                if(this.db.update("UPDATE User SET nome = '" + email + "' WHERE id = '" + this.user.getNomeutilizador() + "'")){
                    this.user.setEmail(email);
                    this.view.line("Email alterado com sucesso!");
                }
                else{
                    this.view.line("Erro ao alterar email!");
                }
                break;
            case 3:
                this.view.line("Password atual: " + this.user.getPassword());
                this.view.line("Insira a nova password: ");
                String password = this.scan.next();
                if(this.db.update("UPDATE User SET nome = '" + password + "' WHERE id = '" + this.user.getNomeutilizador() + "'")){
                    this.user.setPassword(password);
                    this.view.line("Password alterada com sucesso!");
                }
                else{
                    this.view.line("Erro ao alterar password!");
                }
                break;
            default:
                this.view.line("Opção inválida!");
                break;
        }
    }

    private void apostar(List<Jogo> jogos) {
        this.view.line("Insira o id do jogo: ");
        int opcao = this.scanOption(0, jogos.size());
        if (opcao != 0) {
            this.view.line("Insira o prognóstico: ");
            String prognostico = this.scan.next();

            this.view.line("Insira o valor a apostar: ");
            double valor = this.scan.nextDouble();

            Jogo jogo = jogos.get(opcao-1);
            if (jogo.getEstado() == EstadoJogo.ABERTO) {
                if (this.user.levantar(valor)) {
                    this.db.update("UPDATE User SET saldo = " + this.user.getSaldo() +
                            " WHERE id = '" + this.user.getNomeutilizador() + "'");
                    this.db.update("INSERT INTO Apostas (idJogo, idApostador, prognostico, valor) VALUES ('" +
                            jogo.getId() + "', '" + this.user.getNomeutilizador() + "', '" + prognostico + "', " + valor + ")");
                } else {
                    this.view.line("Saldo insuficiente!");
                }
            } else {
                this.view.line("Não é possível apostar neste jogo!");
            }
        }
    }
}