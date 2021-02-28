/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author KITT
 */
public class GO_weather {

    Charset charset;
    String today_Weather = "";
    int today_temp;
    String tomorrow_Weather = "";

    public String getTomorrow_Weather() {
        return tomorrow_Weather;
    }

    public int getTomorrow_temp() {
        return tomorrow_temp;
    }
    int tomorrow_temp;
    String area;
    String work = "";
    String work2 = "";

    public int getToday_temp() {
        return today_temp;
    }

    public String getArea() {
        return area;
    }

    public String getToday_Weather() {
        return today_Weather;
    }

    public boolean istodayError() {
        return today_temp == -50;
    }

    public boolean istomorrowError() {
        return tomorrow_temp == -50;
    }

    public void tenki(String location, String area) throws MalformedURLException, IOException {
        this.area = area;
        String line;
        URL url = new URL("http://weather.livedoor.com/forecast/webservice/json/v1?city=" + location);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        String json_st = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

            while ((line = reader.readLine()) != null) {
                json_st += line;
            }
        }

        ObjectMapper ob = new ObjectMapper();
        JsonNode json = ob.readTree(json_st);
        //参考: https://fasterxml.github.io/jackson-databind/javadoc/2.2.0/com/fasterxml/jackson/databind/JsonNode.html

        today_Weather = json.get("forecasts").get(0).get("telop").textValue();
        tomorrow_Weather = json.get("forecasts").get(1).get("telop").textValue();

        try {
            work = json.get("forecasts").get(0).get("temperature").get("max").get("celsius").textValue();//なぜかテキストにしないとNULLを返す
        } catch (NullPointerException e) {
            work = "-50";
        } finally {
            today_temp = Integer.parseInt(work);
        }
        
        try {
            work2 = json.get("forecasts").get(1).get("temperature").get("max").get("celsius").textValue();
        } catch (NullPointerException a) {
            work2 = "-50";
        } finally {
            tomorrow_temp = Integer.parseInt(work2);
        }

    }
}



    

//temperature")
