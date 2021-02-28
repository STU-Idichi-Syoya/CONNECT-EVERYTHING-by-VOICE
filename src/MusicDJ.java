
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author KITT
 */
public class MusicDJ {

    String songName;
    String voiceInfo;
    Map<String, String> ジャンル = new HashMap<String, String>() {/*ノリの良い曲:1 失恋ソング:2 かっこいい曲:3 ラブソング:4 クリスマス:5
         */
        {
            put("ノリ", "1");
            put("テンポの", "1");
            put("楽しい", "1");
            put("失恋", "2");
            put("悲しい", "2");
            put("かっこいい", "3");
            put("ジェーポップ", "3");
            put("j-pop", "3");
            put("ラブソング", "4");
            put("クリスマス", "5");
            put("アニソン", "6");
            put("ボカロ", "7");
        }
    };
    Map<String, String> 曲名 = new HashMap<>();
    PlayAudio m = PlayAudio.getInstance();
    boolean playflg = false;

    public String getSongName() {
        if (songName.contains(".wav")) {
            songName = songName.substring(0, songName.length() - 4);
        }
        return songName;
    }

    public void MusicDJ() {
    }

    public void setVoiceInfo(String voiceInfo) {
        this.voiceInfo = voiceInfo;
    }

    public static List<File> findAllFile(String absolutePath) {

        List<File> files = new ArrayList<>();

        Stack<File> stack = new Stack<>();
        stack.add(new File(absolutePath));
        while (!stack.isEmpty()) {
            File item = stack.pop();
            if (item.isFile()) {
                files.add(item);
            }

            if (item.isDirectory()) {
                for (File child : item.listFiles()) {
                    stack.push(child);
                }
            }
        }
        return files;
    }

    public void DJ() {
        playflg = false;

        //ジャンルべつにrandomにかける場合
        if (!playflg) {
            for (String song : ジャンル.keySet()) {//曲種別
                if (voiceInfo.contains(song)) {
                    playflg = true;
                    songName = m.play("./WAVE/song/" + ジャンル.get(song), false, true);
                    break;
                }
            }
        }
        //該当する完全一致検索
        if (!playflg) {
            List<File> file = findAllFile("./WAVE/song/");
            for (int i = 0; i < file.size(); i++) {//ファイル名を曲名にセット
                曲名.put(file.get(i).getPath(), file.get(i).getName());  //path,fileName
            }
            for (Map.Entry<String, String> e : 曲名.entrySet()) {
                String 曲 = e.getValue();
                int len = 曲.length();
                曲 = 曲.substring(0, len - 4);//.wav を引く
                if (voiceInfo.contains(曲)) {
                    m.play(e.getKey(), false, false);
                    songName = e.getValue();
                    playflg = true;
                }
            }
        }
        if (voiceInfo.contains("おすすめ") && !playflg) {
            playflg = true;
            PRINT.getInstance().printf("この曲は､はじめてヒップホップをしり､良いと思えた曲です｡");
            songName = m.play("./WAVE/song/3/基準.wav", false, false);
        } else if (voiceInfo.contains("曲") && !playflg) {
            playflg = true;
            int filepath = new java.util.Random().nextInt(+7) + 1; //ファイル数 7 
            songName = m.play("./WAVE/song/" + Integer.toString(filepath), true);
        } else if (voiceInfo.contains("歌っ") && !playflg) {
            playflg = true;
            PRINT.getInstance().printf("私は歌えないので曲をシャッフルしてかけますね｡");
            int filepath = new java.util.Random().nextInt(+7) + 1; //ファイル数 7 

            songName = m.play("./WAVE/song/" + Integer.toString(filepath), true);
        } //該当なしの場合randomに選ぶ
        else if (!playflg) {
            playflg = true;
            PRINT.getInstance().printf("データベースにありませんでした｡わたしのおすすめの曲を聞いてください｡");
            int filepath = new java.util.Random().nextInt(+7) + 1; //ファイル数 7 

            songName = m.play("./WAVE/song/" + "3", true);

        }
        System.out.println("　　　　　　　　　※音楽を停止するには↖の■をクリックしてください");
    }

}
