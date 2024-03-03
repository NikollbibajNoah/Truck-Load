package src;

import src.DB.DB;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.imageio.ImageIO;

public class Main {

    public static BufferedImage truckImg;
    static Connection con;

    public static DB database;

    public static void main(String[] args) throws SQLException, IOException {

        //Load Truck Img
        truckImg = ImageIO.read(new File("./assets/truck.png"));
       
        ///Start App
        new App().createApp();

        database = new DB();
        database.connect();
    }
}
