
import java.applet.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PlayAudio {

    private URL url;
    private AudioClip audioClip = null;
    private static final PlayAudio PLAYAUDIO = new PlayAudio();
    TimeCounter c = new TimeCounter();

    private void AudioClip() {
    }

    ;
    public static PlayAudio getInstance() {
        return PLAYAUDIO;

    }

    /**
     * 指定ファイルでAudioCli
     *
     * @param fileName
     */
    public void fileLoad(String fileName) {
        //long start=System.currentTimeMillis();
        try {
            url = new URL("file:"
                    + System.getProperty("user.dir") + "/" + fileName);
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        }

        //AudioClipの生成
        audioClip = Applet.newAudioClip(url);
        // long end=System.currentTimeMillis()-start;
        //System.out.println("audio:"+end);
    }

    /**
     * 再
     *
     * @param fileName
     */
    public void play(String fileName) {
        if (audioClip != null) {
            audioClip.stop();
        }
        fileLoad(fileName);

        audioClip.play();

    }

    public String play(String fileName, boolean DirectoryRandamflg, int timeout) {
        String songName = "";
        if (DirectoryRandamflg) {
            File dir = new File(fileName);
            File[] files = dir.listFiles();
            int len = files.length;
            if (len < 0) {
                len = 1;
            }
            int decide = new java.util.Random().nextInt(len);
            play(files[decide].getPath());
            songName = files[decide].getName();
        } else {
            play(fileName);
        }
//        c.基準 = timeout;//基準は決められる
//
//        c.setTimes();
//
//        for (;;) {
//            if (c.isTime()) {
//                audioClip.stop();
//                c.ResetTimes();
//                break;
//            }
//        }
        return songName;
    }

    public String play(String fileName, boolean Directoryflg) {//基準時間が無い場合は基準は1分

        String songName = play(fileName, Directoryflg, 70 * 1000);
        return songName;
    }

    public String play(String fileName, boolean isSHORT, boolean Directoryflg) {//列挙型にかきなおせ 
        String songName = "";
        if (isSHORT) {
            play(fileName);
        } else if (!isSHORT && Directoryflg) {
            songName = play(fileName, true);
        } else if (!isSHORT && !Directoryflg) {
            songName = play(fileName, Directoryflg);

        } else if (!isSHORT) {
            songName = play(fileName, false);
        }
        return songName;
    }

    /**
     * ループ
     *
     * @param fileName
     */
    public void loop(String fileName) {
        fileLoad(fileName);
        audioClip.loop();
    }

    /**
     * 停止
     */
    public void stop() {
        audioClip.stop();
    }
}
