package workcalendar;

import javax.swing.*;
import java.awt.*;

public class QueryFunctionPanels extends JPanel {
    public static JPanel queryPanel = new JPanel();
    public JPanel functionMainPanel = new JPanel();
    public JPanel[] functionPanel = new JPanel[3];
    public static Color dayColour;
    public static ButtonGroup typeOfFreeDay, scheduleButtonGroup;
    public static JRadioButton jRadioButtonMorning, jRadioButtonAfternoon, jRadioButtonNight, jRadioButtonWhistle; // journey buttons
    public static JCheckBox jCheckBoxExtra, jCheckBoxProfSickLeave, jCheckBoxCommfSickLeave, jCheckBoxCeased; // extra type button
    public static JRadioButton jRadioButtonHolydays, jRadioButtonMedic, jRadioButtonAgreement, jRadioButtonOwnBusiness; //type of free day buttons
    public static JToggleButton jToggleButtonAddDay, jToggleButtonEditDay, jToggleButtonDeleteDay; //type of query buttons
    public static JComboBox jComboBoxIdBadBoys; // IdBadBoys combobox
    public JLabel jLabelBadBoyId, jLabelBadBoyName, jLabelBadBoyIdGroup; //Query extra hours panel labels
    public static JTextField jTextFieldBadBoyName, jTextFieldBadBoyGroup, jTextFieldCheckDayForExtraHours; //Query extra hours text fields
    public static JButton jButtonAddFullCalendar, jButtonWhoNextExtra;
    public QueryFunctionPanels(){
        SharedVariables shared = new SharedVariables();
        //setBounds(5, shared.alturaPantalla/2,shared.anchoPantalla-25, shared.alturaPantalla/2 );
        //Main Result Panel
        setLayout(new GridLayout(1,2));
        queryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Consultas"));
        queryPanel.setLayout(new GridLayout(1,1));
        add(queryPanel);
        //Building Main Function Panel
        functionMainPanel.setLayout(new GridLayout(3,3));
        functionMainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Funciones"));
        //Building Edit Journey Panel
        setdEditJourneyComponents();
        setExtraTypeComponents();
        setTypeOfFReeDayComponents();
        setTypeOfQueryComponents();
        addEditJourneyComponents();
        //Building Extra Hours Query Pannel
        functionPanel[1].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Selección operario"));
        functionPanel[1].setLayout(new GridLayout(3,3));
        setQueryPannelComponents();
        addQueryPannelComponents();
        //Building Query Buttons
        setBadBoyQueryComponents();
        addBadBoyQueryComponents();
        //Extra hours Query
        //queryBadBoyID();
        //queryBadBoyDataByID();
        functionPanel[2].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Consultas"));
        functionPanel[2].setLayout(new GridLayout(3,3));
        //Add Panels to Main Panel
        functionMainPanel.add(functionPanel[0]);functionMainPanel.add(functionPanel[1]);functionMainPanel.add(functionPanel[2]);
        add(functionMainPanel);
        //new ResultQueryPanel(queryPanel);
    }

