package workcalendar;

public class Worker {
    private static int idWorker;
    private static String name;
    private static int section;
    private static int operatorLevel;

    public Worker(int idWorker, String name, int section, int operatorLevel) {
        this.idWorker = idWorker;
        this.name = name;
        this.section = section;
        this.operatorLevel = operatorLevel;
    }
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
