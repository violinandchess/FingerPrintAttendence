/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.LectureSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author Vibhavi
 */
public class SessionService {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(WeeklyAttendenceService.class.getName());

    public SessionService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    public LectureSession[] GetTodaySession(String date) {
        try {

            ResultSet response = dbConnection.query("select *  from lectureSession  where lecture_date='"+date+"'");

            if (response == null) {
                return null;
            } else {
                response.first();
                response.last();
                int rsCount = response.getRow();
                response.first();
                int i = 0;
                LectureSession[] todayattends = new LectureSession[rsCount];
                do {

                    String sessionid = response.getString("sessionID");
                    String starttime = response.getString("start_time");
                    String endtime = response.getString("end_time");
               
                    String wdwe = response.getString("WD_WE");
                    String code = response.getString("code");
                    String lecturedate = response.getString("lecture_date");

                    LectureSession tempattend = new LectureSession();
                    tempattend.setSessionID(sessionid);
                    tempattend.setStartTime(starttime);
                    tempattend.setEndTime(endtime);
                
                    tempattend.setLectureDay(lecturedate);
                    tempattend.setWD_WE(wdwe);
                    tempattend.setCode(code);
               
                    todayattends[i] = tempattend;
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
