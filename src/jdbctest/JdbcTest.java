/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denis
 */
public class JdbcTest {

    static String dbName = "jdbc";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=nbuser");
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void createDb(String dbName) {
        Connection conn = getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void deleteDb(String dbName) {
        Connection conn = getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DROP DATABASE IF EXISTS " + dbName);
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    static String createCarsTableQuery = "CREATE TABLE IF NOT EXISTS Cars ("
        + "Id integer NOT NULL AUTO_INCREMENT, "
        + "Model varchar(45) NOT NULL, "
        + "Price decimal(9,2) NOT NULL, "
        + "PRIMARY KEY (Id))";

    public static void createTable(String query) {
        Connection conn = getConnection();
        Statement stmt = null;
        try {
            conn.setCatalog(dbName);
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void populateCarsTable() {
        Set<Car> cars = InfoLoader.getCarsFromFile("resources/cars.txt");   // load cars from file
        String query = "INSERT INTO Cars (Model, Price) VALUES (?, ?)";
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            conn.setCatalog(dbName);
            ps = conn.prepareStatement(query);
            final int batchSize = 1000;
            int count = 0;
            for (Car car : cars) {
                ps.setString(1, car.getModel());
                ps.setFloat(2, car.getPrice());
                ps.addBatch();
                if (++count % batchSize == 0) {     // prevent out of memory
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void addCar(String model, float price) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO Cars (Model, Price) VALUES (?, ?)";
        try {
            conn.setCatalog(dbName);
            ps = conn.prepareStatement(query);
            ps.setString(1, model);
            ps.setFloat(2, price);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void updateCarById(int id, String model, float price) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE Cars SET Model = ?, price = ? WHERE Id = ?";
        try {
            conn.setCatalog(dbName);
            ps = conn.prepareStatement(query);
            ps.setString(1, model);
            ps.setFloat(2, price);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void deleteCarById(int id) {
        Connection conn = getConnection();
        Statement stmt = null;
        String query = "DELETE FROM Cars WHERE Id = " + id;
        try {
            conn.setCatalog(dbName);
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void setupDatabase() {
        deleteDb("jdbc");
        createDb("jdbc");
        createTable(createCarsTableQuery);
    }

    public static void setupAndPopulateDatabase() {
        setupDatabase();
        populateCarsTable();
    }

    public static void main(String[] args) {
//        addCar("Vaz 2110", 5);
//        createDb("jdbc");
//        createTable(createCarsTableQuery);
//        deleteCarById(5);
//        deleteDb("jdbc");
//        populateCarsTable();
        setupAndPopulateDatabase();
//        setupDatabase();
//        updateCarById(8, "Chevrolet Aveo", 1500);

    }
}
