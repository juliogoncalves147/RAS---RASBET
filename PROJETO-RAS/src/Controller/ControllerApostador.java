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

    public ControllerApostador(Apostador user, Controller controller) {
        this.user = user;
        this.view = controller.getView();
        this.scan = controller.getScan();
        this.db = controller.getDb();
    }

    public void run() {
        int opcao = 0;
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