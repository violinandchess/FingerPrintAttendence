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
public class Subject {
    
    private String SubCode;
    private String Duration;
    private String SubjectStartDate;
    private String Intake;
    private String Semster;
    private String SubjectYear;
    private String SubjectName;

    public String getSubCode() {
        return SubCode;
    }

    public void setSubCode(String SubCode) {
        this.SubCode = SubCode;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String Duration) {
        this.Duration = Duration;
    }

    public String getSubjectStartDate() {
        return SubjectStartDate;
    }

    public void setSubjectStartDate(String SubjectStartDate) {
        this.SubjectStartDate = SubjectStartDate;
    }

    public String getIntake() {
        return Intake;
    }

    public void setIntake(String Intake) {
        this.Intake = Intake;
    }

    public String getSemster() {
        return Semster;
    }

    public void setSemster(String Semster) {
        this.Semster = Semster;
    }

    public String getSubjectYear() {
        return SubjectYear;
    }

    public void setSubjectYear(String SubjectYear) {
        this.SubjectYear = SubjectYear;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName = SubjectName;
    }
}
