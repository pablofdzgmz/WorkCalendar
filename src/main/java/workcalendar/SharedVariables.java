package workcalendar;
import java.awt.*;

public class SharedVariables {
    public int screenHeight;
    public int screenWidth;
    public final String[] daysName = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    public final String[] monthsName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public SharedVariables() {
        Toolkit myScreen = Toolkit.getDefaultToolkit();
        Dimension screenSize = myScreen.getScreenSize();
        screenHeight = screenSize.height - 100;
        screenWidth = screenSize.width - 100;
    }

}