package Controller;

import Model.*;
import View.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ControllerEspecialista extends Controller {
    private Especialista user;
    public ControllerEspecialista(Especialista user, Controller controller) {
        this.user = user;
        this.view = controller.getView();
        this.scan = controller.getScan();
        this.db = controller.getDb();
    }

    public void run() {
        int opcao = 0;
        while (opcao != 5) {
            this.view.especialistaMainMenu(this.user.getNomeutilizador());
            opcao = this.scan.nextInt();
            switch (opcao) {
                case 1:
                    // 1. Consultar Jogos
                    consultarJogos();
                    break;
                case 2:
                    // 2. Inserir odd
                    inserirOdd();
                    break;
                case 3:
                    // 3 Atualizar odd
                    atualizarOdd();
                    break;
                case 0: //Terminar sessão
                    this.view.line("A terminar sessão...");
                    return;

                default:
                    this.view.line("Opção inválida!");
                    break;
            }
        }
    }

    String getPrognostico(ResultSet odds, int posicaoPrognostico) {
        String result = "";


        // Itera ate indexPrognostico
        for(int i = 0; i < posicaoPrognostico; i++) {

            try {
                odds.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        // Associa prognostico
        try {
            result = odds.getString("prognostico");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Reset posicao cursor
        try {
            odds.beforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    void consultarJogos() {

        // 1.1 Escolhe um desporto
        List<String> menuDesportos = this.getMenuDesportos();
        this.view.optionsMenu(menuDesportos.toArray(new String[0]));
        this.view.line("Insira o id do desporto: ");
        int idDesporto = this.scan.nextInt();
        String desporto = menuDesportos.get(idDesporto - 1).split(" - ")[1];

        // 1.2 Lista jogos do desporto
        List<Jogo> jogos = this.getJogos(desporto);
        List<String> menuJogos = this.getMenuJogos(jogos);
        this.view.optionsMenu(menuJogos.toArray(new String[0]));

    }

    void atualizarOdd() {
        // 3.1 Escolhe um desporto
        List<String> menuDesportos = this.getMenuDesportos();
        this.view.optionsMenu(menuDesportos.toArray(new String[0]));
        this.view.line("Insira o id do desporto: ");
        int idDesporto = this.scan.nextInt();
        String desporto = menuDesportos.get(idDesporto - 1).split(" - ")[1];

        // 3.2 Lista jogos do desporto
        List<Jogo> jogos = this.getJogos(desporto);
        List<String> menuJogos = this.getMenuJogos(jogos);
        this.view.optionsMenu(menuJogos.toArray(new String[0]));

        String idJogo = "";
        // 3.3 Input id jogo
        //Integer idJogo = -1;
        boolean existe = false;
        while(!existe) { // Caso nao exista, tenta novamente
            // 3.3.1 Scan Id Jogo
            this.view.line("Porfavor introduza o id do jogo que pretende alterar: ");
             idJogo = this.scan.nextLine();

            // 3.3.2 Verifica existencia idJogo
            for (Jogo j : jogos) {
                if (j.getId().equals(idJogo))
                    existe = true;
            }
        }

        // 3.4 Print odds
        ResultSet rs = null;
        rs = this.db.query("SELECT * FROM Odds WHERE idJogo='" + idJogo + "'");
        try {
            this.view.printOdds(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // 3.5 Select odd to change
        this.view.line("Qual o prognostico que deseja alterar?");
        int posicaoPrognostico = this.scanOption(1,3);
        String prognosticoString = getPrognostico(rs, posicaoPrognostico);


        // 3.6 Change odd
        this.view.line("Introduza nova odd para " + prognosticoString + ":");
        float newOdd = this.scan.nextFloat();
        this.db.update("UPDATE Odds SET valor="+newOdd+" WHERE idJogo='"
                +idJogo+"' AND prognostico='" + prognosticoString+"';");


        // 3.7 Confirmacao mudanca
        System.out.println("A odd foi alterada com sucesso!");

    }

    void inserirOdd() {
        // 2.1 Escolhe um desporto
        List<String> menuDesportos = this.getMenuDesportos();
        this.view.optionsMenu(menuDesportos.toArray(new String[0]));
        this.view.line("Insira o id do desporto: ");
        int idDesporto = this.scan.nextInt();
        String desporto = menuDesportos.get(idDesporto - 1).split(" - ")[1];

        // 2.2 Lista jogos (sem odd) do desporto
        List<Jogo> jogos = this.getJogosSemOdd(desporto);
        if(jogos.isEmpty()) {
            this.view.line("Nao ha jogos sem odds neste desporto...");
            return;
        }

        //
        List<String> menuJogos = this.getMenuJogos(jogos);
        this.view.optionsMenu(menuJogos.toArray(new String[0]));

        // 2.3 Input id jogo a inserir odd
        Integer idJogo = -1;
        boolean existe = false;
        while(!existe) { // Caso nao exista, tenta novamente
            // 2.3.1 Scan Id Jogo
            this.view.line("Porfavor introduza o id do jogo que pretende inserir odd: ");
            idJogo = this.scan.nextInt();

            // 2.3.2 Verifica existencia idJogo
            for (Jogo j : jogos) {
                if (j.getId().equals(idJogo.toString()))
                    existe = true;
            }
        }

        // 2.4 Insere odds
        this.view.line("Introduza <EquipaA>");
        String equipaA = this.scan.next();
        this.view.line("Introduza <OddA>");
        int oddA = this.scan.nextInt();
        String empate = "Empate";
        this.view.line("Introduza <OddEmpate>");
        int oddE = this.scan.nextInt();
        this.view.line("Introduza <EquipaB>");
        String equipaB = this.scan.next();
        this.view.line("Introduza <OddB>");
        int oddB = this.scan.nextInt();

        // Insert Equipa A
        this.db.update("INSERT INTO Odds values('"+idJogo+"','"+equipaA+"',"+oddA+");");
        // Insert Equipa B
        this.db.update("INSERT INTO Odds values('"+idJogo+"','"+equipaB+"',"+oddB+");");
        // Insert Empate
        this.db.update("INSERT INTO Odds values('"+idJogo+"','"+empate+"',"+oddE+");");

        this.view.line("Odds inseridas com sucesso!");
    }
}
