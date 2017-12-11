/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author Avishka
 */
public class SessionService {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    public SessionService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    public boolean AddSession(Session session) {
        try {
            int respose = dbConnection.insert("insert into lectureSession  values('" + session.getSessionID() + "','" + session.getStartTime() + "','" + session.getEndTime() + "','" + session.getStartDate() + "','" + session.getBatch()+ "','"+session.getCode()+"')");

            return respose > 0;
        } catch (Exception exception) {

            return false;
        }

    }

    public Session[] GetTotalSessions(String scode) {
        try {

            ResultSet response = dbConnection.query("select *  from lectureSession   where code='" + scode + "'");
            
            if (response == null) {
                return null;
            } else {
                response.first();
                response.last();
                int rsCount = response.getRow();
                response.first();
                int i = 0;
                Session[] todayattends = new Session[rsCount];
                
                do {

                    String sessionid = response.getString("sessionID");
                    String starttime = response.getString("start_time");
                    String endtime = response.getString("end_time");
                
                    String weekstartdate = response.getString("lecture_date");
                    
                    String wdwe = response.getString("WD_WE");
                    String code = response.getString("code");
                 
                    Session tempattend = new Session(sessionid,starttime,endtime,code);
                    tempattend.setSessionID(sessionid);
                    tempattend.setBatch(wdwe);
                    tempattend.setStartDate(weekstartdate);
                    
                
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
    
    public boolean UpdateSession(Session session)
    {
         try {
            int respose = dbConnection.insert("update  lectureSession  set start_time='" + session.getStartTime()+ "',end_time='" + session.getEndTime()+ "',lecture_date='" +  session.getStartDate()+ " where sessionID='"+session.getSessionID()+"';");
                    

            return respose > 0;
        } catch (Exception exception) {

            return false;
        }
    }
       public String GetSessionID(String date,String code)
    {
         try {
            ResultSet respose = dbConnection.query("select sessionID from  lectureSession where lecture_date='"+date+"' and  code='"+code+"'");
 
            return respose.getString("sessionID");
        } catch (Exception exception) {

            return null;
        }
    }

}
