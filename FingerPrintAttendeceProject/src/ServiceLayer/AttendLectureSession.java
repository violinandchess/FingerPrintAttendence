/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.AttendLecture;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author Vibavi
 */
public class AttendLectureSession {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    public AttendLectureSession() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    public AttendLecture[] GetAttendence(String session) {
        try {

            ResultSet response = dbConnection.query("select *  from AttendLecture   where sessionID='" + session + "'");

            if (response == null) {
                return null;
            } else {
                response.first();
                response.last();
                int rsCount = response.getRow();
                if (rsCount == 0) {
                    return null;
                }
                response.first();
                int i = 0;
                AttendLecture[] todayattends = new AttendLecture[rsCount];
                do {

                    String sessionid = response.getString("sessionID");
                    String deviceID = response.getString("deviceID");
                    String RegNo = response.getString("RegNo");
                    String IDate = response.getString("lDate");
                    String ITime = response.getString("lTime");
                    String AB_P = response.getString("AB_P");
                    ResultSet response2 = dbConnection.query("select count(*)  from AttendLecture   where sessionID='" + session + "' and AB_P='present'");
                    response2.first();
                    response2.last();
                    int rsCount2 = response.getRow();

                    response2.first();
                    if (rsCount2 != 0) {

                        AttendLecture tempattend = new AttendLecture();
                        tempattend.setSessionID(sessionid);
                        tempattend.setDeviceID(deviceID);
                        tempattend.setITime(ITime);
                        tempattend.setIDate(IDate);
                        tempattend.setRegNo(RegNo);
                        tempattend.setAB_P(AB_P);

                        todayattends[i] = tempattend;
                    }
                    i++;
                } while ((response.next()));

                return todayattends;
            }
        } catch (SQLException exception) {
            System.out.println("err" + exception);
            return null;
        }

    }
}
