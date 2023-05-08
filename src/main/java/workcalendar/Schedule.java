package workcalendar;

import java.awt.*;

public class Schedule {
    private static final String[] MORROW_SCHEDULE = {"06:00:00", "14:00:00"};
    private static final String[] AFTERNOON_SCHEDULE = {"14:00:00", "22:00:00"};
    private static final String[] NIGHT_SCHEDULE = {"22:00:00", "06:00:00"};
    private static final String[] WHISTLES_SCHEDULE = {"08:00:00", "17:00:00"};
    private static String[] daySchedule = new String[2];
    public static final Color AFTERNOON_DAY_COLOR = Color.YELLOW;
    public static final Color MORNING_DAY_COLOR = new Color (65, 234, 15);
    public static final Color NIGHT_DAY_COLOR = Color.RED;
    public static final Color WHISTLES_DAY_COLOR = new Color (255,128,0);

    public Schedule(){

    }
}
