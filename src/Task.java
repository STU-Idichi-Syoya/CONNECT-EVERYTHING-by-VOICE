
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author KITT
 */
public class Task {

    String name;
    private ArrayList<String> day = new ArrayList<String>();
    private ArrayList<String> 全体 = new ArrayList<String>();
    String todaysTask;
    String BaseData;

    public void load() throws FileNotFoundException, IOException {
        File file = new File("./text./" + name + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        this.name = br.readLine();
        while ((line = br.readLine()) != null) {
            String a[] = new String[1];
            a = line.split(":", 0);
            day.add(a[1]);
        }

    }

    public void save(String voiceDay) {
        File file = new File("./text./" + name + ".txt");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 加算される現在時間の取得(Calender型)
        Calendar cal = Calendar.getInstance();

        // 日時を加算する
        cal.add(Calendar.MONTH, TranceInt(voiceDay));

        // Calendar型の日時をDate型に変換
        Date d1 = cal.getTime();
        String dayState = sdf.format(d1);
        try (FileWriter w = new FileWriter(file, true)) {
            w.write(name + "\r\n");
            w.write(dayState + ":");
            int i = BaseData.lastIndexOf("予定") + 2;
            BaseData = BaseData.substring(0, i);

            w.write(BaseData + "\r\n");
            w.flush();
        } catch (IOException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void serch(String voiceDay) throws IOException {

        File file = new File("./text./" + name + ".txt");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 加算される現在時間の取得(Calender型)
        Calendar cal = Calendar.getInstance();

        // 日時を加算する
        cal.add(Calendar.MONTH, TranceInt(voiceDay));

        // Calendar型の日時をDate型に変換
        Date d1 = cal.getTime();
        String dayState = sdf.format(d1);

        if (day.contains(dayState)) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int flg = 0;
            while ((line = br.readLine()) != null) {
                if (line.equals(dayState)) {
                    todaysTask += line.substring(9) + "｡";//20170913: これは9文字
                }
            }

        } else {
            todaysTask = "予定はありません";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTodaysTask() {
        return todaysTask;
    }

    private int TranceInt(final String day) {
        int AfterDay = 0;
        Map<String, Integer> p = new HashMap<String, Integer>() {
            {
                put("明日", 1);
                put("今日", 0);
                put("明後日", 2);
                put("明々後日", 3);
            }
        };
        for (String key : p.keySet()) {
            if (day.contains(key)) {
                AfterDay = p.get(key).intValue();
                int i = day.indexOf(key);
                BaseData = day.substring(i + key.length());
            }
        }
        if (day.contains("日後")) {
            AfterDay = Integer.parseInt(day.replaceAll("[^0-9]", ""));
            int i = day.indexOf("日後");
            BaseData = day.substring(i + 2);
        }
        return AfterDay;
    }
}
