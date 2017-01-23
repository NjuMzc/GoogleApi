import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NjuMzc on 2017/1/23.
 */
public class Main4 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NamingException {
        EarthDAO dao = EarthDAO.getInstance();

        ArrayList<Point> originList=dao.getDealedPoints();

        for(int i=0;i<13;i++){
            int temp = i+1;
            if(temp==8){
                continue;
            }
            ArrayList<Point> points = dao.getPoints("dam"+String.valueOf(temp));
            System.out.println("处理dam"+String.valueOf(temp));
            for(int j=0;j<points.size();j++){
                Point p = points.get(j);
                if(p.getEle()==0){
                    for(int k=0;k<originList.size();k++){
                        Point op = originList.get(k);
                        if(p.getLat()==op.getLat() && p.getLng()==op.getLng()){
                            op.setEle(0);
                            break;
                        }
                    }
                }
            }
        }

        dao.setDbname("damAfter");
        dao.savePoint(originList);

    }
}
