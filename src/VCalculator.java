import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NjuMzc on 2017/1/21.
 */
public class VCalculator {


    public static ArrayList<Point> calculate(Point a, Point b, ArrayList<Point> inputList, double height, double[] axis) {
        ArrayList<Point> resultSet = new ArrayList<>();

        double x1=axis[0];
        double y1=axis[1];
        double x2=axis[2];
        double y2=axis[3];
        double dX=(x2-x1)/99;
        double dY=(y2-y1)/99;

        findPoint(a, b, height, dX, dY, inputList, resultSet,axis);

        System.out.println(resultSet.size());

        double ds = Math.abs(dX*dY*110000*110000);
        double v = 0;

        for(int i=0;i<resultSet.size();i++){
            double dv = ds*(height-resultSet.get(i).getEle());
            v+=dv;
        }

        System.out.println(v);
        return resultSet;
    }

    public static void findPoint(Point a, Point b, double height, double dx, double dy, ArrayList<Point> inputList, ArrayList<Point> resultList,double[] axis) {

        if (resultList.size() == 0) {
            a.hasConcerned = true;
            b.hasConcerned = true;
            resultList.add(a);
            resultList.add(b);
        }
        int last=0;
        int hist=0;
        while(true){
            hist = resultList.size();
            System.out.println(hist);
            for (int i = last; i < hist; i++) {
                Point p = resultList.get(i);

                Point p1 = getPoint(p.getLat() + dx, p.getLng(), inputList,axis);
                if (Judge(a,b,p1,height)){
                    resultList.add(p1);
                }
                Point p2 = getPoint(p.getLat() - dx, p.getLng(), inputList,axis);
                if (Judge(a,b,p2,height)){
                    resultList.add(p2);
                }
                Point p3 = getPoint(p.getLat(), p.getLng() + dy, inputList,axis);
                if (Judge(a,b,p3,height)){
                    resultList.add(p3);
                }
                Point p4 = getPoint(p.getLat(), p.getLng() - dy, inputList,axis);
                if (Judge(a,b,p4,height)){
                    resultList.add(p4);
                }
            }
            if(last==resultList.size()){
                break;
            }
            last=hist;

        }

    }

    private static boolean isUpLine(Point a, Point b, Point c) {
        double k = (a.getLng() - b.getLng() / (a.getLat() - b.getLat()));
        double judge = k * (c.getLat() - a.getLat()) + a.getLng() - c.getLng();
        return judge <=0;
    }

    public static Point getPoint(double lat, double lng, ArrayList<Point> inputList,double[] axis) {


        double x1=axis[0];
        double y1=axis[1];
        double x2=axis[2];
        double y2=axis[3];
        double dX=(x2-x1)/99;
        double dY=(y2-y1)/99;


        int x0= (int)((lat-x1)/dX);
        int y0=(int)((lng-y1)/dY);

        x0=Math.abs(x0);
        y0=Math.abs(y0);


        double xNow = x1+x0*dX;
        double yNow =  y1+y0*dY;

        System.out.println("修正的坐标："+xNow+" "+yNow);


        for (int i = 0; i < inputList.size(); i++) {
            Point tempPoint = inputList.get(i);
            if (Math.abs((xNow-tempPoint.getLat())) <=0.0005&&Math.abs((yNow-tempPoint.getLng())) <=0.0005) {
                return tempPoint;
            }
        }
        return null;
    }

    private static boolean Judge(Point a, Point b, Point p, double height) {
        System.out.println("判断点"+p.getLat()+","+p.getLng()+"海拔："+p.getEle()+"是否被判断过"+ p.hasConcerned);
        if (p != null && (!p.hasConcerned)) {
            p.hasConcerned = true;
            if (!isUpLine(a, b, p)) {
                if (p.getEle() <= height) {
                    System.out.println("判断通过！");
                    return true;
                }else {
                    System.out.println("判断失败！错误的高度");
                    return false;
                }
            } else {
                System.out.println("判断失败！错误的方向");
                return false;
            }
        } else {
            System.out.println("判断失败！已经判断过了");
            return false;
        }
    }
}
