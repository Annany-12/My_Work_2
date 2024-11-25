// MySQL:
// CREATE DATABASE database_name;
// GRANT ALL PRIVILEGES ON database_name.* TO 'root'@'localhost';
// FLUSH PRIVILEGES;

// --------------

// Java:
// private static final String URL = "jdbc:mysql://localhost:3306/database_name";

// --------------

// CMD:
// javac -cp "C:\Users\annan\Desktop\jdbc_ass\mysql-connector-j-8.3.0\mysql-connector-java-8.3.0.jar" assignment_name.java
// java -cp ".;C:\Users\annan\Desktop\jdbc_ass\mysql-connector-j-8.3.0\mysql-connector-j-8.3.0.jar" assignment_name

import java.sql.*;
import java.util.Scanner;

public class a {

    // Database credentials (replace with your own)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql_password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connected to the database!");

            int choice;
            do {
                System.out.println("\nDatabase Operations:");
                System.out.println("1. Insert");
                System.out.println("2. Display");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> insertData(conn, scanner);
                    case 2 -> displayData(conn);
                    case 3 -> updateData(conn, scanner);
                    case 4 -> deleteData(conn, scanner);
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            } while (choice != 5);

        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    private static void insertData(Connection conn, Scanner scanner) {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String sql = "INSERT INTO your_table_name (id, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Insert operation failed!");
        }
    }

    private static void displayData(Connection conn) {
        String sql = "SELECT * FROM your_table_name";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("ID\tName");
            System.out.println("---------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Display operation failed!");
        }
    }

    private static void updateData(Connection conn, Scanner scanner) {
        System.out.print("Enter ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Name: ");
        String newName = scanner.nextLine();

        String sql = "UPDATE your_table_name SET name = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Data updated successfully!");
        } catch (SQLException e) {
            System.out.println("Update operation failed!");
        }
    }

    private static void deleteData(Connection conn, Scanner scanner) {
        System.out.print("Enter ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM your_table_name WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Data deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Delete operation failed!");
        }
    }
}
