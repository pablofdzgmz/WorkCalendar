package workcalendar;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class QueryFunctionPanels extends JPanel {
    private static DbConnection myConnection;
    static Operations operations = new Operations();
    static JPanel queryPanel = new JPanel();
    static JPanel functionMainPanel = new JPanel();
    static JPanel[] functionPanel = new JPanel[3];
    static Color dayColour;
    static ButtonGroup typeOfFreeDay, scheduleButtonGroup;
    static JRadioButton jRadioButtonMorning, jRadioButtonAfternoon, jRadioButtonNight, jRadioButtonWhistle; // journey buttons
    static JRadioButton jRadioButtonExtra, jRadioButtonProfSickLeave, jRadioButtonFestive, jRadioButtonCeased; // extra type button
    static JRadioButton jRadioButtonHolydays, jRadioButtonMedic, jRadioButtonAgreement, jRadioButtonOwnBusiness; //type of free day buttons
    static JToggleButton jToggleButtonAddDay, jToggleButtonEditDay, jToggleButtonDeleteDay; //type of query buttons
    static JComboBox jComboBoxWorkerId; // IdBadBoys combobox
    static JLabel jLabelWorkerId, jLabelWorkerName, jLabelWorkerIdSection, JLabelWorkerOpLevel; //Query extra hours panel labels
    static JTextField jTextFieldWorkerName, jTextFieldWorkerSection, jTextFieldCheckDayForExtraHours, jTextFieldWorkerOpLevel; //Query extra hours text fields
    static JButton jButtonAddFullCalendar, jButtonWhoNextExtra, jButtonAddWorker, jButtonAddSection, jButtonAssignSectionToWorker;
    public QueryFunctionPanels(){ myConnection = new DbConnection();
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
        new ResultQueryPanel(queryPanel);
    }

    public static void setdEditJourneyComponents(){
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

    public static void setTypeOfFreeDayComponents(){
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
    public static void setTypeOfQueryComponents(){
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
    public static void setQueryPanelComponents(){
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
    public static void addQueryPannelComponents(){
        functionPanel[1].add(jLabelWorkerId); functionPanel[1].add(jComboBoxWorkerId);
        functionPanel[1].add(jLabelWorkerName); functionPanel[1].add(jTextFieldWorkerName);
        functionPanel[1].add(jLabelWorkerIdSection); functionPanel[1].add(jTextFieldWorkerSection);
        functionPanel[1].add(JLabelWorkerOpLevel); functionPanel[1].add(jTextFieldWorkerOpLevel);
    }
    public static void setBadBoyQueryComponents(){
        jButtonAddFullCalendar = new JButton("Add full new calendar");
        jButtonAddFullCalendar.addActionListener(e -> checkScheduleAndGroup());
        jButtonWhoNextExtra = new JButton("Next worker for extra hours");
        jButtonWhoNextExtra.addActionListener(e -> checkNextWorkerExtraHours(QueryFunctionPanels.queryPanel, jTextFieldCheckDayForExtraHours.getText()));
        jTextFieldCheckDayForExtraHours = new JTextField("");
        jTextFieldCheckDayForExtraHours.setSize(12,2);jTextFieldCheckDayForExtraHours.setEditable(false);
        jButtonAddWorker = new JButton("Create new worker into Data Base");
        jButtonAddWorker.addActionListener(e -> { createNewWorker();});
        jButtonAddSection = new JButton("Create new section into Data Base");
        jButtonAssignSectionToWorker = new JButton("Assign Worker to Section");
        jButtonAssignSectionToWorker.addActionListener(e -> { assignWorkerToSection();});
    }
    public static void addBadBoyQueryComponents(){
        functionPanel[2].add(jButtonAddFullCalendar); functionPanel[2].add(jButtonAddWorker);
        functionPanel[2].add(jButtonWhoNextExtra); functionPanel[2].add(jButtonAddSection);
        functionPanel[2].add(jTextFieldCheckDayForExtraHours); functionPanel[2].add(jButtonAssignSectionToWorker);
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

    public static void checkNextWorkerExtraHours(JPanel panel, String day){
        ResultQueryPanel resultQueryPanel = new ResultQueryPanel(panel);
        panel.removeAll();
        try {
            if(QueryFunctionPanels.setEntryExitHour() && !day.equalsIgnoreCase(StringUtils.EMPTY)) {
                resultQueryPanel.checkNextWorkerExtraHours(panel, day, Schedule.entryHour);
            }
            else{
                JOptionPane.showMessageDialog(null,"You have to choose schedule!"," Error !",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
    public static void checkScheduleAndGroup(){
        int option = 0;
        String []scheduleAndGroup = {"4th Turn Group A", "4th Turn Group B","4th Turn Group C", "4th Turn Group D", "Whistles"};
        JComboBox scheduleOption = new JComboBox(scheduleAndGroup);
        option = JOptionPane.showConfirmDialog(null, scheduleOption,"Select Schedule and Group", JOptionPane.YES_NO_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION) {
            if(scheduleOption.getSelectedIndex()==0) Schedule.fillFourthTurnCalendar2023(2,1);
            else if(scheduleOption.getSelectedIndex()==1) Schedule.fillFourthTurnCalendar2023(2,2);
            else if(scheduleOption.getSelectedIndex()==2) Schedule.fillFourthTurnCalendar2023(2,3);
            else if(scheduleOption.getSelectedIndex()==3) Schedule.fillFourthTurnCalendar2023(2, 4);
            else if(scheduleOption.getSelectedIndex()==4) Schedule.fillWhistleTurnCalendar2023();
            JOptionPane.showMessageDialog(null,"Turn added successfully!"," Great !",JOptionPane.INFORMATION_MESSAGE);
        }
        fillCalendarColours();
    }
    public static void createNewWorker(){
        Connection conn = myConnection.getMyConnection();
        String workerName;
        int sectionID;
        int operatorLevel;
        int workerID = askForWorkerID();
        if(workerID != 0) {
            workerName = askForWorkerName();
            if(!workerName.equalsIgnoreCase(StringUtils.EMPTY)) {
                sectionID = askForSectionID();
                if(sectionID != 0) {
                    operatorLevel = askForOperatorLevel();
                    try {
                        operations.addNewWorker(workerID,workerName,sectionID,operatorLevel);
                    } catch (SQLException e) {
                        try {
                            conn.setAutoCommit(false);
                            conn.rollback();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JOptionPane.showMessageDialog(null," You can't add that worker !"," Error !",JOptionPane.ERROR_MESSAGE);
                        System.err.println("Something has gone wrong with data base " + e.getMessage());
                    }
                }
            }
        }
    }
    public static int askForWorkerID(){
        boolean validID = false;
        int workerID = 0;
        int option = 0;
        JTextField jTextFieldWorkerId = new JTextField();
        do{
            try{
                option = JOptionPane.showConfirmDialog(null, jTextFieldWorkerId,"Insert Worker ID ", JOptionPane.YES_NO_CANCEL_OPTION);
                if(option==JOptionPane.OK_OPTION){
                    if(((int)(Math.log10(Integer.parseInt(jTextFieldWorkerId.getText()))+1)) == 8) {
                        validID = true;
                        workerID = Integer.parseInt(jTextFieldWorkerId.getText());
                    }else throw new InputMismatchException();
                }else{
                    JOptionPane.showMessageDialog(null,"Create new worker canceled ! "," Canceled !",JOptionPane.ERROR_MESSAGE);
                    validID = true;
                }
            }catch (NumberFormatException | InputMismatchException e){
                JOptionPane.showMessageDialog(null,"Only 8 digits allowed [0-9] ! "," Error !",JOptionPane.ERROR_MESSAGE);
            }
        }while(!validID);
        return workerID;
    }
    public static String askForWorkerName(){
        boolean validName = false;
        String workerName = StringUtils.EMPTY;
        int option = 0;
        JTextField jTextFieldWorkerName = new JTextField();
        do{
            try{
                option = JOptionPane.showConfirmDialog(null, jTextFieldWorkerName,"Insert Worker Name ", JOptionPane.YES_NO_CANCEL_OPTION);
                if(option==JOptionPane.OK_OPTION){
                    if(jTextFieldWorkerName.getText().matches("^[A-z]+\\s[A-z]*$")) {
                            validName = true;
                            workerName = jTextFieldWorkerName.getText();
                    }else throw new InputMismatchException();
                }else{
                    JOptionPane.showMessageDialog(null,"Create new worker canceled ! "," Canceled !",JOptionPane.ERROR_MESSAGE);
                    validName = true;
                }
            }catch (InputMismatchException e){
                JOptionPane.showMessageDialog(null,"Only characters allowed [a-Z] ! "," Error !",JOptionPane.ERROR_MESSAGE);
            }
        }while(!validName);
        return workerName;
    }
    public static int askForSectionID(){
        int sectionID = 0;
        int option = 0;
        String []section = {"1.- Logistica", "2.- Laboratorio","3.- Recursos humanos", "4.- Administracion", "5.- Informatica","6.- Produccion"};
        JComboBox sectionOption = new JComboBox(section);
        option = JOptionPane.showConfirmDialog(null, sectionOption,"Select section", JOptionPane.YES_NO_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION) {
            if(sectionOption.getSelectedIndex()==0) sectionID = 1;
            else if(sectionOption.getSelectedIndex()==1) sectionID = 2;
            else if(sectionOption.getSelectedIndex()==2) sectionID = 3;
            else if(sectionOption.getSelectedIndex()==3) sectionID = 4;
            else if(sectionOption.getSelectedIndex()==4) sectionID = 5;
            else if(sectionOption.getSelectedIndex()==6) sectionID = 6;
        }else
            JOptionPane.showMessageDialog(null,"Create new worker canceled ! "," Canceled !",JOptionPane.ERROR_MESSAGE);
        return sectionID;
    }
    public static int askForOperatorLevel(){
        int operatorLevel = 0;
        int option = 0;
        String []stringOperatorLevel = {"Nivel 1", "Nivel 2","Nivel 3", "Nivel 4", "Nivel 5","Nivel 6","Nivel 7"
                            ,"Nivel 8","Nivel 9","Nivel 10","Nivel 11","Nivel 12","Nivel 13","Nivel 14"};
        JComboBox operatorLevelOption = new JComboBox(stringOperatorLevel);
        option = JOptionPane.showConfirmDialog(null, operatorLevelOption, "Select operator level", JOptionPane.YES_NO_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION) {
            operatorLevel = operatorLevelOption.getSelectedIndex()+1;
        }else
            JOptionPane.showMessageDialog(null,"Create new worker canceled ! "," Canceled !",JOptionPane.ERROR_MESSAGE);
        return operatorLevel;
    }
    public static void assignWorkerToSection(){
        int option = 0;
        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        try {
            id = operations.getWorkerId();
            name = operations.getSectionName();

        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
        JComboBox jWorkerListComboBox = new JComboBox(id.toArray());
        JComboBox jSectionListComboBox = new JComboBox(name.toArray());
        jWorkerListComboBox.addActionListener(e -> {
            String numWorker = ""+jWorkerListComboBox.getSelectedItem();
            Worker.idWorker = (Integer.parseInt(numWorker));
            try {
                operations.getWorkerById(Worker.idWorker);
                Worker workerById = operations.getWorkerById(Worker.idWorker);
                jSectionListComboBox.setSelectedItem(workerById.getSectionName());
            } catch (SQLException | NumberFormatException ex ) {
                throw new RuntimeException(ex);
            }
        });
        Object[] comboBox = {jWorkerListComboBox, jSectionListComboBox};
        option = JOptionPane.showConfirmDialog(null, comboBox, "Select operator level", JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION) {
            updateWorkerSection(Integer.parseInt(jWorkerListComboBox.getSelectedItem().toString()), jSectionListComboBox.getSelectedIndex()+1);
        }else JOptionPane.showMessageDialog(null,"Select new section canceled ! "," Canceled !",JOptionPane.ERROR_MESSAGE);
    }
    public static void updateWorkerSection(int workerID, int sectionID){
        try {
                operations.updateWorkerSection(workerID, sectionID);
        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }
}
