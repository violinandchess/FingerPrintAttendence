/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.AttendLecture;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vibhavi
 */
public class AttendLectureService {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(WeeklyAttendenceService.class.getName());

    public AttendLectureService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    
      public boolean AddWeekendAttendence(AttendLecture attendrecord) {
       
          try {

            int response = dbConnection.insert("insert into AttendLecture values('" + attendrecord.getRegNo()+ "','" + attendrecord.getDeviceID()+ "','" + attendrecord.getSessionID()+ "','"+attendrecord.getIDate()+"','"+attendrecord.getITime()+"','"+attendrecord.getAB_P()+"')");

            return response != 0;

        } catch (Exception exception) {
           
            if (exception instanceof SQLIntegrityConstraintViolationException) {
                // System.out.println("Duplicate Student Entry Detected");
                LOGGER.log(Level.WARNING, "Duplicate Student Entry Detected");
            }
            return false;
        }
    }

}
