/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.User;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Vibavi
 */
public class UsersService {
       private DataAccessLayer.DatabaseConnection dbconn;

    public UsersService() {
        dbconn = DatabaseConnection.getDbCon();
    } 
    public boolean AddUser(User us1){
        try {
            int respose = dbconn.insert("insert into login(username,password,type)  values('" + us1.getUserName()+ "','" + us1.getPassword()+ "','" + us1.getType()+ "')");

            return respose > 0;
        } catch (Exception exception) {
            
            return false;
        }
    }
    
    public ResultSet getData(){
        
        ResultSet rs = dbconn.query("Select username,type from login");
        return rs;
        
    }
}
