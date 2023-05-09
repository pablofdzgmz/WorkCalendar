package workcalendar;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Schedule {
    public static final String[] MORROW_SCHEDULE = {"06:00:00", "14:00:00"};
    public static final String[] AFTERNOON_SCHEDULE = {"14:00:00", "22:00:00"};
    public static final String[] NIGHT_SCHEDULE = {"22:00:00", "06:00:00"};
    public static final String[] WHISTLES_SCHEDULE = {"08:00:00", "17:00:00"};
    public static String[] daySchedule = new String[2];
    public static final Color AFTERNOON_DAY_COLOR = Color.YELLOW;
    public static final Color MORNING_DAY_COLOR = new Color (65, 234, 15);
    public static final Color NIGHT_DAY_COLOR = Color.RED;
    public static final Color WHISTLES_DAY_COLOR = new Color (255,128,0);
    public static String entryHour;
    public static String exitHour;
    public static String freeDay;

    public Schedule(){

    }
    public static void fillFourthTurnCalendar2023() {
        int countDay = 1;
        int restDay = 7;
        int week = 1;
        do {
            if (week == 1) { firstWeek(restDay, countDay); week = 2;
            } else if (week == 2) { secondWeek(restDay, countDay); week = 3;
            } else if (week == 3) { thirdWeek(restDay, countDay); week = 4;
            } else if (week == 4) { fourthWeek(restDay, countDay); week = 1;}
            countDay += 7;
            if (countDay > 365) restDay = countDay - 365;
        } while (countDay < 366);
    }
    public static void fillWhistleTurnCalendar2023(){
        int countDay = 1;
        int restDay = 7;
        do {
            whistleWeek(restDay, countDay);
            countDay += 7;
            if (countDay > 365) restDay = countDay - 365;
        } while (countDay < 366);
    }
    public static void firstWeek(int nDays, int firstDayOfWeek) {
        String[] week = {"M", "M", "T", "T", "N", "N", "N"};
        for (int i = 0; i < nDays; i++) {
            if(!isFreeDay(week[i])){
                daySchedule = checkSchedule(week[i],i);
                addDayToDataBase(getDateString(firstDayOfWeek+i), daySchedule[0], daySchedule[1]);
            }
        }
    }
    public static void secondWeek(int nDays, int firstDayOfWeek) {
        String[] week = {"L", "L", "M", "M", "T", "T", "T"};
        for (int i = 0; i < nDays; i++) {
            if(!isFreeDay(week[i])){
                daySchedule = checkSchedule(week[i],i);
                addDayToDataBase(getDateString(firstDayOfWeek+i), daySchedule[0], daySchedule[1]);
            }
        }
    }
    public static void thirdWeek(int nDays, int firstDayOfWeek) {
        String[] week = {"N", "N", "L", "L", "M", "M", "M"};
        for (int i = 0; i < nDays; i++) {
            if(!isFreeDay(week[i])){
                daySchedule = checkSchedule(week[i],i);
                addDayToDataBase(getDateString(firstDayOfWeek+i), daySchedule[0], daySchedule[1]);
            }
        }
    }
    public static void fourthWeek(int nDays, int firstDayOfWeek) {
        String[] week = {"T", "T", "N", "N", "L", "L", "L"};
        for (int i = 0; i < nDays; i++) {
            if(!isFreeDay(week[i])){
                daySchedule = checkSchedule(week[i],i);
                addDayToDataBase(getDateString(firstDayOfWeek+i), daySchedule[0], daySchedule[1]);
            }
        }
    }
    public static void whistleWeek(int nDays, int firstDayOfWeek){
        String[] week = {"T/N", "T/N", "T/N", "T/N", "T/N", "L", "L"};
        for (int i = 0; i < nDays; i++) {
            if(!isFreeDay(week[i])){
                daySchedule = checkSchedule(week[i],i);
                addDayToDataBase(getDateString(firstDayOfWeek+i), daySchedule[0], daySchedule[1]);
            }
        }
    }
    public static String[] getMorrowSchedule() { return MORROW_SCHEDULE; }
    public static String[] getAfternoonSchedule() { return AFTERNOON_SCHEDULE; }
    public static String[] getNightSchedule() { return NIGHT_SCHEDULE; }
    public static String[] getWhistlesSchedule() { return WHISTLES_SCHEDULE; }

    public static String[] checkSchedule(String dayChar, int dayNum) {
        if (dayChar.equals("M"))  return getMorrowSchedule();
        else if (dayChar.equals("T")) return getAfternoonSchedule();
        else if (dayChar.equals("N")) return getNightSchedule();
        else return getWhistlesSchedule();
    }
    public static boolean isFreeDay(String dayChar){
        return dayChar.equals("L");
    }
    public static String getDateString(int dayOfYear){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "" + dateFormat.format(date);
    }
    public static void addDayToDataBase(String day, String entryHour, String exitHour){
        /*Operations query = new Operations();
        try {
            query.addDayToDataBaseQuery(Integer.parseInt(QueryFunctionPanels.jComboBoxIdBadBoys.getSelectedItem().toString()), Integer.parseInt(QueryFunctionPanels.jTextFieldBadBoyGroup.getText()),day,entryHour ,exitHour,"","","","");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"¡ Error al construir calendario !","¡ Error !",JOptionPane.ERROR_MESSAGE);
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }*/
    }
}
