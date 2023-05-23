package workcalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static workcalendar.CalendarFunctions.monthDays;
import static workcalendar.CalendarFunctions.zeller;

public class MonthPanels extends JPanel {
    Operations operations = new Operations();
    SharedVariables shared = new SharedVariables();
    public JPanel panel12Months[] = new JPanel[12];
    public JLabel label7Days[] = new JLabel[7];
    public static JButton daysButton[][] = new JButton[12][49];
    public JLabel labelFillMonth[] = new JLabel[20];
    public MonthPanels(int year){
        //setBounds(5,5, shared.screenWidth-25, (shared.screenHeight-100)/2 );
        setLayout(new GridLayout(3,4));
        for(int i=0;i<12;i++){
            panel12Months[i] = new JPanel();
            panel12Months[i].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), shared.monthsName[i]));
            panel12Months[i].setLayout(new GridLayout(7,7));
            //Fill day of week names
            for(int j=0;j<7;j++){
                label7Days[j] = new JLabel();
                label7Days[j].setText(shared.daysName[j]);
                panel12Months[i].add(label7Days[j]);
            }
            int dias= monthDays(year,i+1);
            int z = zeller(year, i+1);
            int count=0;
            //Fill empty first days of month with JLabel
            for(int k=0;k<z;k++) {
                labelFillMonth[k] = new JLabel(" ");
                panel12Months[i].add(labelFillMonth[k]);
                count++;
            }
            //Fill days of month with JButton
            for(int m=1; m<=dias; m++){
                daysButton[i][m+z+1] = new JButton();
                daysButton[i][m+z+1].setText(""+m);
                panel12Months[i].add(daysButton[i][m+z+1]);
                int finalI = i;
                int finalM = m;
                int finalZ = z;
                daysButton[i][m+z+1].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String day = "2023-" + (String.format("%02d", finalI+1)) + "-" + (String.format("%02d" , finalM)) + "";
                        QueryFunctionPanels.jTextFieldCheckDayForExtraHours.setText(day);
                        if(QueryFunctionPanels.jToggleButtonAddDay.isSelected()){
                            QueryFunctionPanels.setTypeOfFreeDay(); QueryFunctionPanels.setTypeOfDay();
                            addDayToDataBase(finalI,finalM,finalZ);
                            setDayScheduleToTable(QueryFunctionPanels.queryPanel, day);
                        }else if (QueryFunctionPanels.jToggleButtonEditDay.isSelected()){
                            QueryFunctionPanels.setTypeOfFreeDay(); QueryFunctionPanels.setTypeOfDay();
                            editDayFromDataBase(finalI,finalM,finalZ);
                            setDayScheduleToTable(QueryFunctionPanels.queryPanel, day);
                        }else if (QueryFunctionPanels.jToggleButtonDeleteDay.isSelected()){
                            deleteDayFromDataBase(Worker.idWorker, day);
                            setDayScheduleToTable(QueryFunctionPanels.queryPanel, day);
                        }else if(!QueryFunctionPanels.jToggleButtonAddDay.isSelected() && !QueryFunctionPanels.jToggleButtonEditDay.isSelected() && !QueryFunctionPanels.jToggleButtonDeleteDay.isSelected()){
                            //QueryFunctionPanels.setTypeOfFreeDay(); QueryFunctionPanels.setTypeOfDay();
                            setDayTypesFromDataBase(Worker.idWorker, day);
                            setDayScheduleToTable(QueryFunctionPanels.queryPanel, day);
                        }
                    }
                });
                count++;
            }
            //Fill empty GridLayout spaces with JLabel
            for(int n=0; n<(42-count); n++){
                labelFillMonth[n] = new JLabel(" ");
                panel12Months[i].add(labelFillMonth[n]);
            }
            add(panel12Months[i]);
        }
    }
    public void addDayToDataBase(int i, int m, int z){
        try {
            String day = "2023-" + (String.format("%02d", i+1)) + "-" + (String.format("%02d" , m)) + "";
            if(QueryFunctionPanels.setEntryExitHour()) {
                operations.addDayToDataBaseQuery(Worker.idWorker, Worker.sectionName, day, Schedule.entryHour, Schedule.exitHour, Schedule.extraHour, Schedule.freeDay, Schedule.profsickleave, Schedule.festive, Schedule.ceased);
                QueryFunctionPanels.fillCalendarColours();
            }
            else{
                JOptionPane.showMessageDialog(null,"You have to choose schedule !"," Error !",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," You can't add that day !"," Error !",JOptionPane.ERROR_MESSAGE);
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
    public void deleteDayFromDataBase(int idWorker, String workDay){
        try {
            int optionPane = JOptionPane.showConfirmDialog(null, "Do you wish to remove " + workDay + " from Worker " + Worker.name + " ?", "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(optionPane == 0) {
                operations.deleteDayFromDataBase(idWorker, workDay);
                JOptionPane.showMessageDialog(null, "Day " + workDay + " deleted", " Done !", JOptionPane.ERROR_MESSAGE);
                QueryFunctionPanels.fillCalendarColours();
            }else{
                JOptionPane.showMessageDialog(null, "Day " + workDay + " not removed", " Error !", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"You're not working that day !"," Error !",JOptionPane.ERROR_MESSAGE);
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
    public void editDayFromDataBase(int i, int m, int z){
        try {
            String day = "2023-" + (String.format("%02d", i+1)) + "-" + (String.format("%02d" , m)) + "";
            if(QueryFunctionPanels.setEntryExitHour()) {
                operations.updateDayToDataBaseQuery(Worker.idWorker, Worker.sectionName, day, Schedule.entryHour, Schedule.exitHour, Schedule.extraHour, Schedule.freeDay, Schedule.profsickleave, Schedule.festive, Schedule.ceased);
                QueryFunctionPanels.fillCalendarColours();
            }
            else{
                JOptionPane.showMessageDialog(null," You have to choose schedule !","¡ Error !",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"You cannot edit a not working day ! Pulse 'Add' for add schedule !"," Error !",JOptionPane.ERROR_MESSAGE);
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
    public void setDayTypesFromDataBase(int idWorker, String workDay){
        try {
            operations.setDayTypeFromDataBase(idWorker, workDay);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"You aren't working that day !"," Error !",JOptionPane.ERROR_MESSAGE);
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }

    public void setDayScheduleToTable(JPanel panel, String day){
        ResultQueryPanel resultQueryPanel = new ResultQueryPanel(panel);
        panel.removeAll();
        try {
            resultQueryPanel.daySelectedSchedule(panel, day);
        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
}
