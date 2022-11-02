package Controller;

import Model.Administrador;
import Model.Apostador;


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
            opcao = this.scan.nextInt();
            switch (opcao) {
                case 1: //alterar estado de apostas
                    this.getJogos();
                    this.view.line("Escolha um jogo" + this.getJogos());
                    String jogo = this.scan.nextLine();
                    this.view.line("Deseja ativar, suspender ou fechar a aposta?");
                    String estado = this.scan.nextLine();
                    this.alterarEstadoApostas(estado, jogo);
                    break;
                case 2: //criar promocao
                    this.view.line("Selecione o jogo para a promoção: " + this.getJogos());
                    String jogo = this.scan.nextLine();
                    this.view.line("Insira a promoção desejada: ");
                    String promo = this.scan.nextLine();
                    if(this.isPromocaoValida(promo)){
                        
                        this.view.line("Deseja enviar notificação?");
                        Boolean notificacaobool = this.scan.nextBoolean();
                        
                        
                        if(notificacaobool){
                            this.view.line("Para quem deseja enviar a notificação?");
                            String destinatarios = this.scan.nextLine();
                            this.view.line("Insira a notificação desejada: ");
                            String notificacao = this.scan.nextLine();
                            this.enviarNotificacoes(notificacao,destinatarios);
                            this.criarPromocao(promo, jogo);
                        }
                    }
                    else{
                        this.view.line("Promoção inválida");
                    }


                    break;
                case 3: //enviar notificacoes
                    this.view.line("Para quem deseja enviar a notificação?");
                    String destinatarios = this.scan.nextLine();
                    this.view.line("Insira a notificação desejada: ");
                    String notificacao = this.scan.nextLine();
                    this.enviarNotificacoes(notificacao,destinatarios);
                    break;

                default:
                    this.view.line("Opção inválida!");
            }
        }
    }
    }

