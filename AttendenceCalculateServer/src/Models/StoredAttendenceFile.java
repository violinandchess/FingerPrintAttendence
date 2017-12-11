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
public class StoredAttendenceFile {

    private int ID;
    private String FileName;
    private String FilePath;
    private String WeekStartDate;
    private String WeekEndDate;
    private String NextProcessDate;
    private int IsProcessed;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIsProcessed() {
        return IsProcessed;
    }

    public String getNextProcessDate() {
        return NextProcessDate;
    }

    public void setNextProcessDate(String NextProcessDate) {
        this.NextProcessDate = NextProcessDate;
    }

    public void setIsProcessed(int IsProcessed) {
        this.IsProcessed = IsProcessed;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public String getWeekStartDate() {
        return WeekStartDate;
    }

    public void setWeekStartDate(String WeekStartDate) {
        this.WeekStartDate = WeekStartDate;
    }

    public String getWeekEndDate() {
        return WeekEndDate;
    }

    public void setWeekEndDate(String WeekEndDate) {
        this.WeekEndDate = WeekEndDate;
    }

}
