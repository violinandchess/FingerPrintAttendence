/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author Vibavi
 */
public class LoginService {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    public LoginService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    public User ValidateUser(String username, String password) {
        try {

            ResultSet response = dbConnection.query("select * from login where username='" + username + "' and password='" + password + "'");
            if (!response.next()) {
                return null;
            } else {
                do {

                    String dbusername = response.getString("username");
                    String dbpassword = response.getString("password");
                    if (username.equals(dbusername) && dbpassword.equals(dbpassword)) {
                        User loginUser = new User();
                        loginUser.setUserName(username);
                        loginUser.setType(response.getString("type"));
                        return loginUser;
                    }

                } while ((response.next()));
                return null;

            }

        } catch (SQLException exception) {
            
            return null;
        }

    }
}
