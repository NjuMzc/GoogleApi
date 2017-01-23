import sun.security.krb5.internal.EncASRepPart;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NjuMzc on 2017/1/22.
 */
public class Main3 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NamingException {

        ArrayList<Point> list  = EarthDAO.getInstance().getDealedPoints();

        double x1 = -16.940087;
        double y1 = 28.151006;
        double x2 = -16.458544;
        double y2 = 29.006739;

        double dx = (18.110823-15.408299)/300;
        double dy= (30.108682-26.320168)/300;

        double[] axis = {x1,y1,x2,y2};

        double height = 452;

        String dbname1 = "area1";
        String dbname2 = "dam1";

        Calculator.calculateV(list,height,axis,dx,dy,dbname1,dbname2);

    }

    //参数依次为：第一个点坐标，第二个点坐标，区域坐标（x,y,x,y），高度
    public static void damit(double x1 ,double y1,double x2,double y2,double[] axis,double height){
        //先获取所需要区域的数据
        EarthDAO dao = null;
        try {
            dao = EarthDAO.getInstance();
        } catch (NamingException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        ArrayList<Point> pointList = new ArrayList<Point> ();

        double xx1=axis[0];
        double yy1=axis[1];
        double xx2=axis[2];
        double yy2=axis[3];
        double dX=(xx2-xx1)/99;
        double dY=(yy2-yy1)/99;

        for(int i=0;i<100;i++){
            for(int j=0;j<100;j++){
                pointList.add(new Point(xx1+i*dX,yy1+j*dY));
            }
        }

        int counter=0;
        ArrayList result = new ArrayList();
        ArrayList tempResult = new ArrayList();
        ArrayList temp = new ArrayList();

        System.out.println("获取区域坐标中");
        for(int i=0;i<pointList.size();i++){
            if(i==(pointList.size()-1)){
                tempResult=Crawler.getEle(temp);
                result.addAll(tempResult);
            }
            if(counter==50){
                tempResult=Crawler.getEle(temp);
                result.addAll(tempResult);
                temp.clear();
                counter=0;
            }
            temp.add(pointList.get(i));
            counter++;
        }
        System.out.println("区域坐标获取完毕，挖湖中...");

       // result = MakeLakeLogic.MakingLake(result);

        try {
            System.out.println("挖湖完毕，保存区域坐标...");
            dao.setDbname("area1");
            dao.savePoint(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //保存了原来区域的数据了
        System.out.println("开始计算淹没区域");

        //接下来，计算淹没区域坐标
        Point a = VCalculator.getPoint(x1,y1,result,axis);
        Point b =VCalculator.getPoint(x2,y2,result,axis);


       ArrayList resultSet= VCalculator.calculate(a,b,result,height,axis);

        dao.setDbname("dam1");
        try {
            dao.savePoint(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
