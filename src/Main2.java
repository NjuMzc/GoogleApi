import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NjuMzc on 2017/1/21.
 */
public class Main2 {


//
//    public static void main(String[] args){
//
//        double x1=-17.104529;
//        double y1=28.035494;
//        double x2=-17.145768;
//        double y2=28.146186;
//        double dX=(x2-x1)/99;
//        double dY=(y2-y1)/99;
//
//
//        try {
//            ArrayList<Point> inputList = EarthDAO.getInstance().getPoints();
//
//            Point a = VCalculator.getPoint(-17.102648,28.058233,inputList);
//            Point b = VCalculator.getPoint(-17.104423,28.059210,inputList);
//
//            System.out.println("修正过的点坐标是："+a.getLat()+","+a.getLng()+","+a.getEle());
//            System.out.println("修正过的点坐标是："+b.getLat()+","+b.getLng());
//
//            ArrayList results = VCalculator.calculate(a,b,inputList,489,dX,dY);
//
//            for(int i=0;i<results.size();i++){
//                Point p = (Point)results.get(i);
//                for(int j=0;j<inputList.size();j++){
//                    if(p.getLat()==inputList.get(j).getLat()&&p.getLng()==inputList.get(j).getLng()){
//                        inputList.get(i).setEle(489);
//                    }
//                }
//            }
//
//            EarthDAO.getInstance().savePoint(inputList);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//

//    }
}
