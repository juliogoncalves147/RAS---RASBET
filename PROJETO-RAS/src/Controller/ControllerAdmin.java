package Controller;

import Model.Administrador;
import Model.Apostador;


public class ControllerAdmin extends Controller {
    private Administrador user;
    public ControllerAdmin(Administrador user){
        this.user = user;
    }

    public void run() {
        /*int opcao = 0;
        while (opcao != 5) {
            //this.view.menuAdmin();
            opcao = this.scan.nextInt();
            switch (opcao) {
                case 1:
                    this.view.line("Saldo: " + ((Apostador) this.user).getSaldo());
                    break;
                case 2:
                    this.view.line("Insira o valor a depositar: ");
                    double valor = this.scan.nextDouble();
                    ((Apostador) this.user).depositar(valor);
                    this.db.update("UPDATE User SET saldo = " + ((Apostador) this.user).getSaldo() +
                            " WHERE id = '" + this.user.getId() + "'");
                    break;
                case 3:
                    this.view.line("Insira o valor a levantar: ");
                    valor = this.scan.nextDouble();
                    ((Apostador) this.user).levantar(valor);
                    this.db.update("UPDATE User SET saldo = " + ((Apostador) this.user).getSaldo() +
                            " WHERE id = '" + this.user.getId() + "'");
                    break;
                case 4:
                    this.view.line("Insira o valor a apostar: ");
                    valor = this.scan.nextDouble();
                    this.view.line("Insira o id do evento: ");
                    String idEvento = this.scan.next();
                    this.view.line("Insira o id da equipa: ");
                    String idEquipa = this.scan.next();
                    ((Apostador) this.user).apostar(valor, idEvento, idEquipa);
                    this.db.update("UPDATE User SET saldo = " + ((Apostador) this.user).getSaldo() +
                            " WHERE id = '" + this.user.getId() + "'");
                    break;
                case 5:
                    this.view.line("A sair...");
                    break;
                default:
                    this.view.line("Opção inválida!");
                    break;
            }
        }*/
    }
}
