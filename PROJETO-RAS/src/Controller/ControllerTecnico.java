package Controller;

import Model.PedidoAjuda;
import Model.Tecnico;

import java.util.*;

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
            opcao = this.scan.nextInt();
            switch (opcao) {
                case 1: //consultar jogos
                    this.getJogos();
                    break;

                case 2: //responder a pedidos
                    // Get pedidos
                    List<PedidoAjuda> pedidos = this.getPedidos();
                    if (pedidos.isEmpty()) {
                        this.view.line("Nao existem pedidos por responder");
                        break;
                    }

                    // Print menu pedidos
                    ArrayList<String> optionsPedido = new ArrayList<>();
                    for (int i = 1; i < pedidos.size()+1; i++) {
                        optionsPedido.add(i + " - " + pedidos.get(i-1).toString());
                    }
                    String[] arr = optionsPedido.toArray(new String[0]);
                    this.view.optionsMenu(arr);

                    this.view.line("Escolha um pedido" );
                    int i = this.scanOption(0, this.getPedidos().size());
                    this.view.line("Escreva a resposta ao pedido:");
                    String resposta = this.scan.nextLine();
                    this.responderPedido(resposta, pedidos.get(i-1));
                    String notificacao = "O seu pedido de ajuda foi respondido com sucesso, aceda ao mesmo através da sua area de notificações";
                    String destinatarios = pedidos.get(i-1).getIdUtilizador();
                    this.enviarNotificacoes(notificacao,destinatarios);
                    
                    break;
                case 3: //terminar sessão
                    this.view.line("A terminar sessão...");
                    break;
                default:
                    this.view.line("Opção inválida!");
            }
        }
    }


    public void responderPedido(String resposta, PedidoAjuda pedido) {
       if (this.db.update("UPDATE PedidoAjuda SET resposta='" + resposta + "' WHERE texto='" + pedido.getTexto() + "'")) {
            this.view.line("Pedido respondido com sucesso!");
        } else {
            this.view.line("Erro ao responder pedido!");
        }
    }
   

}
