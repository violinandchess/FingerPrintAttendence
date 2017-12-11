/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Vibavi
 */
public class ReportAttendence {
    private String StudentID;
    private String Name;
    private float AttendencePercentage;

    public ReportAttendence() {
        AttendencePercentage=0;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public float getAttendencePercentage() {
        return AttendencePercentage;
    }

    public void setAttendencePercentage(float AttendencePercentage) {
        this.AttendencePercentage = AttendencePercentage;
    }
    
}
