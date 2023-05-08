package workcalendar;
import java.awt.*;

public class SharedVariables {
    public int screenHeight;
    public int screenWidth;
    public final String[] daysName = {"Dom","Lun","Mar","Mie","Jue","Vie","Sab"};
    public final String[] monthsName = {"Enero", "Febero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    public SharedVariables() {
        Toolkit myScreen = Toolkit.getDefaultToolkit();
        Dimension screenSize = myScreen.getScreenSize();
        screenHeight = screenSize.height - 100;
        screenWidth = screenSize.width - 100;
    }

}