package Controller;

import Model.Administrador;
import Model.Apostador;
import Model.Jogo;

import java.util.List;


public class ControllerAdmin extends Controller {
    private Administrador user;
    public ControllerAdmin(Administrador user, Controller controller) {
        this.user = user;
        this.view = controller.getView();
        this.scan = controller.getScan();
        this.db = controller.getDb();
    }


    public void run() {
        int opcao = 0;
        while (opcao != 5) {
            this.view.adminMainMenu(this.user.getNomeutilizador());
            opcao = this.scanOption(0, 5);
            switch (opcao) {
                case 1: //consultar jogos
                    this.getJogos();
                    break;

                case 2: //alterar estado de apostas
                    List<Jogo> jogos = this.getJogos();
                    this.view.line("Escolha um jogo" );
                    int i = this.scanOption(0, this.getJogos().size());

                    this.view.line("Deseja alterar para: \n 0 -> Vai Decorrer \n 1 -> Acabado \n  2 -> A correr \n 3 -> Suspenso \n ");
                    int estado = this.scanOption(0,3);
                    this.alterarEstadoJogo(estado, jogos.get(i-1));
                    break;

                case 3: //criar promocao
                    this.view.line("Selecione o jogo para a promoção:\n ");
                    List<Jogo> jogos1= this.getJogos();
                    this.view.line("Selecione o jogo: \n");
                    int i1 = this.scanOption(0, this.getJogos().size());

                    this.view.line("Selecione a odd que deseja alterar: \n");
                    for (String odd : jogos1.get(i1-1).getOdds().keySet()) {
                        this.view.line(odd);
                        this.view.line(jogos1.get(i1-1).getOdds().get(odd).toString());

                    }
                    String key = this.scan.nextLine();

                  //  this.view.line("odd1: " + jogos1.get(i1-1).getOdds().get("odd1"));
                    this.view.line("Altere a odd selecionada para a nova: ");
                    double oddalterada = this.scan.nextDouble();





                    if(this.isPromocaoValida(oddalterada, jogos1.get(i1 - 1).getOdds().get(key))){

                        this.view.line("Deseja enviar notificação?");
                        Boolean notificacaobool = this.scan.nextBoolean();


                        if(notificacaobool){
                            this.view.line("Para quem deseja enviar a notificação?");
                            String destinatarios = this.scan.nextLine();
                            this.view.line("Insira a notificação desejada: ");
                            String notificacao = this.scan.nextLine();
                            this.enviarNotificacoes(notificacao,destinatarios,this.user.getNomeutilizador());
                            this.criarPromocao(oddalterada, jogos1.get(i1-1), key);
                        } else {
                            this.criarPromocao(oddalterada, jogos1.get(i1-1),key);
                        }
                    }
                    else{
                        this.view.line("Promoção inválida");
                    }


                    break;
                case 4: //enviar notificacoes
                    this.view.line("Para quem deseja enviar a notificação?");
                    String destinatarios = this.scan.nextLine();
                   if (destinatarios.equals("todos")) {
                       this.view.line("Insira a notificação desejada: ");
                       String notificacao = this.scan.nextLine();
                       this.enviarNotificacoes(notificacao, "todos",this.user.getNomeutilizador());
                       break;
                   }


                    this.view.line("Insira a notificação desejada: ");
                    String notificacao = this.scan.nextLine();
                    this.enviarNotificacoes(notificacao,destinatarios,this.user.getNomeutilizador());
                    break;

                case 0: //terminar sessão
                    this.view.line("A terminar sessão...");
                    return;


                default:
                    this.view.line("Opção inválida!");
            }
        }
    }


    public void alterarEstadoJogo(int estado, Jogo jogo) {
        if (this.db.update("UPDATE Jogo SET estado = " + estado + " WHERE id = " + jogo.getId())){
            this.view.line("Estado alterado com sucesso!");
        } else {
            this.view.line("Erro ao alterar estado!");
        }
    }

    public boolean isPromocaoValida(double oddalterada, double odd) {
        if(oddalterada < odd && oddalterada < 0){
            return false;
        }
        else{
            return true;
        }
    }

    public void criarPromocao(double oddalterada, Jogo jogo,String key) {
        if (this.db.update("UPDATE Odds SET valor = " + oddalterada + " WHERE idJogo='" + jogo.getId() + "' AND prognostico='" + key + "'")){
            this.view.line("Promoção criada com sucesso!");
        } else {
            this.view.line("Erro ao criar promoção!");
        }
    }





}

