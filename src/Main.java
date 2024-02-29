package src;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.sql.*;

public class Main {

    public static BufferedImage truckImg;
    public static void main(String[] args) throws IOException, SQLException {
        truckImg = ImageIO.read(new File("./assets/truck.png")); ///Load Image
        
        ///Start App
        new App().createApp();


        connect();
    }

    static void connect() {
        //DB Connnection
        String url = "jdbc:mysql://localhost:3306/truckload";
        String user = "root";
        String password = "";
        String query = "select id, brand from autos";

        ///Check for class
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int id = result.getInt("id");
                String brand = result.getString("brand");

                System.out.println(id + ": " + brand);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        ///After Statements
        System.out.println("Done");
    }
}
