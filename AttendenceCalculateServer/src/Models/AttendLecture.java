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
public class AttendLecture {
   
    private String RegNo;
    private String DeviceID;
    private String SessionID;
    private String IDate;
    private String ITime;
    private String AB_P;

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String RegNo) {
        this.RegNo = RegNo;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String SessionID) {
        this.SessionID = SessionID;
    }

    public String getIDate() {
        return IDate;
    }

    public void setIDate(String IDate) {
        this.IDate = IDate;
    }

    public String getITime() {
        return ITime;
    }

    public void setITime(String ITime) {
        this.ITime = ITime;
    }

    public String getAB_P() {
        return AB_P;
    }

    public void setAB_P(String AB_P) {
        this.AB_P = AB_P;
    }
    
}
