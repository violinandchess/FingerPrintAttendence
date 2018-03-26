/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.Attendence;

import java.util.logging.Logger;

/**
 *
 * @author Malik
 */
public class ManualStudentAttendenceService {
     private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());
    
     public ManualStudentAttendenceService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }
     
    public boolean AddAttendence(Attendence session) {
        try {
            int respose = dbConnection.insert("insert into weeklyAttendance  values('" + session.getDeviceId()+ "','" + session.getDate()+ "','" + session.getTime()+"')");

            return respose > 0;
        } catch (Exception exception) {
            
            return false;
        } 
    }
    }