    public void setdEditJourneyComponents(){
        functionPanel[0] = new JPanel();functionPanel[1] = new JPanel();functionPanel[2] = new JPanel();
        functionPanel[0].setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Editar horario"));
        functionPanel[0].setLayout(new GridLayout(3,1));
        jRadioButtonMorning = new JRadioButton("Mañana (06:00 - 14: 00)");
        jRadioButtonAfternoon = new JRadioButton("Tarde (14:00 - 23:00)");
        jRadioButtonNight = new JRadioButton("Noche (22:00 - 06:00)");
        jRadioButtonWhistle = new JRadioButton("T/N (08:00 - 22:00)");
        scheduleButtonGroup = new ButtonGroup();
        scheduleButtonGroup.add(jRadioButtonMorning); scheduleButtonGroup.add(jRadioButtonAfternoon);
        scheduleButtonGroup.add(jRadioButtonNight); scheduleButtonGroup.add(jRadioButtonWhistle);
    }
    public void setExtraTypeComponents(){
        jCheckBoxExtra = new JCheckBox("Extra");
        jCheckBoxProfSickLeave = new JCheckBox("Baja laboral");
        jCheckBoxCommfSickLeave = new JCheckBox("Enfermedad común");
        jCheckBoxCeased = new JCheckBox("Cesado");
    }
    public void setTypeOfFReeDayComponents(){
        jRadioButtonHolydays = new JRadioButton("Vacaciones");
        jRadioButtonMedic = new JRadioButton("Medico");
        jRadioButtonAgreement = new JRadioButton("Convenio");
        jRadioButtonOwnBusiness = new JRadioButton("Asuntos propios");
        typeOfFreeDay = new ButtonGroup();
        typeOfFreeDay.add(jRadioButtonHolydays);typeOfFreeDay.add(jRadioButtonMedic);typeOfFreeDay.add(jRadioButtonAgreement);typeOfFreeDay.add(jRadioButtonOwnBusiness);
    }
    public void setTypeOfQueryComponents(){
        jToggleButtonAddDay = new JToggleButton("Añadir");
        jToggleButtonAddDay.addActionListener(e -> {
            QueryFunctionPanels.scheduleButtonGroup.clearSelection();
            QueryFunctionPanels.typeOfFreeDay.clearSelection();
        });
        jToggleButtonEditDay = new JToggleButton("Editar");
        jToggleButtonEditDay.addActionListener(e -> {
            QueryFunctionPanels.scheduleButtonGroup.clearSelection();
            QueryFunctionPanels.typeOfFreeDay.clearSelection();
        });
        jToggleButtonDeleteDay = new JToggleButton("Borrar");
    }
    public void addEditJourneyComponents(){
        functionPanel[0].add(jRadioButtonMorning);functionPanel[0].add(jRadioButtonAfternoon);functionPanel[0].add(jRadioButtonNight);
        functionPanel[0].add(jRadioButtonWhistle);functionPanel[0].add(jToggleButtonAddDay);functionPanel[0].add(jCheckBoxExtra);
        functionPanel[0].add(jCheckBoxProfSickLeave);functionPanel[0].add(jCheckBoxCommfSickLeave);functionPanel[0].add(jCheckBoxCeased);
        functionPanel[0].add(jToggleButtonEditDay);functionPanel[0].add(jRadioButtonHolydays);functionPanel[0].add(jRadioButtonMedic);
        functionPanel[0].add(jRadioButtonAgreement);functionPanel[0].add(jRadioButtonOwnBusiness);functionPanel[0].add(jToggleButtonDeleteDay);
    }
    public void setQueryPannelComponents(){
        jLabelBadBoyId = new JLabel("Codigo");
        jLabelBadBoyName = new JLabel("Nombre");
        jLabelBadBoyIdGroup = new JLabel("Seccion");
        jComboBoxIdBadBoys = new JComboBox();jComboBoxIdBadBoys.setEditable(false);
        jTextFieldBadBoyName = new JTextField();
        jTextFieldBadBoyGroup = new JTextField();
        jTextFieldBadBoyName.setEditable(false);jTextFieldBadBoyName.setText("Nombre del operario");
        jTextFieldBadBoyGroup.setEditable(false);jTextFieldBadBoyGroup.setText("Seccion");
    }
    public void addQueryPannelComponents(){
        functionPanel[1].add(jLabelBadBoyId);
        functionPanel[1].add(jComboBoxIdBadBoys);
        functionPanel[1].add(jLabelBadBoyName);
        functionPanel[1].add(jTextFieldBadBoyName);
        functionPanel[1].add(jLabelBadBoyIdGroup);
        functionPanel[1].add(jTextFieldBadBoyGroup);
    }
    public void setBadBoyQueryComponents(){
        jButtonAddFullCalendar = new JButton("Añadir calendario completo");
        //jButtonAddFullCalendar.addActionListener(e -> FillNewCalendar.fillBadBoyCalendar2023());
        jButtonWhoNextExtra = new JButton("Agente proximas horas extra");
        //jButtonWhoNextExtra.addActionListener(e -> checkNextBadBoyExtraHours(QueryFunctionPanels.queryPanel, jTextFieldCheckDayForExtraHours.getText()));
        jTextFieldCheckDayForExtraHours = new JTextField("");
        jTextFieldCheckDayForExtraHours.setSize(12,2);jTextFieldCheckDayForExtraHours.setEditable(false);
    }
    public void addBadBoyQueryComponents(){
        functionPanel[2].add(jButtonAddFullCalendar);
        functionPanel[2].add(jButtonWhoNextExtra);
        functionPanel[2].add(jTextFieldCheckDayForExtraHours);
    }
    /*public void queryBadBoyDataByID(){
        jComboBoxIdBadBoys.addActionListener(e -> {
            Operations queryBadBoyData = new Operations();
            String numBadBoy = ""+jComboBoxIdBadBoys.getSelectedItem();
            int num = Integer.parseInt(numBadBoy);
            try {
                queryBadBoyData.getBadBoyById(num);
                BadBoys badBoysById = queryBadBoyData.getBadBoyById(num);
                jTextFieldBadBoyName.setText(badBoysById.getName());
                jTextFieldBadBoyGroup.setText("" + badBoysById.getIdgroup());
                fillCalendarColours();
            } catch (SQLException | NumberFormatException ex ) {
                throw new RuntimeException(ex);
            }
        });
    }*/
    /*public static void fillCalendarColours(){
        Operations queryBadBoyData = new Operations();
        try {
            for(int i=0;i<12;i++){
                int dias= diasMes(2023,i+1);
                int z = zeller(2023, i+1);
                int contador=0;
                for(int k=0;k<z;k++) {
                    contador++;
                }
                for(int m=1; m<=dias; m++){
                    MonthPanels.daysButton[i][m+z+1].setBackground(queryBadBoyData.fillBadBoyCalendarColours(Integer.parseInt(QueryFunctionPanels.jComboBoxIdBadBoys.getSelectedItem().toString()), "2023-" + (String.format("%02d", i + 1)) + "-" + (String.format("%02d", m)) + ""));
                }
            }
        }catch (SQLException e){
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }*/
    /*public void queryBadBoyID(){
        Operations query = new Operations();
        try {
            ArrayList<Integer> id = query.getBadBoyId();
            for(int i=0;i<id.size();i++) {
                jComboBoxIdBadBoys.addItem(id.get(i));
            }
        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }*/
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
    public static void setTypeOfDay(){
        if(jRadioButtonHolydays.isSelected()){ Schedule.freeDay = "Vacaciones";
        }else if(jRadioButtonMedic.isSelected()){ Schedule.freeDay = "Medico";
        }else if(jRadioButtonAgreement.isSelected()){ Schedule.freeDay = "Convenio";
        }else if(jRadioButtonOwnBusiness.isSelected()){ Schedule.freeDay = "Asuntos propios";
        }else{ Schedule.freeDay = "";
        }
    }

    /*public void checkNextBadBoyExtraHours(JPanel panel, String day){
        ResultQueryPanel resultQueryPanel = new ResultQueryPanel(panel);
        panel.removeAll();
        try {
            if(QueryFunctionPanels.setEntryExitHour() && !day.equalsIgnoreCase(StringUtils.EMPTY)) {
                resultQueryPanel.checkNextBadBoyExtraHours(panel, day, QueryFunctionPanels.entryHour);
            }
            else{
                JOptionPane.showMessageDialog(null,"¡ Debes seleccionar un horario y un dia !","¡ Error !",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Something has gone wrong with data base " + e.getMessage());
        }
    }*/
}
