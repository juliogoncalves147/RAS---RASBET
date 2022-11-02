package Model;

public interface IAdministradorFacade {
    
    void alterarEstadoApostas(String estado, int idAposta);

    void criarPromocao(String texto, String tipo, String dataInicio, String dataFim, String valor);

    void enviarNotificacoes(String texto, String destinatarios);

}



