package workcalendar;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operations {
    private DbConnection myConnection;

    public Operations() {
        myConnection = new DbConnection();
    }
    public Worker getWorkerById(int id) throws SQLException {
        Worker worker = null;
        String stringQueryWorkerByID = "SELECT worker.idworker, worker.name, section.name, worker.operatorlevel FROM worker " +
                                        "INNER JOIN section ON section.idsection=worker.section WHERE idworker=?";
        PreparedStatement queryWorkerByID;
        Connection conn = myConnection.getMyConnection();
        queryWorkerByID = conn.prepareStatement(stringQueryWorkerByID);
        queryWorkerByID.setInt(1, id);
        ResultSet resultSet = queryWorkerByID.executeQuery();
        while (resultSet.next()) {
            worker = new Worker(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4));
        }
        return worker;
    }
    public ArrayList<Integer> getWorkerId() throws SQLException {
        List<Integer> idlist = new ArrayList<>();
        Connection conn = myConnection.getMyConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT idworker FROM worker ORDER BY idworker ASC");
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            idlist.add(id);
        }
        return (ArrayList<Integer>) idlist;
    }
    /*public void addDayToDataBaseQuery(int idworker, String section, String day, String entryHour, String exitHour, String extraHour, String freeday, String profsickleave, String commsickleave) throws SQLException {
        Connection conn = myConnection.getMyConnection();
        int recordCount = 0;
        String queryIsDayBusy = "SELECT COUNT(*) FROM schedule WHERE idworker=? AND dia=?";
        PreparedStatement queryRecordCount = conn.prepareStatement(queryIsDayBusy);
        queryRecordCount.setInt(1, idworker);
        queryRecordCount.setString(2, day);
        ResultSet resultSetRecordCount = queryRecordCount.executeQuery();
        while (resultSetRecordCount.next())
            recordCount = resultSetRecordCount.getInt(1);
        if (recordCount == 0) {
            if((!typeOfFreeDay.equalsIgnoreCase("") && hasFreeDays(idbadboy,typeOfFreeDay)) || typeOfFreeDay.equalsIgnoreCase("") ){
                String insertBadBoyJobDay = "INSERT INTO schedule (idworker, section, day, entryhour, exithour, extrahour, freeday, profsickleave, commsickleave) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement queryBadBoyByID = conn.prepareStatement(insertBadBoyJobDay);
                queryBadBoyByID.setInt(1, idworker);
                queryBadBoyByID.setString(2, section);
                queryBadBoyByID.setString(3, day);
                queryBadBoyByID.setString(4, entryHour);
                queryBadBoyByID.setString(5, exitHour);
                queryBadBoyByID.setString(6, extraHour);
                queryBadBoyByID.setString(7, slacker);
                queryBadBoyByID.setString(8, reason);
                queryBadBoyByID.setString(9, typeOfFreeDay);
                queryBadBoyByID.executeUpdate();
                if(!extraHour.equalsIgnoreCase(StringUtils.EMPTY) || !typeOfFreeDay.equalsIgnoreCase(StringUtils.EMPTY))
                    addExtraHoursToTable(idbadboy, entryHour, exitHour, extraHour, slacker, reason, typeOfFreeDay);
            }
        } else {
            throw new SQLException();
        }
    }*/
}
