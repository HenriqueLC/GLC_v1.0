package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBUtil {

    public static Connection getConnection() throws SQLException {
        // JDBC driver name and database URL
        String DB_URL = "jdbc:h2:./src/main/java/db/test";

        //  Database credentials
        String USER = "sa";
        String PASS = "";

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public static void Init() {

        System.out.println("Connecting to selected database...");
        try (Connection connection = getConnection()) {
            System.out.println("Successfully connected!");

            System.out.println("Conditional creating States table...");
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS States (ID int NOT NULL, Name varchar(32), Abbreviation varchar(2), PRIMARY KEY(ID));");
                System.out.println("Successfully created!");

                System.out.println("Checking States table...");
                ResultSet resultSet = statement.executeQuery("SELECT * FROM States LIMIT 1;");
                System.out.println("OK!");

                if (!resultSet.next()) {

                    try (BufferedReader br = new BufferedReader(new FileReader(DBUtil.class.getClassLoader().getResource("dump/states.csv").getFile()))) {

                        String line = "";
                        while ((line = br.readLine()) != null) {
                            // Comma separator
                            String[] states = line.split(",");

                            System.out.println("Populating States table...");
                            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO States(ID, Name, Abbreviation) VALUES (?, ?, ?);")) {
                                preparedStatement.setInt(1, Integer.parseInt(states[0]));
                                preparedStatement.setString(2, states[1]);
                                preparedStatement.setString(3, states[2]);
                                preparedStatement.executeUpdate();
                                System.out.println("Done!");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Conditional creating Cities table...");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Cities (ID int NOT NULL, Name varchar(64), FK_States_Cities int NOT NULL, PRIMARY KEY(ID), FOREIGN KEY(FK_States_Cities) REFERENCES States(ID));");
                System.out.println("Successfully created!");

                System.out.println("Checking Cities table...");
                resultSet = statement.executeQuery("SELECT * FROM Cities LIMIT 1;");
                System.out.println("OK!");

                if (!resultSet.next()) {

                    try (BufferedReader br = new BufferedReader(new FileReader(DBUtil.class.getClassLoader().getResource("dump/cities.csv").getFile()))) {

                        String line = "";
                        while ((line = br.readLine()) != null) {
                            // Comma separator
                            String[] states = line.split(",");

                            System.out.println("Inserting record...");
                            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Cities(ID, Name, FK_States_Cities) VALUES (?, ?, ?);")) {
                                preparedStatement.setInt(1, Integer.parseInt(states[0]));
                                preparedStatement.setString(2, states[1]);
                                preparedStatement.setInt(3, Integer.parseInt(states[2]));
                                preparedStatement.executeUpdate();
                                System.out.println("Done!");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
