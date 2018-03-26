/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainService;

import Models.AttendLecture;
import Models.LectureSession;
import Models.StoredAttendenceFile;
import Models.WeeklyAttendence;
import ServiceLayer.AttendLectureService;
import ServiceLayer.SessionService;
import ServiceLayer.WeeklyAttendenceService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vibavi
 */
public class CalculateAttendence {

    private static Date StartDate;
    private static Date EndDate;
    private static final Logger LOGGER = Logger.getLogger(WeeklyAttendenceService.class.getName());

    public static void main(String[] args) throws ParseException {

        WeeklyAttendenceService weeklyattendence = new WeeklyAttendenceService();
        StoredAttendenceFile[] storedFiles = weeklyattendence.GetWeeklyFiles(false);//get unprocessed files
        if (storedFiles != null) {
            for (StoredAttendenceFile file : storedFiles) {
                GenarateWeeklyAttendenceFromRAWVersion2(file);
            }
        } else {
            LOGGER.log(Level.INFO, "No_Files_To_Process", "No weekly files uploaded to process");
        }
        SimpleDateFormat dateonly = new SimpleDateFormat("yyyy-MM-dd");
        StartDate = dateonly.parse(storedFiles[0].getWeekStartDate());
        EndDate = dateonly.parse("2018-01-08");
        EndDate = dateonly.parse("2018-02-25");
      //  StartDate = dateonly.parse(storedFiles[0].getWeekStartDate());
       // EndDate = dateonly.parse(storedFiles[0].getWeekEndDate());

        CalculateAbsentPresent(dateonly.format(EndDate.getTime()) + 1);
    }

    private static void GenarateWeeklyAttendenceFromRAWVersion2(StoredAttendenceFile file) {
        System.out.println("File name " + file.getFileName());
        System.out.println("File Path " + file.getFilePath());

        List<String> readbuffer = new ArrayList<>();
        String readfilefullpath = file.getFilePath() + file.getFileName();

        File readFile = new File(readfilefullpath);

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(readFile));

            String line;
            int lineno = 1;

