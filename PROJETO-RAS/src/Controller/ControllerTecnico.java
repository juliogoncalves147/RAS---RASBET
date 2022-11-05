package Controller;

import Model.PedidoAjuda;
import Model.Apostador;
import Model.BaseDados;
import Model.Tecnico;
import Model.Utilizador;
import View.Menu;
import java.util.*;

import java.util.Scanner;

public class ControllerTecnico extends Controller {
    private Tecnico user;
    public ControllerTecnico(Tecnico user, Controller controller) {
        this.user = user;
        this.view = controller.getView();
        this.scan = controller.getScan();
        this.db = controller.getDb();
    }

    public void run() {
        int opcao = 0;
        while (opcao != 5) {
            this.view.TecnicoMainMenu(this.user.getNomeutilizador());
            opcao = this.scanOption(0,3);
            switch (opcao) {
                case 1: //consultar jogos
                    this.getJogos();
                    break;
                case 2: //responder a pedidos
                    List<PedidoAjuda> pedidos = this.getPedidos();
                    this.view.line("Escolha um pedido" );
                    int i = this.scanOption(1, this.getPedidos().size());
                    this.view.line("Escreva a resposta ao pedido:");
                    String resposta = this.scan.nextLine();
                    this.responderPedido(resposta, pedidos.get(i-1),this.user.getNomeutilizador());
                    String notificacao = "O seu pedido de ajuda foi respondido com sucesso, aceda ao mesmo através da sua area de notificações";
                    String destinatarios = pedidos.get(i-1).getIdUtilizador();
                    this.enviarNotificacoes(notificacao,destinatarios,this.user.getNomeutilizador());

                    break;
                case 3: //terminar sessão
                    this.view.line("A terminar sessão...");
                    break;
                default:
                    this.view.line("Opção inválida!");
            }
        }
    }


    public void responderPedido(String resposta, PedidoAjuda pedido, String idTrabalhador) {
        if (this.db.update("UPDATE PedidoAjuda SET resposta = '" + resposta + "' , idTrabalhador='" + idTrabalhador +  "' WHERE id = '" + pedido.getId() + "'")) {
            this.view.line("Pedido respondido com sucesso!");
        } else {
            this.view.line("Erro ao responder pedido!");
        }
    }


}