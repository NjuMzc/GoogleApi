import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NjuMzc on 2017/1/22.
 */
public class Calculator {

    public static double calculateV(ArrayList<Point> input,double height,double[] axis,double dx,double dy,String dbname1,String dbname2) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NamingException {
        double x1 = axis[0];
        double y1 = axis[1];
        double x2 = axis[2];
        double y2 = axis[3];

        double ds = 1372140;
        double v = 0;

        int counter = 0;

        //先找出所有的区域点
        ArrayList<Point> areaList = new ArrayList<>();
        ArrayList<Point> damList = input;
        for(int i=0;i<damList.size();i++){
            Point p = damList.get(i);
            if(p.getLat()>=x1 && p.getLat()<=x2 && p.getLng()>=y1 && p.getLng()<=y2){
                areaList.add(p);
                if(p.getEle() <= height){
                    v += (height-p.getEle())*ds;
                    counter++;
                    p.setEle(0);
                }
            }
        }

        System.out.println("点个数："+ counter+" 面积："+ counter*ds +"面积元"+ ds);
        System.out.println("Dx , Dy"+ dx +" "+dy);

        System.out.println("淹没面积是："+ counter*ds );

        System.out.println("计算体积得："+ v);

//        EarthDAO.getInstance().setDbname(dbname1);
//        EarthDAO.getInstance().savePoint(areaList);
        EarthDAO.getInstance().setDbname(dbname2);
        EarthDAO.getInstance().savePoint(damList);

        return v;
    }
}
