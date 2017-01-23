import java.util.ArrayList;

public class MakeLakeLogic {
    public static ArrayList<Point> MakingLake(ArrayList<Point> rawList) {
        ArrayList<Point> newList = new ArrayList<Point>();
        ArrayList<Point> tempList = new ArrayList<Point>();
        ArrayList<Double> LngAxis = new ArrayList<Double>();
        double lng = 0;
        int size = rawList.size();

        for (int i = 0; i < size; i++) {
            lng = rawList.get(i).getLng();
            if (!LngAxis.contains(lng))
                LngAxis.add(lng);
        }

        for (int i = 0; i < LngAxis.size(); i++) {
            tempList = new ArrayList<Point>();
            lng = LngAxis.get(i);
            for (int j = 0; j < size; j++) {
                if (lng == rawList.get(j).getLng())
                    tempList.add(rawList.get(j));
            }
            newList.addAll(Ellipsoid(tempList));
        }

        return newList;
    }

    public static ArrayList<Point> Ellipsoid(ArrayList<Point> rList) {
        int index1 = 0;
        int index2 = 0;
        int a = 1;
        ArrayList<Point> nList = new ArrayList<Point>();
        for (int i = 0; i < rList.size()-2; i++) {
            if (rList.get(i).getEle() < 490 && rList.get(i).getEle() > 486)
                if (rList.get(i + 1).getEle() < 490
                        && rList.get(i + 1).getEle() > 486)
                    if (rList.get(i + 2).getEle() < 490
                            && rList.get(i + 2).getEle() > 486) {
                        index1 = i;
                        break;
                    }
        }
        for (int i = rList.size()-1; i >= 2; i--) {
            if (rList.get(i).getEle() < 490 && rList.get(i).getEle() > 486)
                if (rList.get(i - 1).getEle() < 490
                        && rList.get(i - 1).getEle() > 486)
                    if (rList.get(i - 2).getEle() < 490
                            && rList.get(i - 2).getEle() > 486) {
                        index2 = i;
                        break;
                    }
        }
        if (index1 * index2 != 0) {
            double y1 = rList.get(index1).getLat();
            double y2 = rList.get(index2).getLat();
            for (int i = 0; i < rList.size(); i++) {
                double x = rList.get(i).getLng();
                double y = rList.get(i).getLat();
                double newEle = rList.get(i).getEle();
                if (i >= index1 && i <= index2) {
                    newEle = 488
                            - 45
                            * (x - 27)
                            * a
                            * Math.sqrt(1 - Math.pow((2 * y - y1 - y2)
                            / (y1 - y2), 2));
                }
                nList.add(new Point(y, x, newEle));
            }
            return nList;
        } else
            return rList;
    }
}

