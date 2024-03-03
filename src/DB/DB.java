package src.DB;

import java.sql.*;
import src.App;
import src.data.Pipe;

public class DB {

    //DB Connnection
    String url = "jdbc:mysql://localhost:3306/truckload"; ///Benutzerkontoname
    String user = "root"; //Username
    String password = ""; //Passwort

    Connection con;
    
    public void connect() {
        ///Check for class
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            con = DriverManager.getConnection(url, user, password);

            /*Delete Table Pipes
            Only execute to clear table
            */
            //Statement statement = con.createStatement();
            //statement.executeUpdate("drop table pipes");

            //Initialize DB
            initPipesDB();
        
            ///Load DB Pipes into Application
            loadPipesData();
            
        } catch (SQLException e) {e.printStackTrace();}

        ///After Statements
        System.out.println("Done");
    }

    /**
     * Creates new Table in DB and inserts empty values created from the Application
     * 
     **/
    void initPipesDB() {
        try {
            Statement statement = con.createStatement();

    
            //Table doesnt Exist
            if (!checkIfTableExists()) {

                ///Create Table Pipes
                statement.executeUpdate("create table pipes(id int auto_increment primary key, charge float, legierung char(50))");

                for (int i = 0; i < App.pipes.size(); i++) {
                    Pipe p = App.pipes.get(i);

                    ///Insert Pipes values
                    statement.executeUpdate("insert into pipes(charge, legierung) values(" + p.charge + ", '" + p.legierung + "');");
                }
            }
            
            statement.close();
        } catch (SQLException e) {e.printStackTrace();}
    }

    /**
     * Loads Data from DB and puts the value into the application
     * 
     * **/
    void loadPipesData() {
        try {
            Statement statement = con.createStatement();

            if (checkIfTableExists()) {
                ResultSet result = statement.executeQuery("select * from pipes");//Retrieves Data from DB

                while (result.next()) {
                    int id = result.getInt("id");
                    Float charge = result.getFloat("charge");
                    String legierung = result.getString("Legierung");
    
                    System.out.println(id + ": " + charge + ", " + legierung); ///Now insert into app.pipes
    
                    //Apply Values in Application
                    Pipe p = App.pipes.get(id - 1);
                    p.charge = charge;
                    p.legierung = legierung;
                }
            }
            

            statement.close();
        } catch (SQLException e) {e.printStackTrace();}
    }

    /**
     * Updates the current pipe with the given values
     * @param id Primary Key of Pipe in DB
     * @param charge Charge Value
     * @param legierung Material of Pipe
     */
    public void updatePipe(int id, Float charge, String legierung) {
        try {
            Statement statement = con.createStatement();

            if (checkIfTableExists()) {
                statement.executeUpdate("update pipes set charge = " + charge + ", legierung = '" + legierung + "' where id = " + id + ";");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if table pipes exists, for preventing errors
     * @return True if pipes exists and false if it doesnt
     */
    Boolean checkIfTableExists() {
        int state = -1;

        try {
            Statement statement = con.createStatement();

            ///Get State
            ResultSet r = statement.executeQuery("select (exists(select * from information_schema.tables where table_schema = 'truckload' and table_name = 'pipes'));");

            while (r.next()) {
                state = r.getInt("(exists(select * from information_schema.tables where table_schema = 'truckload' and table_name = 'pipes'))");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        //Exists
        if (state == 1) {
            return true;
        }

        return false;
    }
}
