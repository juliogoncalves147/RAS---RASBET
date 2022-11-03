package Controller;

import Model.Apostador;
import Model.BaseDados;
import Model.Especialista;
import Model.Utilizador;
import View.Menu;

import java.util.Scanner;

public class ControllerEspecialista extends Controller {
    private Especialista user;
    public ControllerEspecialista(Especialista user, Controller controller) {
        this.user = user;
        this.view = controller.getView();
        this.scan = controller.getScan();
        this.db = controller.getDb();
    }



  
    public void run() {/*
        int opcao = 0;
        while (opcao != 5) {
            this.view.especialistaMainMenu(this.user.getNomeutilizador());
            opcao = this.scan.nextInt();
            switch (opcao) {
                case 1://consultar jogos
                this.getJogos();
                    break;
                case 2://inserir odd
                    this.view.line("Insira o valor a depositar: ");
                    double valor = this.scan.nextDouble();
                    ((Apostador) this.user).depositar(valor);
                    this.db.update("UPDATE User SET saldo = " + ((Apostador) this.user).getSaldo() +
                            " WHERE id = '" + this.user.getId() + "'");
                    break;
                case 3: //atualizar odd
                    this.view.line("Insira o valor a levantar: ");
                    valor = this.scan.nextDouble();
                    ((Apostador) this.user).levantar(valor);
                    this.db.update("UPDATE User SET saldo = " + ((Apostador) this.user).getSaldo() +
                            " WHERE id = '" + this.user.getId() + "'");
                    break;
                default:
                    this.view.line("Opção inválida!");
            }
        }
    */}
}
