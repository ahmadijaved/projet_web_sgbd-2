
package be.isl.ue.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmadi
 */

public class ConnectDB {

    private static Connection conn = null;

    public ConnectDB() {
        try {
            if (conn == null || conn.isClosed()) {
                String server = "jdbc:postgresql://titus.isl.be:5432/ue";
                String user = "ue";
                String pw = "ue2018";
                Driver driver = (Driver) Class.forName("org.postgresql.Driver").newInstance();

                conn = DriverManager.getConnection(server, user, pw);
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public static void close() {
        try {
            conn.close();
            System.out.println("Closing DB connection");
        } catch (SQLException e) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}

