package workcalendar;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
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
    public void addDayToDataBaseQuery(int idworker, String section, String day, String entryHour, String exitHour, String extraHour, String freeday, String profsickleave, String festive, String ceased) throws SQLException {
        Connection conn = myConnection.getMyConnection();
        int recordCount = 0;
        String queryIsDayBusy = "SELECT COUNT(*) FROM schedule WHERE idworker=? AND day=?";
        PreparedStatement queryRecordCount = conn.prepareStatement(queryIsDayBusy);
        queryRecordCount.setInt(1, idworker);
        queryRecordCount.setString(2, day);
        ResultSet resultSetRecordCount = queryRecordCount.executeQuery();
        while (resultSetRecordCount.next())
            recordCount = resultSetRecordCount.getInt(1);
        if (recordCount == 0) {
            if((!freeday.equalsIgnoreCase("") && hasFreeDays(idworker,freeday)) || freeday.equalsIgnoreCase("") ){
                String insertBadBoyJobDay = "INSERT INTO schedule (idworker, section, day, entryhour, exithour, extrahour, freeday, profsickleave, festive, ceased) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement queryBadBoyByID = conn.prepareStatement(insertBadBoyJobDay);
                queryBadBoyByID.setInt(1, idworker);
                queryBadBoyByID.setString(2, section);
                queryBadBoyByID.setString(3, day);
                queryBadBoyByID.setString(4, entryHour);
                queryBadBoyByID.setString(5, exitHour);
                queryBadBoyByID.setString(6, extraHour);
                queryBadBoyByID.setString(7, freeday);
                queryBadBoyByID.setString(8, profsickleave);
                queryBadBoyByID.setString(9, festive);
                queryBadBoyByID.setString(10, ceased);
                queryBadBoyByID.executeUpdate();
                if(!extraHour.equalsIgnoreCase(StringUtils.EMPTY) || !freeday.equalsIgnoreCase(StringUtils.EMPTY))
                    addExtraHoursToTable(idworker, extraHour, freeday);
            }
        } else {
            throw new SQLException();
        }
    }
    public void updateDayToDataBaseQuery(int idworker, String section, String day, String entryHour, String exitHour, String extraHour, String freeday, String profsickleave, String festive, String ceased) throws SQLException {
        Connection conn = myConnection.getMyConnection();
        int recordCount = 0;
        String queryIsDayBusy = "SELECT COUNT(*) FROM schedule WHERE idworker=? and day=?";
        PreparedStatement queryRecordCount = conn.prepareStatement(queryIsDayBusy);
        queryRecordCount.setInt(1, idworker);
        queryRecordCount.setString(2, day);
        ResultSet resultSetRecordCount = queryRecordCount.executeQuery();
        while (resultSetRecordCount.next())
            recordCount = resultSetRecordCount.getInt(1);
        if (recordCount == 1) {
            if((!freeday.equalsIgnoreCase("") && hasFreeDays(idworker,freeday)) || freeday.equalsIgnoreCase("") ){
                String insertBadBoyJobDay = "UPDATE schedule SET idworker=?, section=?, day=?, entryhour=?, exithour=?, extrahour=?, profsickleave=?, festive=?, freeday=?, ceased=? WHERE idworker=? and day=?";
                PreparedStatement queryBadBoyByID = conn.prepareStatement(insertBadBoyJobDay);
                queryBadBoyByID.setInt(1, idworker);
                queryBadBoyByID.setString(2, section);
                queryBadBoyByID.setString(3, day);
                queryBadBoyByID.setString(4, entryHour);
                queryBadBoyByID.setString(5, exitHour);
                queryBadBoyByID.setString(6, extraHour);
                queryBadBoyByID.setString(7, profsickleave);
                queryBadBoyByID.setString(8, festive);
                queryBadBoyByID.setString(9, freeday);
                queryBadBoyByID.setString(10, ceased);
                queryBadBoyByID.setInt(11, idworker);
                queryBadBoyByID.setString(12, day);
                queryBadBoyByID.executeUpdate();
                if((!extraHour.equalsIgnoreCase(StringUtils.EMPTY) && !extraHour.equalsIgnoreCase("No")) || !freeday.equalsIgnoreCase(StringUtils.EMPTY))
                    addExtraHoursToTable(idworker,extraHour,freeday);
            }
        } else {
            throw new SQLException();
        }
    }
    private void addExtraHoursToTable(int idworker, String extraHour, String freeday) throws SQLException {
        Connection conn = myConnection.getMyConnection();
        String insertHours = StringUtils.EMPTY;
        int hours = 8;
        if(extraHour.equalsIgnoreCase("Yes")){
            insertHours = "UPDATE hours SET extrahours = extrahours + " + hours + " WHERE hours.idworker = " + idworker + "";
        }else if(freeday.equalsIgnoreCase("Holidays")) {
            insertHours = "UPDATE hours SET holidays = a-1 WHERE hours.idworker = " + idworker + "";
        }else if(freeday.equalsIgnoreCase("Agreement")) {
            insertHours = "UPDATE hours SET agreement = agreement-1 WHERE hours.idworker = " + idworker + "";
        }else if(freeday.equalsIgnoreCase("Own Business")) {
            insertHours = "UPDATE hours SET ownbusiness = ownbusiness-1 WHERE horas.idworker = " + idworker + "";
        }else if(freeday.equalsIgnoreCase("Medic")) {
            insertHours = "UPDATE hours SET medic = medic-1 WHERE horas.idworker = " + idworker + "";
        }
        Statement statement = conn.createStatement();
        statement.executeUpdate(insertHours);
    }
    private boolean hasFreeDays(int idworker, String freeDayType) throws SQLException{
        Connection conn = myConnection.getMyConnection();
        String type = StringUtils.EMPTY;
        if (freeDayType.equalsIgnoreCase("Holidays")) type = "Holidays";
        else if (freeDayType.equalsIgnoreCase("Agreement")) type = "Agreement";
        else if (freeDayType.equalsIgnoreCase("Own Business")) type = "Own Business";
        else if (freeDayType.equalsIgnoreCase("Medic")) type = "Medic";
        int recordCount = 0;
        String queryHasFreeDay = "SELECT " + type + " FROM horas where idworker = ?";
        PreparedStatement queryRecordCount = conn.prepareStatement(queryHasFreeDay);
        queryRecordCount.setInt(1, idworker);
        ResultSet resultSetRecordCount = queryRecordCount.executeQuery();
        while (resultSetRecordCount.next())
            recordCount = resultSetRecordCount.getInt(1);
        if (recordCount == 0 ){
            JOptionPane.showMessageDialog(null,"You haven't enough " + freeDayType + " days availables !","¡ Error !",JOptionPane.ERROR_MESSAGE);
            return false;
        } else return true;
    }
    public void deleteDayFromDataBase(int idworker, String workDay) throws SQLException {
        Connection conn = myConnection.getMyConnection();
        int recordCount = 0;
        String queryIsDayBusy = "SELECT COUNT(*) FROM schedule WHERE idworker=? and day=?";
        PreparedStatement queryRecordCount = conn.prepareStatement(queryIsDayBusy);
        queryRecordCount.setInt(1, idworker);
        queryRecordCount.setString(2, workDay);
        ResultSet resultSetRecordCount = queryRecordCount.executeQuery();
        while (resultSetRecordCount.next())
            recordCount = resultSetRecordCount.getInt(1);
        if (recordCount == 1) {
            String deleteWorkerJobDay = "DELETE FROM schedule WHERE idworker=? and day=?";
            PreparedStatement queryWorkerByID = conn.prepareStatement(deleteWorkerJobDay);
            queryWorkerByID.setInt(1, idworker);
            queryWorkerByID.setString(2, workDay);
            queryWorkerByID.executeUpdate();
        } else {
            throw new SQLException();
        }
    }
    public void setDayTypeFromDataBase(int idworker, String day) throws SQLException{
        Connection conn = myConnection.getMyConnection();
        int recordCount = 0;
        String currentEntryHour = StringUtils.EMPTY; String currentExitHour = StringUtils.EMPTY; String currentFreeDay = StringUtils.EMPTY;
        String currentExtraHour = StringUtils.EMPTY; String currentCeased = StringUtils.EMPTY; String currentFestive = StringUtils.EMPTY;
        String currentProfSickLeave = StringUtils.EMPTY;
        String queryIsDayBusy = "SELECT COUNT(*),entryhour,exithour,extrahour,profsickleave,festive,freeday,ceased FROM schedule WHERE idworker=? and day=?";
        PreparedStatement queryRecordCount = conn.prepareStatement(queryIsDayBusy);
        queryRecordCount.setInt(1, idworker);
        queryRecordCount.setString(2, day);
        ResultSet resultSetRecordCount = queryRecordCount.executeQuery();
        while (resultSetRecordCount.next()) {
            recordCount = resultSetRecordCount.getInt(1);
            currentEntryHour = resultSetRecordCount.getString(2);
            currentExitHour = resultSetRecordCount.getString(3);
            currentExtraHour = resultSetRecordCount.getString(4);
            currentProfSickLeave = resultSetRecordCount.getString(5);
            currentFestive = resultSetRecordCount.getString(6);
            currentFreeDay = resultSetRecordCount.getString(7);
            currentCeased = resultSetRecordCount.getString(8);
        }
        if (recordCount != 0) {
            checkSchedule(currentEntryHour, currentExitHour);
            checkTypeOfDay(currentFreeDay);
            checkTypeOfExtraDay(currentExtraHour,currentCeased,currentFestive,currentProfSickLeave);
        }
        else{
            checkSchedule("", "");
            checkTypeOfDay("");
            checkTypeOfExtraDay("","","","");
        }
    }
    public void checkSchedule(String entryHour, String exitHour){
        if (entryHour.equalsIgnoreCase(Schedule.MORROW_SCHEDULE[0])) QueryFunctionPanels.jRadioButtonMorning.setSelected(true);
        else if (entryHour.equalsIgnoreCase(Schedule.AFTERNOON_SCHEDULE[0])) QueryFunctionPanels.jRadioButtonAfternoon.setSelected(true);
        else if (entryHour.equalsIgnoreCase(Schedule.NIGHT_SCHEDULE[0])) QueryFunctionPanels.jRadioButtonNight.setSelected(true);
        else if (entryHour.equalsIgnoreCase(Schedule.WHISTLES_SCHEDULE[0])) QueryFunctionPanels.jRadioButtonWhistle.setSelected(true);
        else if (entryHour.equalsIgnoreCase(StringUtils.EMPTY)) QueryFunctionPanels.scheduleButtonGroup.clearSelection();
    }
    public void checkTypeOfDay(String currentTypeOfDay){
        if (currentTypeOfDay.equalsIgnoreCase("Holidays")) QueryFunctionPanels.jRadioButtonHolydays.setSelected(true);
        else if (currentTypeOfDay.equalsIgnoreCase("Agreement")) QueryFunctionPanels.jRadioButtonAgreement.setSelected(true);
        else if (currentTypeOfDay.equalsIgnoreCase("Own Business") ) QueryFunctionPanels.jRadioButtonOwnBusiness.setSelected(true);
        else if (currentTypeOfDay.equalsIgnoreCase("Medic")) QueryFunctionPanels.jRadioButtonMedic.setSelected(true);
        else if (currentTypeOfDay.equalsIgnoreCase("")) QueryFunctionPanels.typeOfFreeDay.clearSelection();
    }
    public void checkTypeOfExtraDay(String extraDay, String profSickLeave, String festive, String ceased){
        if (extraDay.equalsIgnoreCase("Yes")) QueryFunctionPanels.jRadioButtonExtra.setSelected(true);
        else if (profSickLeave.equalsIgnoreCase("Yes")) QueryFunctionPanels.jRadioButtonProfSickLeave.setSelected(true);
        else if (festive.equalsIgnoreCase("Yes")) QueryFunctionPanels.jRadioButtonFestive.setSelected(true);
        else if (ceased.equalsIgnoreCase("Yes")) QueryFunctionPanels.jRadioButtonCeased.setSelected(true);
    }
    public Color fillWorkerCalendarColours(int currentIdWorker, String currentDay) throws SQLException {
        Connection conn = myConnection.getMyConnection();
        int recordCount = 0;
        Color dayColour = null;
        String currentEntryHour = StringUtils.EMPTY;
        String queryIsDayBusy = "SELECT COUNT(*),entryhour FROM schedule WHERE idworker=? and day=?";
        PreparedStatement queryRecordCount = conn.prepareStatement(queryIsDayBusy);
        queryRecordCount.setInt(1, currentIdWorker);
        queryRecordCount.setString(2, currentDay);
        ResultSet resultSetRecordCount = queryRecordCount.executeQuery();
        while (resultSetRecordCount.next()) {
            recordCount = resultSetRecordCount.getInt(1);
            currentEntryHour = resultSetRecordCount.getString(2);
        }
        if (recordCount != 0) {
            if (currentEntryHour.equalsIgnoreCase(Schedule.MORROW_SCHEDULE[0])) dayColour = Schedule.MORNING_DAY_COLOR;
            else if (currentEntryHour.equalsIgnoreCase(Schedule.AFTERNOON_SCHEDULE[0])) dayColour = Schedule.AFTERNOON_DAY_COLOR;
            else if (currentEntryHour.equalsIgnoreCase(Schedule.NIGHT_SCHEDULE[0])) dayColour = Schedule.NIGHT_DAY_COLOR;
            else if (currentEntryHour.equalsIgnoreCase(Schedule.WHISTLES_SCHEDULE[0])) dayColour = Schedule.WHISTLES_DAY_COLOR;
        }
        return dayColour;
    }
}
