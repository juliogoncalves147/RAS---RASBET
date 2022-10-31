public class main{
    public static void main(String[] args) {
        System.out.println("Bem vindos ao RasBet!");

        // Inicializar a base de dados
        BaseDados bd = new BaseDados();

        // Inicializar o menu
        Menu menu = new Menu(bd);
        menu.run();

        // Fechar a base de dados
        bd.close();

        System.out.println("Obrigado por utilizar o RasBet!");

        
    }
}
