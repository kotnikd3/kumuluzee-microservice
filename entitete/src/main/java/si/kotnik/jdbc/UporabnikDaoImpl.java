package si.kotnik.jdbc;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao {
    private static UporabnikDaoImpl instance = null;
    private static final Logger log = Logger.getLogger(UporabnikDaoImpl.class.getName());

    private Connection connection;

    private UporabnikDaoImpl() {
        connection = getConnection();
    }

    public static UporabnikDaoImpl getInstance() {
        if (instance == null) {
            instance = new UporabnikDaoImpl();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource)initCtx.lookup("jdbc/SimpleJdbcDS");
            return ds.getConnection();
        } catch (Exception e) {
            log.severe("Cannot get connection: " + e.toString());
        }
        return null;
    }

    @Override
    public Entiteta vrni(int id) {
        PreparedStatement ps = null;

        try {
            if (connection == null) {
                connection = getConnection();
            }

            String sql = "SELECT * FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getUporabnikFromRS(rs);
            } else {
                log.info("Uporabnik ne obstaja");
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe("Error closing connection: " + e.toString());
                }
            }
        }
        return null;
    }

    @Override
    public void vstavi(Entiteta ent) {
        PreparedStatement ps = null;

        try {
            if (connection == null) {
                connection = getConnection();
            }
            String sql = "INSERT INTO uporabnik (ime, priimek, uporabniskoime) VALUES(?, ?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, ((Uporabnik) ent).getIme());
            ps.setString(2, ((Uporabnik) ent).getPriimek());
            ps.setString(3, ((Uporabnik) ent).getUporabniskoime());

            int results = ps.executeUpdate();
            if (results > 0) {
                log.info("User " + ((Uporabnik) ent).getUporabniskoime() + " inserted.");
            } else {
                log.info("User " + ((Uporabnik) ent).getUporabniskoime() + " NOT inserted.");
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe("Error closing connection: " + e.toString());
                }
            }
        }
    }

    @Override
    public void odstrani(int id) {
        PreparedStatement ps = null;

        try {
            if (connection == null) {
                connection = getConnection();
            }
            String sql = "DELETE FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int results = ps.executeUpdate();
            if (results > 0) {
                log.info("User with ID " + id + " deleted.");
            } else {
                log.info("User with ID " + id + " NOT deleted.");
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe("Error closing connection: " + e.toString());
                }
            }
        }
    }

    @Override
    public void posodobi(Entiteta ent) {
        PreparedStatement ps = null;

        try {
            if (connection == null) {
                connection = getConnection();
            }
            String sql = "SELECT * FROM uporabnik WHERE uporabniskoime = ?";
            ps = connection.prepareStatement(sql);

            ps.setString(1, ((Uporabnik) ent).getUporabniskoime());

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                Uporabnik star = getUporabnikFromRS(result);
                int id_star = result.getInt("id");

                String sql_update = "UPDATE uporabnik SET ime = ? WHERE ID = ?";
                ps = connection.prepareStatement(sql_update);
                ps.setString(1, star.getIme() + "_dodatno");
                ps.setInt(2, id_star);

                int result_2 = ps.executeUpdate();
                if (result_2 > 0) {
                    log.info("User with ID " + id_star + " updated.");
                } else {
                    log.info("User with ID " + id_star + " NOT updated.");
                }

            } else {
                log.info("User with username " + ((Uporabnik) ent).getUporabniskoime() + " DOES NOT exists.");
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe("Error closing connection: " + e.toString());
                }
            }
        }
    }

    @Override
    public List<Entiteta> vrniVse() {
        List<Entiteta> uporabniki = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            if (connection == null) {
                connection = getConnection();
            }

            String sql = "SELECT * FROM uporabnik";
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                uporabniki.add(getUporabnikFromRS(rs));
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe("Error closing connection: " + e.toString());
                }
            }
        }
        return uporabniki;
    }

    private Uporabnik getUporabnikFromRS(ResultSet rs) throws SQLException {
        String ime = rs.getString("ime");
        String priimek = rs.getString("priimek");
        String uporabniskoIme = rs.getString("uporabniskoime");
        return new Uporabnik(ime, priimek, uporabniskoIme);
    }
}
