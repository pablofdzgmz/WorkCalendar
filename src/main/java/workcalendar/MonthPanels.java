package workcalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static workcalendar.CalendarFunctions.monthDays;
import static workcalendar.CalendarFunctions.zeller;

public class MonthPanels extends JPanel {
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
            int contador=0;
            //Fill empty first days of month with JLabel
            for(int k=0;k<z;k++) {
                labelFillMonth[k] = new JLabel(" ");
                panel12Months[i].add(labelFillMonth[k]);
                contador++;
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
                    }
                });
                contador++;
            }
            //Fill empty GridLayout spaces with JLabel
            for(int n=0; n<(42-contador); n++){
                labelFillMonth[n] = new JLabel(" ");
                panel12Months[i].add(labelFillMonth[n]);
            }
            add(panel12Months[i]);
        }
    }
}
