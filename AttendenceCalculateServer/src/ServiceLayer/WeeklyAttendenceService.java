/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.StoredAttendenceFile;
import Models.WeeklyAttendence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vibavi
 */
public class WeeklyAttendenceService {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(WeeklyAttendenceService.class.getName());

    public WeeklyAttendenceService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    public StoredAttendenceFile[] GetWeeklyFiles(boolean isprocess) {

        try {
            ResultSet response = null;

            if (isprocess) {
                response = dbConnection.query("select * from StoredAttendenceFile where is_processed=1 and next_process_date=(select max(next_process_date) from StoredAttendenceFile where is_processed=1)");
            } else {
                response = dbConnection.query("select * from StoredAttendenceFile where is_processed=0");
            }

            String osstring = System.getProperty("os.name");
            String[] osArray = osstring.split(" ");
            String os = osArray[0];
            System.out.println("os is " + os);
            if (!response.next()) {
                return null;
            } else {
                response.first();
                response.last();
                int rsCount = response.getRow();
                response.first();
                int i = 0;
                StoredAttendenceFile[] storedFiles = new StoredAttendenceFile[rsCount];
                do {

                    String filename = response.getString("filename");
                    String startdate = response.getString("start_date");
                    String enddate = response.getString("end_date");
                    int isprocessed = response.getInt("is_processed");
                    String nextprocessdate = response.getString("next_process_date");
                    int id = response.getInt("id");
                    StoredAttendenceFile storedFile = new StoredAttendenceFile();
                    storedFile.setFileName(filename);
                    storedFile.setFilePath(filename);
                    storedFile.setIsProcessed(isprocessed);
                    storedFile.setWeekEndDate(enddate);
                    storedFile.setWeekStartDate(startdate);
                    storedFile.setNextProcessDate(nextprocessdate);
                    storedFile.setID(id);
                    if (os.equals("Windows")) {
                        storedFile.setFilePath("C:\\FingerprintFTP\\AttendenceFiles\\");
                    } else if (os.equals("Linux")) {
                        storedFile.setFilePath("/home/ftpattend/AttendenceFiles/");
                    }
                    storedFiles[i] = storedFile;
                    i++;
                } while ((response.next()));
                return storedFiles;

            }

        } catch (Exception exception) {

            return null;
        }
    }

    public boolean UpdateNextProcessDate(int id, String nextprocess) {
        try {

            int response = dbConnection.insert("update    StoredAttendenceFile set  next_process_date='" + nextprocess + "',is_processed=1 where  id=" + id);

            return response != 0;

        } catch (Exception exception) {

            return false;
        }
    }

    public boolean AddWeekendAttendence(WeeklyAttendence weekRecord) {
        try {

            int response = dbConnection.insert("insert into weeklyAttendance values('" + weekRecord.getDeviceID() + "','" + weekRecord.getWeekDate() + "','" + weekRecord.getWeekTime() + "')");

            return response != 0;

        } catch (Exception exception) {
            if (exception instanceof SQLIntegrityConstraintViolationException) {
                // System.out.println("Duplicate Student Entry Detected");
                LOGGER.log(Level.WARNING, "Duplicate Student Entry Detected");
            }
            return false;
        }
    }

    public WeeklyAttendence[] GetTodayAttendence(String date) {

        try {

            ResultSet response = dbConnection.query("select r.RegNo,r.subCode,w.wTime,w.wDate,w.DeviceID from weeklyAttendance w,register r where w.DeviceID=r.deviceID and w.wDate='" + date + "'");

            if (response == null) {
                return null;
            } else {
                response.first();
                response.last();
                int rsCount = response.getRow();
                response.first();
                int i = 0;
                WeeklyAttendence[] todayattends = new WeeklyAttendence[rsCount];
                do {

                    String deviceID = response.getString("DeviceID");
                    String wdate = response.getString("wDate");
                    String wtime = response.getString("wTime");
                    String scode = response.getString("subCode");
                    String regno = response.getString("RegNo");
                    WeeklyAttendence tempattend = new WeeklyAttendence();
                    tempattend.setDeviceID(deviceID);
                    tempattend.setWeekDate(wdate);
                    tempattend.setWeekTime(wtime);
                    tempattend.setSubCode(scode);
                    tempattend.setRegNo(regno);
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

    public WeeklyAttendence[] GetAbsenties(String subcode) {

        try {

            ResultSet response = dbConnection.query("SELECT * FROM register r LEFT JOIN weeklyAttendance w ON r.DeviceID=w.deviceID WHERE w.deviceID IS NULL and r.subCode='"+subcode+"'");

            if (response == null) {
                return null;
            } else {
                response.first();
                response.last();
                int rsCount = response.getRow();
                response.first();
                int i = 0;
                WeeklyAttendence[] todayattends = new WeeklyAttendence[rsCount];
                do {

                    String deviceID = response.getString("DeviceID");
                   
                    String scode = response.getString("subCode");
                    String regno = response.getString("RegNo");
                    WeeklyAttendence tempattend = new WeeklyAttendence();
                    tempattend.setDeviceID(deviceID);
                  
                    tempattend.setSubCode(scode);
                    tempattend.setRegNo(regno);
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
