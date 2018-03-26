/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import DataAccessLayer.DatabaseConnection;
import Models.Subject;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Vibavi
 */
public class SubjectService {

    private DataAccessLayer.DatabaseConnection dbconn;

    public SubjectService() {
        dbconn = DatabaseConnection.getDbCon();
    }

    public boolean AddSubject(Subject subject) {
        try {
            int respose = dbconn.insert("insert into subject  values('" + subject.getSubCode() + "','" + subject.getDuration() + "','" + subject.getSubjectStartDate() + "','" + subject.getIntake() + "','" + subject.getSemster() + "','" + subject.getSubjectYear() + "','" + subject.getSubjectName() + "')");

            return respose > 0;
        } catch (Exception exception) {

            return false;
        }

    }

}
