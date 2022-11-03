package Model;

public class Promocao {
    private String id;
    private String texto;
    private String idJogo;
    private double oddMelhorada;
    private String prognostico;

    public Promocao(String id, String texto, String idJogo, double oddMelhorada, String prognostico) {
        this.id = id;
        this.texto = texto;
        this.idJogo = idJogo;
        this.oddMelhorada = oddMelhorada;
        this.prognostico = prognostico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(String idJogo) {
        this.idJogo = idJogo;
    }

    public double getOddMelhorada() {
        return oddMelhorada;
    }

    public void setOddMelhorada(double oddMelhorada) {
        this.oddMelhorada = oddMelhorada;
    }

    public String getPrognostico() {
        return prognostico;
    }

    public void setPrognostico(String prognostico) {
        this.prognostico = prognostico;
    }

    @Override

    public String toString() {
        return "Promocao [id=" + id + ", idJogo=" + idJogo + ", oddMelhorada=" + oddMelhorada + ", prognostico="
                + prognostico + ", texto=" + texto + "]";
    }
    
}
