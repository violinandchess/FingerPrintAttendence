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
public class WeeklyAttendence {
    
    private String DeviceID;
    private String WeekDate;
    private String WeekTime;
    private String SubCode;
    private String RegNo;

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String RegNo) {
        this.RegNo = RegNo;
    }
    public String getSubCode() {
        return SubCode;
    }

    public void setSubCode(String SubCode) {
        this.SubCode = SubCode;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    public String getWeekDate() {
        return WeekDate;
    }

    public void setWeekDate(String WeekDate) {
        this.WeekDate = WeekDate;
    }

    public String getWeekTime() {
        return WeekTime;
    }

    public void setWeekTime(String WeekTime) {
        this.WeekTime = WeekTime;
    }
    
    
    
}
