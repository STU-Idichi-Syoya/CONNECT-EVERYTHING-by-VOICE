/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KITT
 */
import java.util.ArrayList;
import java.util.Random;
import jtalk.JTalkJna;
public class PRINT {

    final private static PRINT print = new PRINT();
   
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    private PRINT() {
    }

    public static PRINT getInstance() {
        return print;
    }

    public void printf(String msg) {
        if(msg==null)
            msg="入力されていません";
            if (OS_NAME.startsWith("windows")) {
           try {
			
			JTalkJna tts = new JTalkJna();
			tts.setVoice("mei_happy");
                        tts.setA(0.55);
                        tts.setR(0.85);
                        tts.setJf(1.0);
//			System.out.println("current voice: " + tts.getVoice().name);
			System.out.println("マカシテちゃん(AI)の発言 =>"+msg);
//			System.out.println("s  = " + tts.getS());
//			System.out.println("p  = " + tts.getP());
//			System.out.println("a  = " + tts.getA());
//			System.out.println("b  = " + tts.getB());
//			System.out.println("r  = " + tts.getR());
//			System.out.println("fm = " + tts.getFm());
//			System.out.println("u  = " + tts.getU());
//			System.out.println("jm = " + tts.getJm());
//			System.out.println("jf = " + tts.getJf());
//			System.out.println("g  = " + tts.getG());
			tts.speakAsync(msg);
                        
			tts.waitUntilDone();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			PlayAudio.getInstance().play("./WAVE/エラー.wav", false);
		}
           

        }/* else {
            Runtime run = Runtime.getRuntime();
            try {
                Process pro = run.exec("echo \"" + msg + "\"|/home/pi/Desktop/jtalk.sh");

                pro.waitFor();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(PRINT.class.getName()).log(Level.SEVERE, null, ex);
                PlayAudio.getInstance().loop("./WAVE/エラー.wav");
            }

        }*/
    }

    public void printf(ArrayList<String> msgcomp) {
        int len = msgcomp.size();
        int decide = new Random().nextInt(len);
        printf((String) msgcomp.get(decide));
    }

}


