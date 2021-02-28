/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KITT
 */
import com.pi4j.io.gpio.*;

public class GPIO {

    private boolean now_on_state = false;
    private static final GPIO instance = new GPIO();
    
    private GpioController gpio;

    private GpioPinDigitalOutput pin_power;
//singleton

    private GPIO() {
        if (gpio == null) {
            gpio = GpioFactory.getInstance();
            pin_power = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, PinState.LOW);
        }
    }

    public static GPIO getInstance() {
        return instance;
    }

//pinの設定,取得
    public boolean getPin_power() {
        now_on_state = !pin_power.isLow(); //trueならばHIGI faiseならばLOW
        return now_on_state;
    }

    public void onPin_power(boolean set, long time) {
        if (set) {

            long end = System.currentTimeMillis() + 6000;//初期化のため
            long start = System.currentTimeMillis();
            pin_power.setState(PinState.HIGH);
            for (; (end - start) <= time;) {
                now_on_state = true;
                end = System.currentTimeMillis();
            }
            pin_power.setState(PinState.LOW);
        }
    }

}
