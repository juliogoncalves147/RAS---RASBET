package Model;

public interface IApostadorFacade{
   //alterar informações perfil
    //fazer apostas
    //consultar historico de apostas
    //consultar historico de transações
    //depositar dinheiro
    //levantar dinheiro
    //consultar promoçoes
    //consultar notificações
    //fazer pedido de ajuda

    void alterarInformacoesPerfil(String nome, String email, String password, String numeroidfical, String numeroidcivil);
    void fazerAposta(int idAposta, double valor);
    void consultarHistoricoApostas();
    void consultarHistoricoTransacoes();
    void depositarDinheiro(double valor);
    boolean levantarDinheiro(double valor);
    void consultarPromocoes();
    void consultarNotificacoes();
    void fazerPedidoAjuda(String texto);

    
}