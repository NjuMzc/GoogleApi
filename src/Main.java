import javax.naming.NamingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Point> list = new ArrayList<>();
        EarthDAO dao = null;

        try {
            dao = EarthDAO.getInstance();
           // list = dao.getOriginPoints();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

     //   list=Crawler.getEle(list);
        ArrayList<Point> pointList = new ArrayList<Point> ();

        double x1=-17.104529;
        double y1=28.035494;
        double x2=-17.145768;
        double y2=28.146186;
        double dX=(x2-x1)/99;
        double dY=(y2-y1)/99;
        for(int i=0;i<100;i++){
            for(int j=0;j<100;j++){
                pointList.add(new Point(x1+i*dX,y1+j*dY));
            }
        }
        System.out.println(pointList.size());
        int counter=0;
        ArrayList result = new ArrayList();
        ArrayList tempResult = new ArrayList();
        ArrayList temp = new ArrayList();

        for(int i=0;i<pointList.size();i++){
            if(i==(pointList.size()-1)){
                System.out.println(temp.size());
                tempResult=Crawler.getEle(temp);
                result.addAll(tempResult);
            }
            if(counter==50){
                System.out.println(temp.size());
                tempResult=Crawler.getEle(temp);
                result.addAll(tempResult);
                temp.clear();
                counter=0;
            }
            temp.add(pointList.get(i));
            counter++;
        }
        try {
            dao.savePoint(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
  }
}


