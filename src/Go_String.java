/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.databind.JsonNode;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author KITT
 */
public class Go_String {

    String resalt = null;

    public Go_String(String filepath) throws IOException {
        String responseData = null;

        ObjectMapper ob = new ObjectMapper();

        String boundary = "JT49KPkVKrz2Y4YrGhLezUP8fWHU6kARBrpKRpuNsjXm";//注意
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(filepath);
        Charset charset = StandardCharsets.UTF_8;//キャラセットをUTF-8にする
        byte[] vb = Files.readAllBytes(p);//voice.rawを読み込む
        try {
            URL url = new URL("https://api.apigw.smt.docomo.ne.jp/amiVoice/v1/recognize?APIKEY=342e37762f6e54794d6357526537436964676e5a3649462f3857702f4c6e795466766f50394d4159626938");

            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setDoOutput(true);

            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream outputStream = con.getOutputStream()) {
                outputStream.write(("--" + boundary + "\r\n").getBytes(charset));
                outputStream.write(("Content-Disposition: form-data; name=\"v\"\r\n").getBytes(charset));
                outputStream.write(("\r\n").getBytes(charset));
                outputStream.write(("on").getBytes(charset));
                outputStream.write(("\r\n--" + boundary + "\r\n").getBytes(charset));
                outputStream.write(("Content-Disposition: form-data; name=\"a\"; filename=\"audio_sample_amivoice.raw\"\r\n").getBytes(charset));
                outputStream.write(("Content-Type: application/octet-stream\r\n").getBytes(charset));
                outputStream.write(("\r\n").getBytes(charset));
                outputStream.write(Files.readAllBytes(p));
                outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes(charset));
                outputStream.flush();
            }

            //受信
            StringBuilder sb = new StringBuilder();
            try (InputStream stream = con.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while ((responseData = reader.readLine()) != null) {
                    sb.append(responseData);
                }
            }
            resalt = sb.toString();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Go_String.class.getName()).log(Level.SEVERE, null, ex);
        }

        JsonNode ro = ob.readTree(resalt);

        resalt = ro.path("text").textValue();
        

    }

    public String getResalt() {
        return resalt;
    }
    /**
     * Get the value of string
     *
     * @return the value of string
     */

}
