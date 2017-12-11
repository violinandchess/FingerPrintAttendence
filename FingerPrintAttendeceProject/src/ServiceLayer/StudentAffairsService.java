/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author Vibavi
 */
public class StudentAffairsService {

    private final DatabaseConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    public StudentAffairsService() {
        this.dbConnection = DatabaseConnection.getDbCon();
    }

    public boolean AddStudent(Student student) {
        try {
            int respose = dbConnection.insert("insert into student  values('" + student.getRegNO() + "','" + student.getDeviceID() + "','" + student.getSName() + "')");

            return respose > 0;
        } catch (Exception exception) {

            return false;
        }

    }

    public boolean RegisterStudent(Student student,String subjectcode) {
        try {
            int respose = dbConnection.insert("insert into register  values('" + student.getRegNO() + "','" + student.getDeviceID() + "','" + subjectcode + "')");

            return respose > 0;
        } catch (Exception exception) {

            return false;
        }

    }
    
    public Student[] GetRegisteredStudents(String subcode)
    {
          try {

            ResultSet response = dbConnection.query("SELECT r.RegNo,r.deviceID,r.subCode,s.sName FROM register r ,student s  WHERE s.RegNo=r.RegNo and  subCode='"+subcode+"'");

            if (response == null) {
                return null;
            } else {
                response.first();
                response.last();
                int rsCount = response.getRow();
                response.first();
                int i = 0;
                Student[] todayattends = new Student[rsCount];
                do {

                    String deviceID = response.getString("DeviceID");
                   
                    String scode = response.getString("subCode");
                    String regno = response.getString("RegNo");
                    String sname=response.getString("sName");
                    Student tempattend = new Student();
                    tempattend.setDeviceID(deviceID);
                    tempattend.setSName(sname);
                    tempattend.setSCode(scode);
                    tempattend.setRegNO(regno);
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
