package workcalendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operations {
    private DbConnection myConnection;

    public Operations() {
        myConnection = new DbConnection();
    }
    public Worker getWorkerById(int id) throws SQLException {
        Worker worker = null;
        String stringQueryWorkerByID = "SELECT * from worker WHERE idworker=?";
        PreparedStatement queryWorkerByID;
        Connection conn = myConnection.getMyConnection();
        queryWorkerByID = conn.prepareStatement(stringQueryWorkerByID);
        queryWorkerByID.setInt(1, id);
        ResultSet resultSet = queryWorkerByID.executeQuery();
        while (resultSet.next()) {
            worker = new Worker(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4));
        }
        return worker;
    }
    public ArrayList<Integer> getWorkerId() throws SQLException {
        List<Integer> idlist = new ArrayList<>();
        Connection conn = myConnection.getMyConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT idworker FROM worker ORDER BY idworker ASC");
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            idlist.add(id);
        }
        return (ArrayList<Integer>) idlist;
    }
}
