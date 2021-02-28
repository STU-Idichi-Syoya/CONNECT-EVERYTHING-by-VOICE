		//要再設計！！！！

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class mic {

    private byte[] voiceData = new byte[100000];
    private String msg = null;
    final private String file = "voice.raw";
    PlayAudio sound = PlayAudio.getInstance();

    public String go() throws IOException, LineUnavailableException {
    DataLine.Info info;
    TargetDataLine targetDataLine = null;
       
        Play("./WAVE/click.wav");
        try {
            Thread.sleep(500); //1000ミリ秒Sleepする
        } catch (InterruptedException e) {
        }
        // リニアPCM 16000Hz 16bit モノラル 符号付き リトルエンディアン
        AudioFormat linearFormat = new AudioFormat(16000, 16, 1, true, false);

        // ターゲットデータラインを取得する
        try{
        info = new DataLine.Info(TargetDataLine.class, linearFormat);
        targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        }catch(java.lang.IllegalArgumentException e){
        	TimeCounter t=new TimeCounter();
        	t.基準=6000;
            Play("./WAVE/NOTMIC.wav");
            System.out.println("↖■ボタンをクリックしてもう一度お試しください");
            while(t.isTime());
            
        }
        // ターゲットデータラインをオープンする
        targetDataLine.open(linearFormat);

        // マイク入力開始
        targetDataLine.start();

        // ターゲットデータラインから入力ストリームを取得する
        AudioInputStream linearStream = new AudioInputStream(targetDataLine);

        // 入力ストリームから音声データをByte配列へ取得する
        linearStream.read(voiceData, 0, voiceData.length);

        // マイク入力停止
        targetDataLine.stop();

        // ターゲットデータラインをクローズする
        targetDataLine.close();
        Play("./WAVE/end.wav");

        // rawファイルへ書き出す
        File audioFile = new File(file);
        ByteArrayInputStream baiStream = new ByteArrayInputStream(voiceData);
        AudioInputStream aiStream = new AudioInputStream(baiStream, linearFormat, voiceData.length);
        AudioSystem.write(aiStream, AudioFileFormat.Type.AU, audioFile);
        aiStream.close();
        baiStream.close();

       try{
        Go_String str = new Go_String(file);
        msg = str.getResalt();
       }catch(java.net.UnknownHostException w){
           PRINT.getInstance().printf("インターネットに接続されていません。");
       }
        return msg;
    }

    public void Play(String path) {
        sound.play(path,true,false);
    }
}
