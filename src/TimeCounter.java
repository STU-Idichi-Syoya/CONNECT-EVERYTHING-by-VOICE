/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KITT
 */
public class TimeCounter {
    long times;
   
    long 基準;
    
    public boolean isTime(){
        long now=System.currentTimeMillis();
        return (now-times)>=基準;
        
    }
    public void setTimes() {
       times=System.currentTimeMillis();
    }
    public void ResetTimes(){
       times=System.currentTimeMillis();
    }
    
}
