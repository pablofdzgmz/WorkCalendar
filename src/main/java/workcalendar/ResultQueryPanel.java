package workcalendar;
import javax.swing.*;
import java.sql.*;
import java.util.Arrays;

public class ResultQueryPanel {
    private DbConnection myConnection;
    public ResultQueryPanel(JPanel panel){
        myConnection = new DbConnection();
    }
    public void daySelectedSchedule(JPanel panel, String day) throws SQLException{
        try{
            panel.updateUI();
            Connection conn = myConnection.getMyConnection();
            String code = "SELECT worker.idworker,worker.name,section.name,worker.operatorlevel,schedule.day,schedule.entryhour,schedule.exithour,schedule.extrahour,schedule.profsickleave,schedule.festive,schedule.freeday,schedule.ceased FROM worker INNER JOIN schedule ON worker.idworker=schedule.idworker WHERE day='" + day + "' ORDER BY schedule.entryhour";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(code);
            // Data to be displayed in the JTable
            String[][] data = new String[50][12];
            int con = 0;
            while(result.next()) {
                data[con][0] = "" + result.getInt(1);
                data[con][1] =  result.getString(2);
                data[con][2] = "" + result.getString(3);
                data[con][3] = "" + result.getInt(4);
                data[con][4] = "" + result.getDate(5);
                data[con][5] = "" + result.getTime(6);
                data[con][6] = "" + result.getTime(7);
                data[con][7] = "" + result.getString(8);
                data[con][8] = "" + result.getString(9);
                data[con][9] = "" + result.getString(10);
                data[con][10] = "" + result.getString(11);
                data[con][11] = "" + result.getString(12);
                con++;
            }
            String[] header = {"ID Worker", "Name", "Section", "OP Level", "Day", "Entry Hour", "Exit Hour", "Extra Hour", "Sick Leave", "Festive", "Free day","Ceased"};
            JTable table = new JTable(data, header);
            panel.add(new JScrollPane(table));
        }
        catch (SQLException e) {
            System.err.println("Error" + e.getMessage());
        }
    }
    public void checkNextBadBoyExtraHours(JPanel panel, String dayOfExtraHours, String entryHour) throws SQLException{
        try{
            panel.updateUI();
            Connection conn = myConnection.getMyConnection();
            String code = queryNextBadBoyAbleForExtraHours(dayOfExtraHours, entryHour);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(code);
            // Data to be displayed in the JTable
            String[][] data = new String[50][6];
            int con = 0;
            while(result.next()) {
                data[con][0] = "" + result.getInt(1);
                data[con][1] = "" + result.getInt(2);
                data[con][2] = "" + result.getString(3);
                data[con][3] = "" + result.getInt(4);
                con++;
            }
            String[] header = {"ID Woker", "Section", "Name", "Extra Hours"};
            JTable table = new JTable(data, header);
            panel.add(new JScrollPane(table));
        }
        catch (SQLException e) {
            System.err.println("Error" + e.getMessage());
        }
    }
    public String queryNextBadBoyAbleForExtraHours(String dayOfExtraHours, String sheduleExtraWorkDay){
        if(sheduleExtraWorkDay.equalsIgnoreCase("06:00:00")){
            int day = Integer.parseInt(dayOfExtraHours.charAt(8) +"" +dayOfExtraHours.charAt(9));
            int month = Integer.parseInt(dayOfExtraHours.charAt(5) +"" +dayOfExtraHours.charAt(6));
            if(day == 1){
                month-=1;
                day = CalendarFunctions.monthDays(2003,month);
            }else{
                day-=1;
            }
            String dayBeforeOfExtraHours = "2023-" + String.format("%02d",month) + "-" + String.format("%02d",day);
            return "SELECT DISTINCT f.idworker, section.name, f.nombre, hours.extrahours FROM worker AS f " +
                    "INNER JOIN hours ON f.idworker=hours.idwoker " +
                    "INNER JOIN schedule ON f.idworker=schedule.idworker " +
                    "INNER JOIN section ON f.idworker=section.idworker " +
                    "WHERE NOT EXISTS (SELECT * FROM schedule AS d WHERE f.idworker=d.idworker AND d.day='" + dayOfExtraHours + "') " +
                    "OR EXISTS (SELECT * FROM schedule AS d WHERE f.idworker=d.idworker AND d.day='" + dayBeforeOfExtraHours + "' AND d.exithour!='22:00:00' " +
                    "           AND NOT EXISTS (SELECT * FROM schedule AS h WHERE d.idworker=h.idworker AND h.day='" + dayOfExtraHours + "')) " +
                    "ORDER BY hours.extrahours ASC;";
        }else{
            return "SELECT DISTINCT f.idworker, section.name, f.name, hours.extrahours FROM worker AS f " +
                    "INNER JOIN hours ON f.idworker=horas.idworker " +
                    "INNER JOIN schedule ON f.idworker=schedule.idworker " +
                    "INNER JOIN section ON f.idworker=section.idworker " +
                    "WHERE NOT EXISTS (SELECT * FROM schedule AS d WHERE f.idworker=d.idworker AND d.day='" + dayOfExtraHours + "') ORDER BY hours.extrahours ASC";
        }
    }
}