import org.h2.tools.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

class Initialization {


    static void dbInitialization() {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        // JDBC driver name and database URL
        String JDBC_DRIVER = "org.h2.Driver"; //org.h2.Driver
        String DB_URL = "jdbc:h2:./src/main/java/db/test";

        //  Database credentials
        String USER = "sa";
        String PASS = "";

        // Try to set up the JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);

            // Try to open a connection
            try {
                System.out.println("Connecting to selected database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Successfully connected!");

                // Web server
                System.out.println("Starting local web server...");
                Server webServer = Server.createWebServer("-webAllowOthers", "-webPort", "8082").start();
                System.out.println("Local web server successfully started at port 8082.");

                System.out.println("Executing conditional create table statement...");
                stmt = conn.createStatement();
                // Try to execute a query
                try {
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS States (ID int NOT NULL, Name varchar(32), Abbreviation varchar(2), PRIMARY KEY (ID));");
                    System.out.println("Statement successfully executed!");

                    System.out.println("Executing check null table statement...");
                    stmt = conn.createStatement();
                    // Try to execute a query
                    try {
                        rs = stmt.executeQuery("SELECT NULL FROM States LIMIT 1;");
                        System.out.println("Statement successfully executed!");
                        if (!rs.next()) {

                            // Try to read from local
                            try (BufferedReader br = new BufferedReader(new FileReader(Initialization.class.getResource("db/states.csv").getFile()))) {
                                String line = "";
                                while ((line = br.readLine()) != null) {
                                    // Comma separator
                                    String[] states = line.split(",");

                                    System.out.println("Executing insert statements...");
                                    prepStmt = conn.prepareStatement("INSERT INTO States (ID, Name, Abbreviation) VALUES (?, ?, ?);");
                                    // Try to execute a query
                                    try {
                                        prepStmt.setInt(1, Integer.parseInt(states[0]));
                                        prepStmt.setString(2, states[1]);
                                        prepStmt.setString(3, states[2]);
                                        prepStmt.executeUpdate();
                                        System.out.println("Statement successfully executed!");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } finally {
                                        if (prepStmt != null) {
                                            prepStmt.close();
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (stmt != null) {
                            stmt.close();
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (stmt != null) {
                    conn.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
