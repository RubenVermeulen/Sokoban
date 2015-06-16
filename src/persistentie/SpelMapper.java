package persistentie;

import domein.Kist;
import domein.Mannetje;
import domein.Veld;
import domein.Spel;
import domein.Spelbord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpelMapper {

    public List<Spel> geefSpellen() {
        List<Spel> spelLijst = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("SELECT naam FROM spel");

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {

                    String naam = rs.getString("naam");

                    spelLijst.add(new Spel(naam));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return spelLijst;
    }

    public Spel geefSpel(String spelNaam) {
        Spel spel = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("SELECT naam FROM spel WHERE naam LIKE BINARY ?");
            query.setString(1, spelNaam);

            try (ResultSet rs = query.executeQuery()) {

                if (rs.next()) {
                    String naam = rs.getString("naam");

                    spel = new Spel(naam);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Fout RuntimeException in de SpelMapper (methode = geefSpel)");
            throw new RuntimeException(ex);
        }

        return spel;
    }

    public void voegSpelToe(Spel spel) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            PreparedStatement invoerSpel = conn.prepareStatement("INSERT INTO spel (naam) " + "VALUES (?)");
            invoerSpel.setString(1, spel.getNaam());
            invoerSpel.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void voegKistToe(Spelbord spelbord, Veld veld) {
        int idVeld;

        PreparedStatement invoerKist;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            // Veld id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM veld WHERE spelbord_id = ?");
            querySpel.setInt(1, spelbord.getId());

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idVeld = rs.getInt("id");
            }

            // Veld id en Spelbord id toevoegen aan kist in DB
            invoerKist = conn.prepareStatement("INSERT INTO kist (veld_id, spelbord_id)" + "VALUES (?, ?)");
            invoerKist.setInt(1, idVeld);
            invoerKist.setInt(2, spelbord.getId());
            invoerKist.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void wijzigKist(Spelbord spelbord, Veld veld) {
        int idVeld;

        PreparedStatement wijzigKist;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            // Veld id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM veld WHERE spelbord_id = ?");
            querySpel.setInt(1, spelbord.getId());

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idVeld = rs.getInt("id");
            }

            wijzigKist = conn.prepareStatement("UPDATE kist SET (veld_id, spelbord_id)" + "VALUES (?, ?)");
            wijzigKist.setInt(1, idVeld);
            wijzigKist.setInt(2, spelbord.getId());
            wijzigKist.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void voegSpelbordToe(Spel spel) {
        int idSpel;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            // Spel id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM spel WHERE naam = ?");
            querySpel.setString(1, spel.getNaam());

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idSpel = rs.getInt("id");
            }

            //Spel id toevoegen aan spelbord in DB
            PreparedStatement invoerSpelbord = conn.prepareStatement("INSERT INTO spelbord (spel_id)" + "VALUES (?)");
            invoerSpelbord.setInt(1, idSpel);//spel_id
            invoerSpelbord.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void verwijderSpelbord(String spelNaam) {
        int idSpel;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            // Spel id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM spel WHERE naam = ?");
            querySpel.setString(1, spelNaam);

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idSpel = rs.getInt("id");
            }
            PreparedStatement verwijderSpelbord = conn.prepareStatement("DELETE FROM spelbord WHERE spel_id = ?");
            verwijderSpelbord.setInt(1, idSpel);
            verwijderSpelbord.executeQuery();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void wijzigSpelbord(String spelNaam) {
        int idSpel;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            // Spel id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM spel WHERE naam = ?");
            querySpel.setString(1, spelNaam);

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idSpel = rs.getInt("id");
            }
            PreparedStatement wijzigSpelbord = conn.prepareStatement("UPDATE spelbord SET spel_id = ?");
            wijzigSpelbord.setInt(1, idSpel);
            wijzigSpelbord.executeQuery();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void voegMannetjeToe(Spelbord spelbord, Spel spel) {
        int idVeld;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            // Veld id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM veld WHERE spelbord_id = ?");
            querySpel.setInt(1, spelbord.getId());

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idVeld = rs.getInt("id");
            }

            // Veld id en Spelbord id toevoegen aan mannetje in DB
            PreparedStatement invoerMannetje = conn.prepareStatement("INSERT INTO mannetje (veld_id, spelbord_id)" + "VALUES (?, ?)");
            invoerMannetje.setInt(1, idVeld);
            invoerMannetje.setInt(2, spelbord.getId());
            invoerMannetje.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
        public void wijzigMannetje(Spelbord spelbord, Spel spel) {
        int idVeld;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            // Veld id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM veld WHERE spelbord_id = ?");
            querySpel.setInt(1, spelbord.getId());

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idVeld = rs.getInt("id");
            }

            
            PreparedStatement wijzigMannetje = conn.prepareStatement("UPDATE mannetje SET (veld_id, spelbord_id)" + "VALUES (?, ?)");
            wijzigMannetje.setInt(1, idVeld);
            wijzigMannetje.setInt(2, spelbord.getId());
            wijzigMannetje.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
        
    public void voegVeldToe(Spel spel, Spelbord spelbord, Veld veld) {
        int idSpel, idSpelbord;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            
//            String s ="";
//            int x = 1;
//            for(Veld v : veld){
//                s += "VALUES ( '"+"' v.getX() '"+"' v.getY() '"+"' v.getDoel())";
//                if (x < veld.length){
//                    s+=", ";
//                }
//                x++;                
//            }
//            s = "VALUES(), VALUES(), VALUES()";
            
            // Spel id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM spel WHERE naam = ?");
            querySpel.setString(1, spel.getNaam());

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idSpel = rs.getInt("id");
            }
            
            // Spelbord id ophalen
            PreparedStatement querySpelbord = conn.prepareStatement("SELECT id FROM spelbord WHERE spel_id = ?");
            querySpel.setInt(1, idSpel);

            try (ResultSet rs = querySpelbord.executeQuery()) {
                rs.next();
                idSpelbord = rs.getInt("id");
            }

            PreparedStatement invoerVeld = conn.prepareStatement("INSERT INTO veld (spelbord_id, x, y, doel)" + "VALUES (?, ?, ?, ?)");
            invoerVeld.setInt(1, idSpelbord);
            invoerVeld.setInt(2, veld.getX());
            invoerVeld.setInt(3, veld.getY());
            invoerVeld.setBoolean(2, veld.getDoel());
            invoerVeld.executeUpdate();
            

            
//            INSERT INTO velden(x, x, x) VALUES() VALUES() VALUES();
//            String s = "";
//            int x = 1;
//            
//            for (Veld v : velden)
//            {
//                s += "VALUES(" + v.getX() + + +")";
//
//                if (x < velden.length)
//                {
//                    s += ", ";
//                }
//
//                x++;
//
//            }
//
//            s = "VALUES(), VALUES(), VALUES()"
//
//            String sql = "INSERT INTO veld " + s;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void voegVeldenToe(Spelbord spelbord, Veld[][] velden) {
        
        /* **************************************************************************************
        * RUBEN HEEFT DE METHODE UITEINDELIJK GEMAAKT ZODAT DE VELDEN WORDEN WEGGESCHREVEN      *
        * EBU EN MARTIN KUNNEN DIT GEBRUIKEN ALS VOORBEELD                                      *
        *****************************************************************************************/
        
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            // Spelbord id ophalen van laatst toegevoegd spelbord
            int spelbordId;
            
            PreparedStatement querySpelbord = conn.prepareStatement("SELECT id FROM spelbord ORDER BY id DESC LIMIT 1");

            try (ResultSet rs = querySpelbord.executeQuery()) {
                rs.next();
                spelbordId = rs.getInt("id");
            }
            
            // Dubbele array van velden overlopen
            for (Veld[] rij : velden) {                
                for(Veld veld : rij) {
                    if(veld instanceof Veld) { // Controleren of de waarde een object veld bevat
                        String query = "INSERT INTO veld (spelbord_id, x, y, doel) VALUES(?,?,?,?)";
                        
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setInt(1, spelbordId);
                        stmt.setInt(2, veld.getX());
                        stmt.setInt(3, veld.getY());
                        stmt.setBoolean(4, veld.isDoel());
                        
                        stmt.executeUpdate();
                    }
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
        public void wijzigVeld(Spelbord spelbord, Veld veld) {

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            PreparedStatement wijzigVeld = conn.prepareStatement("UPDATE veld SET (spelbord_id, x, y, doel)" + "VALUES (?, ?, ?, ?)");
            wijzigVeld.setInt(1, spelbord.getId());
            wijzigVeld.setInt(2, veld.getX());
            wijzigVeld.setInt(3, veld.getY());
            wijzigVeld.setBoolean(2, veld.getDoel());
            wijzigVeld.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Spelbord> geefSpelborden(String naam) {
        List<Spelbord> spelborden = new ArrayList<>();
        int idSpel;
        int idSpelbord;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            // Spel id ophalen
            PreparedStatement querySpel = conn.prepareStatement("SELECT id FROM spel WHERE naam = ?");
            querySpel.setString(1, naam);

            try (ResultSet rs = querySpel.executeQuery()) {
                rs.next();
                idSpel = rs.getInt("id");
            }

            // Spelborden met hun id's ophalen
            PreparedStatement querySpelbord = conn.prepareStatement("SELECT id FROM spelbord WHERE spel_id = ? ORDER BY id ASC");
            querySpelbord.setInt(1, idSpel);

            try (ResultSet rs = querySpelbord.executeQuery()) {
                while (rs.next()) {
                    idSpelbord = rs.getInt("id");
                    spelborden.add(new Spelbord(idSpelbord));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return spelborden;
    }

    public void initialiseerSpelbord(Spelbord spelbord) {
        int grootte = 10;
        Veld[][] velden = new Veld[grootte][grootte];
        List<Integer> veldIdKisten = new ArrayList<>(); //slaat de id's op van de velden waar elke kist staat
        List<Kist> kisten = new ArrayList<>();
        Mannetje mannetje = null;
        int veldIdMannetje;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            // Velden ophalen die bij het Spelbord object horen
            PreparedStatement queryVeld = conn.prepareStatement("SELECT x, y, doel FROM veld WHERE spelbord_id = ?");
            queryVeld.setInt(1, spelbord.getId());

            try (ResultSet rs = queryVeld.executeQuery()) {
                while (rs.next()) {
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");

                    velden[x][y] = new Veld(
                            x,
                            y,
                            rs.getBoolean("doel")
                    );
                }
            }

            // Mannetje ophalen die bij het Spelbord object hoort
            PreparedStatement queryVeldIdMannetje = conn.prepareStatement("SELECT veld_id FROM mannetje WHERE spelbord_id = ?");
            queryVeldIdMannetje.setInt(1, spelbord.getId());

            try (ResultSet rs = queryVeldIdMannetje.executeQuery()) {
                rs.next();
                veldIdMannetje = rs.getInt("veld_id");
            }

            // Veldobject kent nu mannetjeobject en omgekeerd
            PreparedStatement queryMannetje = conn.prepareStatement("SELECT x, y FROM veld WHERE id = ?");
            queryMannetje.setInt(1, veldIdMannetje);

            try (ResultSet rs = queryMannetje.executeQuery()) {
                rs.next();
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                Veld veld;

                for (int i = 0; i < velden.length; i++) {
                    for (int j = 0; j < velden[i].length; j++) {
                        veld = velden[i][j];

                        if (veld != null) { // Controleren of er een veld object in zit of niet
                            if (veld.getX() == x && veld.getY() == y) {
                                mannetje = new Mannetje(veld);
                                veld.setMannetje(mannetje);
                            }
                        }
                    }
                }
            }

            // Kisten ophalen die bij het Spelbord object behoren
            PreparedStatement queryVeldIdKisten = conn.prepareStatement("SELECT veld_id FROM kist WHERE spelbord_id = ?");
            queryVeldIdKisten.setInt(1, spelbord.getId());

            try (ResultSet rs = queryVeldIdKisten.executeQuery()) {
                while (rs.next()) {
                    veldIdKisten.add(
                            rs.getInt("veld_id")
                    );
                }
            }

            // Kisten linken aan veld en omgekeerd
            Statement stmt = null;
            for (int veldIdKist : veldIdKisten) {
                stmt = conn.createStatement();
                String query = "SELECT x, y FROM veld WHERE id = " + veldIdKist;

                try (ResultSet rs = stmt.executeQuery(query)) {
                    rs.next();
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    Veld veld;

                    for (int i = 0; i < velden.length; i++) {
                        for (int j = 0; j < velden[i].length; j++) {
                            veld = velden[i][j];

                            if (veld != null) { // Controleren of er een veld object in zit of niet
                                if (veld.getX() == x && veld.getY() == y) {
                                    Kist kist = new Kist(veld);
                                    veld.setKist(kist);
                                    kisten.add(kist);
                                }
                            }

                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        spelbord.setMannetje(mannetje);
        spelbord.setKisten(kisten);
        spelbord.setVelden(velden);
    }
}
