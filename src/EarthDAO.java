import java.sql.*;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NjuMzc on 2017/1/20.
 */
public class EarthDAO {
    private  static EarthDAO instance=null;

    private Connection connection = null;

    private String dbaname = null;

    private EarthDAO() throws NamingException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/googleearth", "root", "960830");
        this.dbaname = "";
    }

    public void setDbname(String dbname){
        this.dbaname = dbname;
    }

    public static EarthDAO getInstance() throws NamingException, SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(instance==null){
            instance=new EarthDAO();
        }
        return  instance;
    }

    public void savePoint(ArrayList<Point> list) throws SQLException {
        PreparedStatement preparedStatement = null;
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO "+dbaname+" VALUES(?, ?, ? , ?)");
        connection.setAutoCommit(false);
        for (int i = 0; i < list.size(); i++) {
            statement.setInt(1, i + 1);
            statement.setString(2, list.get(i).getLat() + "");
            statement.setString(3, list.get(i).getLng() + "");
            statement.setString(4, list.get(i).getEle() + "");
            statement.addBatch();
        }
        statement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
        statement.close();
    }

    public ArrayList<Point> getOriginPoints() throws SQLException {
        ArrayList<Point> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        String sql = "SELECT zambizi.lat,zambizi.lng AS lat,lng FROM zambizi WHERE 1";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeQuery();

        ResultSet results=null;

        results = preparedStatement.getResultSet();

        while(results.next()){
            String latStr = results.getString(1);
            String lngStr = results.getString(2);

            double lat = -1*parse(latStr);
            double lng = parse(lngStr);

            Point p = new Point(lat,lng);
            list.add(p);
        }

        return list;
    }

    public ArrayList<Point> getDealedPoints() throws SQLException {
        ArrayList<Point> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        String sql = "SELECT lat,lng ,elevation AS lat,lng,elevation FROM point2 WHERE 1";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeQuery();

        ResultSet results=null;

        results = preparedStatement.getResultSet();

        while(results.next()){
            String latStr = results.getString(1);
            String lngStr = results.getString(2);
            String eleStr = results.getString(3);
            double lat = Double.valueOf(latStr);
            double lng = Double.valueOf(lngStr);
            double elevation = Double.valueOf(eleStr);

            Point p = new Point(lat,lng,elevation);
            list.add(p);
        }

        return list;
    }

    private double parse(String input){
        double du =Double.valueOf( input.substring(0,2));
        double fen=Double.valueOf( input.substring(2,4))/60;
        double miao=Double.valueOf( input.substring(4,6)+"."+input.substring(6,8))/3600;

        return du+fen+miao;
    }

    public ArrayList<Point> getPoints(String dbname) throws SQLException {
        ArrayList<Point> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;

        String sql = "SELECT lat,lng ,elevation AS lat,lng,elevation FROM "+dbname+" WHERE 1";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeQuery();

        ResultSet results=null;

        results = preparedStatement.getResultSet();

        while(results.next()){
            String latStr = results.getString(1);
            String lngStr = results.getString(2);
            String eleStr = results.getString(3);
            double lat = Double.valueOf(latStr);
            double lng = Double.valueOf(lngStr);
            double elevation = Double.valueOf(eleStr);

            Point p = new Point(lat,lng,elevation);
            list.add(p);
        }

        return list;
    }
}
