import Controller.Controller;
import Model.EstadoJogo;
import Model.Jogo;

import javax.swing.plaf.synth.Region;
import java.util.Locale;

public class main{
    public static void main(String[] args) {
        Controller c = new Controller();
        c.run();
        System.out.println("\n\nObrigado por utilizar o RasBet!");
    }
}
