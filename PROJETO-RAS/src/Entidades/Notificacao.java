package Entidades;

public class Notificacao {
    private String id;
    private String texto;

    public Notificacao(String id, String texto) {
        this.id = id;
        this.texto = texto;
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

    @Override

    public String toString() {
        return "Notificacao{" + "id=" + id + ", texto=" + texto + '}';
    }
}
