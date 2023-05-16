package workcalendar;

public class Worker {
    public static int idWorker;
    public static String name;
    public static int section;
    public static String sectionName;
    public static int operatorLevel;

    public Worker(int idWorker, String name, int section, int operatorLevel) {
        this.idWorker = idWorker;
        this.name = name;
        this.section = section;
        this.operatorLevel = operatorLevel;
    }
    public Worker(int idWorker, String name, String sectionName, int operatorLevel) {
        this.idWorker = idWorker;
        this.name = name;
        this.sectionName = sectionName;
        this.operatorLevel = operatorLevel;
    }
    public Worker(){

    }
    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    public int getIdWorker() {
        return idWorker;
    }
    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSection() {
        return section;
    }
    public void setSection(int section) {
        this.section = section;
    }
    public int getOperatorLevel() {
        return operatorLevel;
    }
    public void setOperatorLevel(int operatorLevel) {
        this.operatorLevel = operatorLevel;
    }
}
