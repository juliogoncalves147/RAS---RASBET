package Model;


public interface IEspecialistaFacade{
    //inserir odds
    //atualizar odds

    void inserirOdds(String equipa1, String equipa2, String odd1, String oddx, String odd2, String idEvento);

    void atualizarOdds(String equipa1, String equipa2, String odd1, String oddx, String odd2, String idEvento);
}