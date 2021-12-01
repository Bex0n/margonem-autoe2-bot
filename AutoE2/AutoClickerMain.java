import java.awt.event.InputEvent;
import java.lang.Thread;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.JFrame;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystem;



public class AutoClickerMain {
    public static void main(final String[] args){
        AutoClicker action = new AutoClicker();
        Screenshot screenshot = new Screenshot();
        Scanner scanner = new Scanner(System.in);
        long startTime = System.nanoTime();
        action.setDelay(GenerateRandom.main(70,90));

        System.out.println("---AutoE2---");

        BufferedImage image = null;
        BufferedImage pattern = null;
        File CaptureFile = new File("capture.jpg");
        File PatternFile = null;
        Path path = Paths.get("C:/Users/jakub/Desktop/AutoE2/E2"); 

        //try to load monster file
        System.out.println("Enter the mob to camp.");
        while(true){
            String mob = scanner.nextLine();
            path = Paths.get(path + "/" + mob);
            if(Files.exists(path)){
                PatternFile = new File(path + "/pattern.jpg");
                break;
            }
            else{
                System.out.println("There is no data about this mob.");
                System.out.println("Enter another one.");
            }
        }

        //window size
        int widthbegin = 260;
        int widthend = 1669;
        int heightbegin = 190;
        int heightend = 980;
        //1409 * 790 = 1 113 110
        
        //character orientation
        Pair character = cameraActions.main();
        while (character.getFirst()==0){
            System.out.println("Character not found. Scanning again in 1 second");
            action.sleep(1000);
            screenshot.makeScreenshot("capture");
            character = cameraActions.main();
        }
        System.out.println("Character successfully scanned.");
        int xField = character.getFirst() - (character.getFirst()-widthbegin)/32*32;
        int yField = character.getSecond() - (character.getSecond()-heightbegin)/32*32;
        Pair firstfield = new Pair (xField,yField);
        System.out.println(character.getFirst());
        System.out.println(character.getSecond());
        System.out.println(firstfield.getFirst());
        System.out.println(firstfield.getSecond());

        //program start
        System.out.println("The program is starting in 3 seconds.");
        action.sleep(3000);

        //loop
        boolean running = true;
        while (running)
        {   
            startTime = System.nanoTime();
            screenshot.makeScreenshot("capture");

            try{
                image = ImageIO.read(CaptureFile);
                pattern = ImageIO.read(PatternFile);} 
            catch(Exception e){e.printStackTrace();}
            
            /*
            int command = scanner.nextInt();
            if(command == 1){
                character = cameraActions.main();
                if(character.getFirst()==0){
                    System.out.println("Character not found. Returning to old calibration");
                }
                else{
                xField = character.getFirst() - (character.getFirst()-widthbegin)/32*32;
                yField = character.getSecond() - (character.getSecond()-heightbegin)/32*32;
                firstfield = new Pair (xField,yField);
                }
            }
            */

            for (int row = firstfield.getSecond(); row < heightend; row+=32) {
                for (int col = firstfield.getFirst(); col < widthend; col+=32) {
                    Color c = new Color(image.getRGB(col, row));
                    Color p = new Color(pattern.getRGB(1341, 573));
                    if(pixelActions.pixelCompare(c, p) < 20){
                        if(pixelActions.pixelCheck(image, pattern, col, row,1341,573,4,4) < 380){ //1332 647 łowca // 1341 573 tollok
                            action.sleep(300);
                            action.enemyDetected(col, row);
                            //wait for the next monster
                            AutoClicker.sleep(150000);
                            break;
                        }
                    }
                }
            }
            action.sleep(1000);
        }
        System.out.println("AutoElita Complete");
    }
}