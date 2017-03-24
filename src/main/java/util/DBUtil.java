package util;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.List;

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

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(DBUtil.class.getClassLoader().getResourceAsStream("dump/states.csv")))) {

                        System.out.println("Populating States table...");
                        String line;
                        while ((line = br.readLine()) != null) {
                            // Comma separator
                            String[] states = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO States(ID, Name, Abbreviation) VALUES (?, ?, ?);")) {
                                preparedStatement.setInt(1, Integer.parseInt(states[0]));
                                preparedStatement.setString(2, states[1]);
                                preparedStatement.setString(3, states[2]);
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Done!");
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

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(DBUtil.class.getClassLoader().getResourceAsStream("dump/cities.csv")))) {

                        System.out.println("Populating Cities table...");
                        String line;
                        while ((line = br.readLine()) != null) {
                            // Comma separator
                            String[] cities = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Cities(ID, Name, FK_States_Cities) VALUES (?, ?, ?);")) {
                                preparedStatement.setInt(1, Integer.parseInt(cities[0]));
                                preparedStatement.setString(2, cities[1]);
                                preparedStatement.setInt(3, Integer.parseInt(cities[2]));
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Done!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Conditional creating ZipCodes table...");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS ZipCodes (ZipCode int NOT NULL, Road varchar(256), District varchar(128), FK_Cities_ZipCodes int NOT NULL, FK_States_ZipCodes int NOT NULL, PRIMARY KEY(ZipCode), FOREIGN KEY(FK_Cities_ZipCodes) REFERENCES Cities(ID), FOREIGN KEY(FK_States_ZipCodes) REFERENCES States(ID));");
                System.out.println("Successfully created!");

                System.out.println("Checking ZipCodes table...");
                resultSet = statement.executeQuery("SELECT * FROM ZipCodes LIMIT 1;");
                System.out.println("OK!");

                if (!resultSet.next()) {

                    try {
                        List<URL> resources = Util.getResources("dump/zipcodes");
                        for (int i = 0; i < resources.size(); i++) {

                            try (BufferedReader br = new BufferedReader(new InputStreamReader(resources.get(i).openStream()))) {

                                System.out.println("Populating ZipCodes table... (" + 100 * (i + 1) / resources.size() + "%)");
                                String line;
                                while ((line = br.readLine()) != null) {
                                    // Comma separator
                                    String[] zipCodes = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                                    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ZipCodes(ZipCode, Road, District, FK_Cities_ZipCodes, FK_States_ZipCodes) VALUES (?, ?, ?, ?, ?);")) {
                                        preparedStatement.setInt(1, Integer.parseInt(zipCodes[0]));
                                        preparedStatement.setString(2, zipCodes[1]);
                                        preparedStatement.setString(3, zipCodes[2]);
                                        preparedStatement.setInt(4, Integer.parseInt(zipCodes[3]));
                                        preparedStatement.setInt(5, Integer.parseInt(zipCodes[4]));
                                        preparedStatement.executeUpdate();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Done!");
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