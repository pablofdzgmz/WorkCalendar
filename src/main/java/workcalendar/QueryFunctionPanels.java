package workcalendar;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryFunctionPanels extends JPanel {
    public static Operations operations = new Operations();
    public static JPanel queryPanel = new JPanel();
    public JPanel functionMainPanel = new JPanel();
    public JPanel[] functionPanel = new JPanel[3];
    public static Color dayColour;
    public static ButtonGroup typeOfFreeDay, scheduleButtonGroup;
    public static JRadioButton jRadioButtonMorning, jRadioButtonAfternoon, jRadioButtonNight, jRadioButtonWhistle; // journey buttons
    public static JRadioButton jRadioButtonExtra, jRadioButtonProfSickLeave, jRadioButtonFestive, jRadioButtonCeased; // extra type button
    public static JRadioButton jRadioButtonHolydays, jRadioButtonMedic, jRadioButtonAgreement, jRadioButtonOwnBusiness; //type of free day buttons
    public static JToggleButton jToggleButtonAddDay, jToggleButtonEditDay, jToggleButtonDeleteDay; //type of query buttons
    public static JComboBox jComboBoxWorkerId; // IdBadBoys combobox
    public JLabel jLabelWorkerId, jLabelWorkerName, jLabelWorkerIdSection, JLabelWorkerOpLevel; //Query extra hours panel labels
    public static JTextField jTextFieldWorkerName, jTextFieldWorkerSection, jTextFieldCheckDayForExtraHours, jTextFieldWorkerOpLevel; //Query extra hours text fields
    public static JButton jButtonAddFullCalendar, jButtonWhoNextExtra;
    public QueryFunctionPanels(){
        //setBounds(5, shared.alturaPantalla/2,shared.anchoPantalla-25, shared.alturaPantalla/2 );
        //Main Result Panel
        setLayout(new GridLayout(1,2));
        queryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Query Table"));
        queryPanel.setLayout(new GridLayout(1,1));
        add(queryPanel);
        //Building Main Function Panel
        functionMainPanel.setLayout(new GridLayout(3,3));
        functionMainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Functions"));
        //Building Edit Journey Panel
        setdEditJourneyComponents();
        setTypeOfFreeDayComponents();
        setTypeOfQueryComponents();
        addEditJourneyComponents();
        //Building Extra Hours Query Panel
        functionPanel[1].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select worker"));
        functionPanel[1].setLayout(new GridLayout(4,4));
        setQueryPanelComponents();
        addQueryPannelComponents();
        //Building Query Buttons
        setBadBoyQueryComponents();
        addBadBoyQueryComponents();
        //Extra hours Query
        queryWorkerID();
        queryWorkerDataByID();
        functionPanel[2].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Query"));
        functionPanel[2].setLayout(new GridLayout(3,3));
        //Add Panels to Main Panel
        functionMainPanel.add(functionPanel[0]);functionMainPanel.add(functionPanel[1]);functionMainPanel.add(functionPanel[2]);
        add(functionMainPanel);
        //new ResultQueryPanel(queryPanel);
    }

    public void setdEditJourneyComponents(){
        functionPanel[0] = new JPanel();functionPanel[1] = new JPanel();functionPanel[2] = new JPanel();
        functionPanel[0].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Edit Schedule"));
        functionPanel[0].setLayout(new GridLayout(3,1));
        jRadioButtonMorning = new JRadioButton("Morning (06:00 - 14: 00)");
        jRadioButtonAfternoon = new JRadioButton("Afternoon (14:00 - 23:00)");
        jRadioButtonNight = new JRadioButton("Night (22:00 - 06:00)");
        jRadioButtonWhistle = new JRadioButton("T/N (08:00 - 22:00)");
        scheduleButtonGroup = new ButtonGroup();
        scheduleButtonGroup.add(jRadioButtonMorning); scheduleButtonGroup.add(jRadioButtonAfternoon);
        scheduleButtonGroup.add(jRadioButtonNight); scheduleButtonGroup.add(jRadioButtonWhistle);
    }

    public void setTypeOfFreeDayComponents(){
        jRadioButtonExtra = new JRadioButton("Extra Hour");
        jRadioButtonProfSickLeave = new JRadioButton("Professional Sick Leave");
        jRadioButtonFestive = new JRadioButton("Festive");
        jRadioButtonCeased = new JRadioButton("Ceased");
        jRadioButtonHolydays = new JRadioButton("Holidays");
        jRadioButtonMedic = new JRadioButton("Medic");
        jRadioButtonAgreement = new JRadioButton("Agreement");
        jRadioButtonOwnBusiness = new JRadioButton("Own Business");
        typeOfFreeDay = new ButtonGroup();
        typeOfFreeDay.add(jRadioButtonHolydays);typeOfFreeDay.add(jRadioButtonMedic);
        typeOfFreeDay.add(jRadioButtonAgreement);typeOfFreeDay.add(jRadioButtonOwnBusiness);
        typeOfFreeDay.add(jRadioButtonExtra);typeOfFreeDay.add(jRadioButtonProfSickLeave);
        typeOfFreeDay.add(jRadioButtonFestive);typeOfFreeDay.add(jRadioButtonCeased);
    }
    public void setTypeOfQueryComponents(){
        jToggleButtonAddDay = new JToggleButton("Add");
        jToggleButtonAddDay.addActionListener(e -> {
            QueryFunctionPanels.scheduleButtonGroup.clearSelection();
            QueryFunctionPanels.typeOfFreeDay.clearSelection();
        });
        jToggleButtonEditDay = new JToggleButton("Edit");
        jToggleButtonEditDay.addActionListener(e -> {
            QueryFunctionPanels.scheduleButtonGroup.clearSelection();
            QueryFunctionPanels.typeOfFreeDay.clearSelection();
        });
        jToggleButtonDeleteDay = new JToggleButton("Delete");
    }
    public void addEditJourneyComponents(){
        functionPanel[0].add(jRadioButtonMorning);functionPanel[0].add(jRadioButtonAfternoon);functionPanel[0].add(jRadioButtonNight);
        functionPanel[0].add(jRadioButtonWhistle);functionPanel[0].add(jToggleButtonAddDay);functionPanel[0].add(jRadioButtonExtra);
        functionPanel[0].add(jRadioButtonProfSickLeave);functionPanel[0].add(jRadioButtonFestive);functionPanel[0].add(jRadioButtonCeased);
        functionPanel[0].add(jToggleButtonEditDay);functionPanel[0].add(jRadioButtonHolydays);functionPanel[0].add(jRadioButtonMedic);
        functionPanel[0].add(jRadioButtonAgreement);functionPanel[0].add(jRadioButtonOwnBusiness);functionPanel[0].add(jToggleButtonDeleteDay);
    }
    public void setQueryPanelComponents(){
        jLabelWorkerId = new JLabel("Code");
        jLabelWorkerName = new JLabel("Name");
        jLabelWorkerIdSection = new JLabel("Section");
        JLabelWorkerOpLevel = new JLabel("Operator level");
        jComboBoxWorkerId = new JComboBox();
        jComboBoxWorkerId.setEditable(false);
        jTextFieldWorkerName = new JTextField();
        jTextFieldWorkerSection = new JTextField();
        jTextFieldWorkerOpLevel = new JTextField();
        jTextFieldWorkerName.setEditable(false); jTextFieldWorkerName.setText("Worker name");
        jTextFieldWorkerSection.setEditable(false); jTextFieldWorkerSection.setText("Section");
        jTextFieldWorkerOpLevel.setEditable(false); jTextFieldWorkerOpLevel.setText("Operator Level");
    }
    public void addQueryPannelComponents(){
        functionPanel[1].add(jLabelWorkerId); functionPanel[1].add(jComboBoxWorkerId);
        functionPanel[1].add(jLabelWorkerName); functionPanel[1].add(jTextFieldWorkerName);
        functionPanel[1].add(jLabelWorkerIdSection); functionPanel[1].add(jTextFieldWorkerSection);
        functionPanel[1].add(JLabelWorkerOpLevel); functionPanel[1].add(jTextFieldWorkerOpLevel);
    }
    public void setBadBoyQueryComponents(){
        jButtonAddFullCalendar = new JButton("Add full new calendar");
        jButtonAddFullCalendar.addActionListener(e -> checkScheduleAndGroup());
        jButtonWhoNextExtra = new JButton("Next worker for extra hours");
        jButtonWhoNextExtra.addActionListener(e -> checkNextBadBoyExtraHours(QueryFunctionPanels.queryPanel, jTextFieldCheckDayForExtraHours.getText()));
        jTextFieldCheckDayForExtraHours = new JTextField("");
        jTextFieldCheckDayForExtraHours.setSize(12,2);jTextFieldCheckDayForExtraHours.setEditable(false);
    }
    public void addBadBoyQueryComponents(){
        functionPanel[2].add(jButtonAddFullCalendar);
        functionPanel[2].add(jButtonWhoNextExtra);
        functionPanel[2].add(jTextFieldCheckDayForExtraHours);
    }
    public static void queryWorkerDataByID(){
        jComboBoxWorkerId.addActionListener(e -> {
            String numWorker = ""+jComboBoxWorkerId.getSelectedItem();
            Worker.idWorker = (Integer.parseInt(numWorker));
            try {
                operations.getWorkerById(Worker.idWorker);
                Worker workerById = operations.getWorkerById(Worker.idWorker);
                jTextFieldWorkerName.setText(workerById.getName());
                jTextFieldWorkerSection.setText("" + workerById.getSectionName());
                jTextFieldWorkerOpLevel.setText("" + workerById.getOperatorLevel());
                fillCalendarColours();
            } catch (SQLException | NumberFormatException ex ) {
                throw new RuntimeException(ex);
            }
        });
    }
    public static void fillCalendarColours(){
        String day;
        try {
            for(int i=0;i<12;i++){
                int monthDays= CalendarFunctions.monthDays(2023,i+1);
                int z = CalendarFunctions.zeller(2023, i+1);
                int count=0;
                for(int k=0;k<z;k++) {
                    count++;
                }
                for(int m=1; m<=monthDays; m++){
                    day = "2023-" + (String.format("%02d", i + 1)) + "-" + (String.format("%02d", m)) + "";
                    MonthPanels.daysButton[i][m+z+1].setBackground(operations.fillWorkerCalendarColours(Worker.idWorker, day));
                }
            }
        }catch (SQLException e){
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
    public static void queryWorkerID(){
        try {
            ArrayList<Integer> id = operations.getWorkerId();
            for(int i=0;i<id.size();i++) {
                jComboBoxWorkerId.addItem(id.get(i));
            }
        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
    public static boolean setEntryExitHour(){
        boolean validHour = false;
        if(jRadioButtonMorning.isSelected()){
            Schedule.entryHour = Schedule.MORROW_SCHEDULE[0]; Schedule.exitHour = Schedule.MORROW_SCHEDULE[1];
            dayColour = Schedule.MORNING_DAY_COLOR;
            validHour = true;
        }else if(jRadioButtonAfternoon.isSelected()){
            Schedule.entryHour = Schedule.AFTERNOON_SCHEDULE[0]; Schedule.exitHour = Schedule.AFTERNOON_SCHEDULE[1];
            dayColour = Schedule.AFTERNOON_DAY_COLOR;
            validHour = true;
        }else if(jRadioButtonNight.isSelected()){
            Schedule.entryHour = Schedule.NIGHT_SCHEDULE[0]; Schedule.exitHour = Schedule.NIGHT_SCHEDULE[1];
            dayColour = Schedule.NIGHT_DAY_COLOR;
            validHour = true;
        }else if(jRadioButtonWhistle.isSelected()){
            Schedule.entryHour = Schedule.WHISTLES_SCHEDULE[0]; Schedule.exitHour = Schedule.WHISTLES_SCHEDULE[1];
            dayColour = Schedule.WHISTLES_DAY_COLOR;
            validHour = true;
        }
        return validHour;
    }
    public static void setTypeOfFreeDay(){
        if(jRadioButtonHolydays.isSelected()){ Schedule.freeDay = "Holidays";
        }else if(jRadioButtonMedic.isSelected()){ Schedule.freeDay = "Medic";
        }else if(jRadioButtonAgreement.isSelected()){ Schedule.freeDay = "Agreement";
        }else if(jRadioButtonOwnBusiness.isSelected()){ Schedule.freeDay = "Own Business";
        }else{ Schedule.freeDay = "";
        }
    }
    public static void setTypeOfDay() {
        if (jRadioButtonExtra.isSelected()) Schedule.extraHour = "Yes";
        else Schedule.extraHour = StringUtils.EMPTY;
        if (jRadioButtonProfSickLeave.isSelected()) Schedule.profsickleave = "Yes";
        else Schedule.profsickleave = StringUtils.EMPTY;
        if (jRadioButtonFestive.isSelected()) Schedule.festive = "Yes";
        else Schedule.festive = StringUtils.EMPTY;
        if (jRadioButtonCeased.isSelected()) Schedule.ceased = "Yes";
        else Schedule.ceased = StringUtils.EMPTY;
    }

    public void checkNextBadBoyExtraHours(JPanel panel, String day){
        ResultQueryPanel resultQueryPanel = new ResultQueryPanel(panel);
        panel.removeAll();
        try {
            if(QueryFunctionPanels.setEntryExitHour() && !day.equalsIgnoreCase(StringUtils.EMPTY)) {
                resultQueryPanel.checkNextWorkerExtraHours(panel, day, Schedule.entryHour);
            }
            else{
                JOptionPane.showMessageDialog(null,"You have to choose schedule!","¡ Error !",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
    public void checkScheduleAndGroup(){
        int option = 0;
        String []scheduleAndGroup = {"4th Turn Group A", "4th Turn Group B","4th Turn Group C", "4th Turn Group D", "Whistles"};
        JComboBox scheduleOption = new JComboBox(scheduleAndGroup);
        option = JOptionPane.showConfirmDialog(this, scheduleOption,"Select Schedule and Group", JOptionPane.YES_NO_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION) {
            if(scheduleOption.getSelectedIndex()==0) Schedule.fillFourthTurnCalendar2023(2);
            else if(scheduleOption.getSelectedIndex()==1) Schedule.fillFourthTurnCalendar2023(4);
            else if(scheduleOption.getSelectedIndex()==2) Schedule.fillFourthTurnCalendar2023(6);
            else if(scheduleOption.getSelectedIndex()==3) Schedule.fillFourthTurnCalendar2023(9);
            else if(scheduleOption.getSelectedIndex()==4) Schedule.fillWhistleTurnCalendar2023();
            JOptionPane.showMessageDialog(null,"Turn added successfully!","¡ Great !",JOptionPane.INFORMATION_MESSAGE);
        }
        fillCalendarColours();
    }
}
