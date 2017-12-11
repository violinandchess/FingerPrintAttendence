/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Vibhavi
 */
public class LectureSession {
    
    private String SessionID;
    private String StartTime;
    private String EndTime;
    private String WD_WE;
    private String Code;
    private String LectureDay;

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String SessionID) {
        this.SessionID = SessionID;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getLectureDay() {
        return LectureDay;
    }

    public void setLectureDay(String LectureDay) {
        this.LectureDay = LectureDay;
    }

    public String getWD_WE() {
        return WD_WE;
    }

    public void setWD_WE(String WD_WE) {
        this.WD_WE = WD_WE;
    }

}
