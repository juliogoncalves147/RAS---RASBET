package DAO.USER;

import Entidades.USER.*;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApostadorDAO extends DAO{

    public ApostadorDAO(DAO dao) {
        super(dao);
    }

    public List<Transacao> getTransacoes(String idUser) {
        ResultSet rs = this.query("SELECT date, tipo, valor FROM Transacao WHERE idUser = '" + idUser + "'");
        List<Transacao> transacoes = new ArrayList<>();
        try {
            while (rs.next()) {
                transacoes.add(new Transacao(rs.getTimestamp("date"), idUser,
                        rs.getDouble("valor"), rs.getInt("tipo")));
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return transacoes;
    }

    public List<Notificacao> getNotificacoes(String idUser) {
        List<Notificacao> notificacoes = new ArrayList<>();
        //TODO: ORDER BY DATA?
        ResultSet rs = this.query("SELECT Notificacao.id, text, idTrabalhador, (enviarTodos = '0') as Geral, (NotificacaoLida.id is not null) as Lida " +
                "FROM Notificacao " +
                "LEFT JOIN NotificacaoLida ON Notificacao.id = NotificacaoLida.id " +
                "WHERE Notificacao.idUser = '" + idUser + "' OR enviarTodos = 1 " +
                "UNION " +
                "SELECT Notificacao.id, text, idTrabalhador, (enviarTodos = '0') as Geral, (NotificacaoLida.id is not null) as Lida " +
                "FROM Notificacao " +
                "RIGHT JOIN NotificacaoLida ON Notificacao.id = NotificacaoLida.id " +
                "WHERE Notificacao.idUser = '" + idUser + "' OR enviarTodos = 1");
        try {
            while (rs.next()) {
                notificacoes.add(new Notificacao(rs.getString("id" ), rs.getString("idTrabalhador"), rs.getString("text"),
                        rs.getBoolean("Geral"), rs.getBoolean("Lida")));
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return notificacoes;
    }


    public List<Boletim> getBoletins(String idUser) {
        ResultSet rs = this.query("SELECT * FROM Boletim WHERE idUser = '" + idUser + "'" );
        List<Boletim> boletins = new ArrayList<>();
        try {
            while (rs.next()) {
                List<Prognostico> prognosticos = getProgs(rs.getString("id"));
                boletins.add(new Boletim(rs.getString("id"), rs.getString("idUser"), prognosticos,
                        EstadoBoletim.values()[rs.getInt("estado")], rs.getDate("data"),
                        rs.getDouble("montante"), rs.getDouble("ganho")));
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return boletins;
    }

    private List<Prognostico> getProgs(String id) {
        ResultSet rs = this.query("SELECT Prognostico.prognostico, Prognostico.valor, " +
                "(Promocao.id is not null) as promocao FROM Prognostico " +
                "LEFT JOIN Promocao ON Prognostico.idJogo = Promocao.idJogo AND " +
                "Prognostico.prognostico = Promocao.prognostico AND Prognostico.valor = Promocao.oddMelhorada " +
                "WHERE idBoletim =" + "'" + id + "'");
        List<Prognostico> progs = new ArrayList<>();
        try {
            while (rs.next()) {
                progs.add(new Prognostico( rs.getString("prognostico"),
                        rs.getDouble("valor"), rs.getBoolean("promocao")));
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return progs;
    }

    public void movimento(String nomeutilizador, TipoMovimento tipo, double saldo, double valor) {
        this.update("UPDATE User SET saldo = " + saldo + " WHERE id = '" + nomeutilizador + "'");
        this.update("INSERT INTO Transacao VALUES ('" + UUID.randomUUID() + "', '" +
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "', '" + nomeutilizador + "', " +
                tipo.ordinal() + ", " + valor + ")");
    }

    public Double getOdd(String id, String prognostico) {
        ResultSet ans = this.query("SELECT valor FROM Odds WHERE idJogo = '" + id + "' AND prognostico = '" + prognostico + "'");
        try {
            if (ans.next()) {
                return ans.getDouble("valor");
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean pedirAjuda(String nomeutilizador, String pedido) {
        return this.update("INSERT INTO PedidoAjuda(userApostador, data, texto) VALUES ('" + nomeutilizador
                + "', '" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "' , '" + pedido + "')");
    }

    public void insereBoletim(String nomeutilizador, double montante, List<AbstractMap.SimpleEntry<Jogo, String>> boletim) {
        String idBoletim = UUID.randomUUID().toString();
        this.update("INSERT INTO Boletim VALUES ('" + idBoletim + "', " + 0 + ", '" + nomeutilizador + "', '"
                + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "', " + montante + ", 0)");
        for (AbstractMap.SimpleEntry<Jogo, String> aposta : boletim) {
            if (aposta.getKey().getPromocoes().get(aposta.getValue()) != null) {
                this.update("INSERT INTO Prognostico VALUES ('" + idBoletim + "', '" + aposta.getKey().getId() +
                        "', '" + aposta.getValue() + "', "+ aposta.getKey().getPromocoes().get(aposta.getValue()) +")");
            }
            else {
                this.update("INSERT INTO Prognostico VALUES ('" + idBoletim + "', '" + aposta.getKey().getId() +
                        "', '" + aposta.getValue() + "', "+ aposta.getKey().getOdds().get(aposta.getValue()) +")");
            }

        }

        this.update("UPDATE User SET saldo = saldo - " + montante + " WHERE id = '" + nomeutilizador + "'");
    }

    public boolean leuNotificacao(String idNotificacao, String idUser) {
        return this.update("INSERT INTO NotificacaoLida VALUES ('" + idNotificacao + "', '" + idUser + "')");
    }
}
