package workcalendar;

public class CalendarFunctions {
    public static int zeller(int ano, int mes){
        int a = (14-mes) / 12;
        int y = ano - a;
        int m = mes + 12 * a - 2;
        int dia = 1, d;
        d = (dia + y + y / 4 - y / 100 + y / 400 + (31 *m) / 12) % 7;
        return (d);
    }
    public static boolean isLeapYear(int year){
        if(year % 4 == 0){
            if(year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            }else {
                return true;
            }
        }else {
            return false;
        }
    }
    public static int monthDays(int year, int month){
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            return 31;
        } else if(month == 2){
            if(isLeapYear(year)){
                return 29;
            }else{
                return 28;
            }
        }else{
            return 30;
        }
    }

}
