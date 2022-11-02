package Controller;

import Model.Apostador;
import Model.EstadoJogo;
import Model.Jogo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ControllerApostador extends Controller {
    private final Apostador user;
    private final String currency;

    public ControllerApostador(Apostador user, Controller controller) {
        this.user = user;
        this.view = controller.getView();
        this.scan = controller.getScan();
        this.db = controller.getDb();
        this.currency = "€"; //TODO: MUDAR DEPOIS
    }

    public void run() {
        int opcao = 0;
        while (opcao != 5) {
            this.view.apostadorMainMenu(this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currency); //mudar de acoroo com região
            opcao = this.scan.nextInt();
            switch (opcao) {
                case 1:
                    this.getJogos();
                    break;
               case 2:
                   //altera informações do perfil

                    break;
                case 3:
                    //historico de transações
                    break;
                case 4:
                    //historico de apostas
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

    private void getJogos() {
        this.view.subheader("Jogos", this.user.getNomeutilizador() + " - " + this.user.getSaldo() + this.currency);
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
        if (opcao == 0) return;

        ResultSet rs = this.db.query("SELECT * FROM Jogo WHERE desporto = '" +
                desportos.get(opcao - 1).split(" - ")[1] + "'" + "AND estado = 0 OR estado = 1");

        List<Jogo> jogos = new ArrayList<>();
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

        this.view.line("Insira o id do jogo: ");
        opcao = this.scanOption(0, jogos.size());
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