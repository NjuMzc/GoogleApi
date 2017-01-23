import com.sun.deploy.net.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by NjuMzc on 2017/1/20.
 */

public class Crawler {
    private static String key = "&key=AIzaSyDRA5PsBV_sDnublrCdzWs89OEZkzxl0NQ";
    private static String api = "https://maps.googleapis.com/maps/api/elevation/json?";
    private static String charset = "utf-8";

    public static ArrayList<Point> getEle(ArrayList pointList) {
        //get请求返回结果
        ArrayList<Point> result = new ArrayList<>();
        String params = "locations=";
        for (int i = 0; i < pointList.size(); i++) {
            if (i != 0) {
                params += "|";
            }
            Point point = (Point) pointList.get(i);
            String lat = String.valueOf(point.getLat());
            String lng = String.valueOf(point.getLng());

            params += lat + "," + lng;
        }

        try {
            URL url = new URL(api + params + key);
            System.out.println(api + params + key);
            URLConnection con = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestProperty("Accept-Charset", charset);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;
            //响应失败
            if (httpURLConnection.getResponseCode() >= 300) {
                System.out.println(httpURLConnection.getResponseCode() );
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            try {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }

            } finally {

                if (reader != null) {
                    reader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }

            }


            JSONObject jo = new JSONObject(resultBuffer.toString());

            JSONArray jsonArray = jo.getJSONArray("results");


            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double elevation = jsonObject.getDouble("elevation");
                JSONObject joo = (JSONObject) jsonObject.get("location");
                double lat = joo.getDouble("lat");
                double lng = joo.getDouble("lng");

                Point point = new Point(lat,lng,elevation);

                result.add(point);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



        return result;
    }
}