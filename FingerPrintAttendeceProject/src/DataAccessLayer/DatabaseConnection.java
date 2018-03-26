/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vibavi
 */
public final class DatabaseConnection {

    public Connection conn;
    private Statement statement;
    public static DatabaseConnection db;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    private DatabaseConnection() {
        String url = "jdbc:mysql://172.18.20.210:3306/";
        String dbName = "Attendence_DB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "Admin@123";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);
        } catch (SQLException sqle) {
             LOGGER.log(Level.SEVERE, "SQLException", sqle.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "ClassException", ex.getMessage());
        }
    }

    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized DatabaseConnection getDbCon() {
        if (db == null) {
            db = new DatabaseConnection();
        }
        return db;

    }

    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not
     * available
     * @throws SQLException
     */
    public ResultSet query(String query) {
        try {
            statement = db.conn.createStatement();
            ResultSet res = statement.executeQuery(query);
            return res;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "SQLException", ex.getMessage());
            return null;

        }

    }

    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) {
        try {
            statement = db.conn.createStatement();
            int result = statement.executeUpdate(insertQuery);
            return result;
        } catch (SQLException ex) {

            LOGGER.log(Level.SEVERE, "SQLException", ex.getMessage());
            return -1;
        }

    }

    public boolean TerminateConnection() {
        try {
            db.conn.close();
            return true;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "SQLException", ex.getMessage());
            return false;
        }

    }
}
