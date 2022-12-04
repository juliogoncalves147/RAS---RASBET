package DAO.API;

import Entidades.API.Bookmaker;
import Entidades.API.Jogo;
import Entidades.USER.Boletim;
import Entidades.USER.EstadoBoletim;

import java.sql.*;
import java.util.*;

public class DAOAPI {
    private Connection conn;

    public DAOAPI() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto_ras", "ras", "ras");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

    public List<Jogo> updateJogos(List<Jogo> jogos) {
        List<Jogo> jAcabados = new ArrayList<>();
        for (Jogo jogo : jogos) {
            if (jogo.getCompleted()){
                //Exemplos de scores dependendo do desporto: 1-1 Futebol; 45 - MotoGP; 6,5-4,7-2,7- Ténis
                //LIstresultados = new int[];
                String[] resultados = jogo.getScores().split("-");
                int home = Integer.parseInt(resultados[0]);
                int away = Integer.parseInt(resultados[1]);

                if (jogo.getCompleted()) {
                    if (home > away) {
                        jogo.setProgCorreto(jogo.getHomeTeam());
                    }
                    else if (home == away) {
                        jogo.setProgCorreto(jogo.getAwayTeam());
                    }
                    else {
                        jogo.setProgCorreto("Empate");
                    }
                }
            }
            String id = jogo.getId();
            String data = jogo.getCommenceTime();
            String formatada = data.substring(0, 10) + " " + data.substring(11, 19);
            int estado = jogo.getCompleted() ? 1 : 0;
            String score = jogo.getScores();
            String query = "REPLACE INTO Jogo VALUES ( ? , 'Futebol', ? ,  ? , ?, ?) ";

            try {
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, id);
                preparedStmt.setString(2, formatada);
                preparedStmt.setInt(3, estado);
                preparedStmt.setString(4, score);
                if (jogo.getProgCorreto() == null)
                    preparedStmt.setNull(5, Types.VARCHAR);
                else
                    preparedStmt.setString(5, jogo.getProgCorreto());


                //Replace faz com que elimine a linha antiga e coloque uma nova. Se não existir, cria uma nova linha.
                //Ou seja, se existir, o valor vai ser 2.
                if (preparedStmt.executeUpdate() == 2 && estado == 1)
                    jAcabados.add(jogo);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Bookmaker bookmaker = jogo.getBookmakers().get(0);
            String homeTeam = bookmaker.getMarkets().get(0).getOutcomes().get(1).getName();
            String awayTeam = bookmaker.getMarkets().get(0).getOutcomes().get(0).getName();
            String draw = bookmaker.getMarkets().get(0).getOutcomes().get(2).getName();
            double homeOdd = bookmaker.getMarkets().get(0).getOutcomes().get(1).getPrice();
            double awayOdd = bookmaker.getMarkets().get(0).getOutcomes().get(0).getPrice();
            double drawOdd = bookmaker.getMarkets().get(0).getOutcomes().get(2).getPrice();

            putOdd(conn, id, homeTeam, homeOdd);
            putOdd(conn, id, awayTeam, awayOdd);
            putOdd(conn, id, draw, drawOdd);
        }
        return jAcabados;
    }

    private void putOdd(Connection conn, String id, String team, double odd)  {
        PreparedStatement preparedStmt;
        try {
            preparedStmt = conn.prepareStatement("REPLACE INTO Odds (idJogo, prognostico, valor) values (?, ? , ?)");
            preparedStmt.setString(1, id);
            preparedStmt.setString(2, team);
            preparedStmt.setDouble(3, odd);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Boletim> updateApostas(List<Jogo> jAcabados) {
        Set<String> boletins = new HashSet<>();

        for (Jogo j : jAcabados) {
            try {
                PreparedStatement stat = conn.prepareStatement("SELECT idBoletim FROM Prognostico WHERE idJogo = ?");
                stat.setString(1, j.getId());
                ResultSet rs = stat.executeQuery();
                if (rs.next()) {
                    boletins.add(rs.getString("idBoletim"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        List<Boletim> boletinsVencedores = new ArrayList<>();
        for (String id : boletins.stream().toList()) {
            try {
                PreparedStatement bAtivos = conn.prepareStatement("SELECT COUNT(*) FROM Jogo INNER JOIN Prognostico ON id=idJogo WHERE Jogo.estado=0 AND Prognostico.idBoletim=?");
                bAtivos.setString(1, id);
                ResultSet rsBA = bAtivos.executeQuery();
                if (rsBA.next()) {
                    if (rsBA.getInt(1) == 0) {
                        PreparedStatement bFechado;
                        if (!perdido(id)) {
                            Boletim b = getBoletim(id);
                            if (b == null) continue;
                            boletinsVencedores.add(b);
                            bFechado = conn.prepareStatement("UPDATE Boletim SET estado=?, ganho=? WHERE id=?");
                            bFechado.setDouble(2, b.getGanho());
                            bFechado.setString(3, id);
                        } else {
                            bFechado = conn.prepareStatement("UPDATE Boletim SET estado=? WHERE id=?");
                            bFechado.setString(2, id);
                        }
                        bFechado.setInt(1, EstadoBoletim.FECHADO.ordinal());
                        bFechado.executeUpdate();

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return boletinsVencedores;
    }

    private Boletim getBoletim(String id) {
        try {
            PreparedStatement p = conn.prepareStatement("SELECT * from Boletim INNER JOIN Prognostico on Prognostico.idBoletim = Boletim.id WHERE id=?");
            p.setString(1, id);
            ResultSet rs = p.executeQuery();
            Boletim b = null;
            double ganho = 1.0;
            while (rs.next()) {
                if (b == null) {
                    b = new Boletim(rs.getString("id"), rs.getString("idUser"), new ArrayList<>(), EstadoBoletim.values()[rs.getInt("estado")],
                            rs.getDate("data"), rs.getDouble("montante"), rs.getDouble("ganho"));
                }
                ganho *= rs.getDouble("valor");
            }
            if (b != null) b.setGanho(ganho);
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean perdido(String id) {
        try {
            PreparedStatement bPerdidos = conn.prepareStatement("SELECT * FROM Boletim " +
                    "INNER JOIN Prognostico ON Prognostico.idBoletim = Boletim.id " +
                    "INNER JOIN Jogo ON Prognostico.idJogo=Jogo.id " +
                    "WHERE Prognostico.prognostico != Jogo.progCorreto AND Boletim.id = ?");
            bPerdidos.setString(1, id);
            ResultSet rsBP = bPerdidos.executeQuery();
            return rsBP.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void updateUsers(List<Boletim> vencedores) {
        for (Boletim b : vencedores){
            try{
                PreparedStatement p = conn.prepareStatement("UPDATE User SET saldo = saldo + ? WHERE id = ?");
                p.setDouble(1, b.getGanho());
                p.setString(2, b.getIdUser());
                p.executeUpdate();

                //Enviar notificação
                p = conn.prepareStatement("INSERT INTO Notificacao VALUES (?, 0, ?, ?, NULL)");
                p.setString(1, UUID.randomUUID().toString());
                p.setString(2, b.getIdUser());
                p.setString(3, "Parabéns! Ganhou " + b.getGanho() + "€ com o boletim:\n" + b);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