            while ((line = reader.readLine()) != null) {
                if (lineno == 1) {
                    lineno++;
                    continue;
                }
                if (!line.trim().isEmpty()) {

                    readbuffer.add(line);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        WeeklyAttendenceService weeklyattendence = new WeeklyAttendenceService();
        StoredAttendenceFile[] lastprocessedfile = weeklyattendence.GetWeeklyFiles(true);
        WeeklyAttendence[] filterdData = new WeeklyAttendence[readbuffer.size()];
        int i = 0;
        Date updatednextprocess = new Date();
        for (String record : readbuffer) {
            String recordArray[] = record.split("\t");
            //System.out.println("recordArray[0]" + recordArray[0]);

            WeeklyAttendence tempFillData = new WeeklyAttendence();

            String datetimestring = recordArray[8];
            String[] datetimeArray = datetimestring.split(" ");
            tempFillData.setDeviceID(recordArray[2]);
            String datepartArray[] = datetimeArray[0].split("/");
            int tempint;
            String converteddatepart = "";
            for (int k = 0; k < datepartArray.length; k++) {

                try {
                    System.out.println("" + datepartArray[k].length());
                    for (int l = 0; l < datepartArray[k].length(); l++) {
                        try {
                            String tempstring = datepartArray[k].charAt(l) + "";
                            tempint = Integer.parseInt(tempstring);
                            converteddatepart += tempint;
                        } catch (NumberFormatException ex) {

                            System.out.println("unreadable Charater");
                        }

                    }
                    if (k != 2) {
                        converteddatepart += "/";
                    }
                } catch (Exception ex) {

                    System.out.println("outot");
                }

            }
            String timearray[] = datetimeArray[1].split(":");
            String convertedtime = "";
            for (int k = 0; k < timearray.length; k++) {

                System.out.println("" + timearray[k].length());
                for (int l = 0; l < timearray[k].length(); l++) {
                    try {
                        String tempstring = timearray[k].charAt(l) + "";
                        tempint = Integer.parseInt(tempstring);
                        convertedtime += tempint;
                    } catch (NumberFormatException ex) {

                        System.out.println("unreadable Charater");
                    }

                }
                if (k != 2) {
                    convertedtime += ":";
                }

            }

            tempFillData.setWeekDate(converteddatepart);
            tempFillData.setWeekTime(convertedtime);
            if (lastprocessedfile == null || lastprocessedfile[0].getNextProcessDate() == null) {
                filterdData[i] = tempFillData;
                i++;

            } else {
                try {

                    String output = lastprocessedfile[0].getNextProcessDate();
                    DateFormat toFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    DateFormat toFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    toFormat.setLenient(false);

                    Date dateRead = toFormat.parse(converteddatepart + " " + convertedtime);
                    Date dateNext = toFormat2.parse(output);

                    if (dateRead.compareTo(dateNext) >= 0) {
                        filterdData[i] = tempFillData;
                        i++;

                    }
                } catch (Exception ex) {
                    System.out.println("" + ex);
                }

            }

        }

        try {
            SimpleDateFormat dateonly = new SimpleDateFormat("yyyy-MM-dd");
            updatednextprocess = dateonly.parse(file.getWeekEndDate());
            Calendar c = Calendar.getInstance();
            c.setTime(updatednextprocess);
            c.add(Calendar.DATE, 1);
            AddWeeklyAttendence(filterdData);
            // StartDate = dateonly.parse(file.getWeekStartDate());
            // EndDate = dateonly.parse(file.getWeekEndDate());

        } catch (ParseException ex) {
            System.out.println("Exception 3 " + ex);
        }

    }

    private static void AddWeeklyAttendence(WeeklyAttendence[] filteredDate) {
        int i = 0;
        WeeklyAttendenceService weeklyattendence = new WeeklyAttendenceService();
        boolean isupdated = false;
        for (WeeklyAttendence entry : filteredDate) {
            if (entry != null) {
                String weekdstring = entry.getWeekDate();
                Date weekd = new Date();
                SimpleDateFormat dateonly2 = new SimpleDateFormat("yyy/MM/dd");
                SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
                Date time = new Date();
                try {
                    time = localDateFormat.parse(entry.getWeekTime());
                    weekd = dateonly2.parse(weekdstring);
                } catch (ParseException ex) {
                    System.out.println("ex" + ex.getMessage());
                }
                dateonly2 = new SimpleDateFormat("yyyy-MM-dd");
                String aftertime = localDateFormat.format(time);
                String afterdate = dateonly2.format(weekd);
                WeeklyAttendence weeklyrecord = new WeeklyAttendence();
                String filteredid = "";
                for (int k = 0; k < entry.getDeviceID().length(); k++) {
                    try {
                        String tempstring = "" + entry.getDeviceID().charAt(k);
                        int tempno = Integer.parseInt(tempstring);
                        filteredid += tempstring;
                    } catch (NumberFormatException ex) {
                        System.out.println("number");
                    }
                }
                weeklyrecord.setDeviceID(Integer.parseInt(filteredid) + "");
                weeklyrecord.setWeekDate(afterdate);
                weeklyrecord.setWeekTime(aftertime);
                isupdated = weeklyattendence.AddWeekendAttendence(weeklyrecord);
            }

            // isupdated = weeklyattendence.AddWeekendAttendence(entry.getDeviceID(), s, entry.getWeekTime());
            if (!isupdated) {
                break;
            }
        }
        if (isupdated) {
            System.out.println("updated");
        } else {
            System.out.println("not updated");
        }

    }

    private static void CalculateAbsentPresent(String nextprocessdate) {

        Date startTempWeek = StartDate;
        Date endTempWeek = EndDate;

        Calendar c = Calendar.getInstance();
        c.setTime(startTempWeek);
        Date tempDate = c.getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        WeeklyAttendenceService service = new WeeklyAttendenceService();
        SessionService session = new SessionService();
        AttendLectureService attendservice = new AttendLectureService();
        do {
            int day = c.get(Calendar.DAY_OF_WEEK);
            int passday = getDay(day);
            WeeklyAttendence[] attendence = service.GetTodayAttendence(dateformat.format(tempDate));
            LectureSession[] todaysession = session.GetTodaySession(dateformat.format(tempDate));
            if (attendence != null && todaysession != null) {
                for (LectureSession today : todaysession) {
                    for (WeeklyAttendence attend : attendence) {
                        if (today.getCode().equals(attend.getSubCode())) {
                            String studenttime = attend.getWeekTime();
                            String lecturestarttime = today.getStartTime();
                            String lectureendtime = today.getEndTime();
                            if (studenttime.compareTo(lecturestarttime) >= 0 && studenttime.compareTo(lectureendtime) <= 0) {
                                AttendLecture templecture = new AttendLecture();
                                templecture.setRegNo(attend.getRegNo());
                                templecture.setDeviceID(attend.getDeviceID());
                                templecture.setSessionID(today.getSessionID());
                                templecture.setIDate(dateformat.format(tempDate));
                                templecture.setITime(today.getStartTime());
                                templecture.setAB_P("Present");

                                attendservice.AddWeekendAttendence(templecture);
                            } else {
                                AttendLecture templecture = new AttendLecture();
                                templecture.setRegNo(attend.getRegNo());
                                templecture.setDeviceID(attend.getDeviceID());
                                templecture.setSessionID(today.getSessionID());
                                templecture.setIDate(dateformat.format(tempDate));
                                templecture.setITime(today.getStartTime());
                                templecture.setAB_P("Absent");

                                attendservice.AddWeekendAttendence(templecture);
                            }

                        }
                    }
                    WeeklyAttendence[] totalregister = service.GetAbsenties(today.getCode());
                    for (WeeklyAttendence absent : totalregister) {
                        AttendLecture templecture = new AttendLecture();
                        templecture.setRegNo(absent.getRegNo());
                        templecture.setDeviceID(absent.getDeviceID());
                        templecture.setSessionID(today.getSessionID());
                        templecture.setIDate(dateformat.format(tempDate));
                        templecture.setITime(today.getStartTime());
                        templecture.setAB_P("Absent");

                        attendservice.AddWeekendAttendence(templecture);

                    }

                }
            }

            c.add(Calendar.DATE, 1);
            tempDate = c.getTime();

        } while ((tempDate.compareTo(startTempWeek) >= 0) && (tempDate.compareTo(endTempWeek) <= 0));
        WeeklyAttendenceService weeklyattendence = new WeeklyAttendenceService();
        //boolean isupdated = weeklyattendence.UpdateNextProcessDate(id, nextprocessdate);
    }

    private static int getDay(int day) {
        switch (day) {
            case Calendar.SUNDAY:
                return 7;
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            default:
                return 0;
        }
    }

}
