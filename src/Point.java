/**
 * Created by NjuMzc on 2017/1/20.
 */
public class Point {
    private double lat;
    private double lng;
    private double ele;

    public boolean hasConcerned;

    public Point(double lat,double lng,double ele){
        this.lat=lat;
        this.lng=lng;
        this.ele=ele;

        hasConcerned=false;
    }

    public Point(double lat,double lng){
        this.lat=lat;
        this.lng=lng;
        this.ele=0;

        hasConcerned=false;
    }

    public void setElevation(double ele){
        this.ele=ele;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getEle() {
        return ele;
    }

    public void setEle(double ele){
        this.ele=ele;
    }
}
