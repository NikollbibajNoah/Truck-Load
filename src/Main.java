package src;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

    public static BufferedImage truckImg;
    public static void main(String[] args) throws IOException {
        truckImg = ImageIO.read(new File("./assets/truck.png")); ///Load Image
        
        ///Start App
        new App().createApp();
    }
}
