package Model;

import java.util.Date;
import java.util.LinkedHashMap;

public class Jogo {
    private String id;
    private EstadoJogo estado;
    private Date data;
    private LinkedHashMap<String, Double> odds;

    public Jogo(String id, EstadoJogo estado, Date data, LinkedHashMap<String, Double> odds) {
        this.id = id;
        this.estado = estado;
        this.data = data;
        this.odds = odds;
    }

    public String getId() {
        return id;
    }

    public EstadoJogo getEstado() {
        return estado;
    }

    public void setOdds(LinkedHashMap<String, Double> odds) {
        this.odds = odds;
    }

    public String toString(){
        //TODO: Mudar para o formato correto da data e das odds de acordo com a moeda
        return "(" + this.estado + ") - Data: " + this.data + "----- Odds: " + this.odds;
    }

}
