 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vibavi
 */
public class StoredAttendenceFileService {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    public StoredAttendenceFileService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    public boolean AddAttendenceFile(String filename, String startingDate, String endingDate) {
        try {

            String startDateString = endingDate;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date nextDate = new Date();
            try {
                // dt is now the new date
                nextDate = df.parse(startDateString);
                Calendar c = Calendar.getInstance();
                c.setTime(nextDate);
                c.add(Calendar.DATE, 1);  // number of days to add
                nextDate = c.getTime();
                String newDateString = df.format(nextDate);
                System.out.println(newDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int respose = dbConnection.insert("insert into StoredAttendenceFile(start_date,end_date,filename)  values ('" + startingDate + "','" + endingDate + "','" + filename + "')");

            return respose > 0;

        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "FileInsertException", exception.getMessage());
            return false;

        }

    }

    public boolean CheckMultipleFiles(String startingDate, String endingDate) {
        try {

            ResultSet response = dbConnection.query("select * from StoredAttendenceFile where start_date>='" + startingDate + "' and end_date<='" + endingDate + "'");

            response.last();
            int rsCount = response.getRow();
            return rsCount > 0;
        } catch (SQLException exception) {
            return false;
        }

    }

}
