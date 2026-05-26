package day6;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Main {
    public static void main(String[] args){
        DBConnect db = new DBConnect();
        Connection connect =  db.connect();

        Boolean running = true;

        Scanner sc = new Scanner(System.in);

        while (running){
            System.out.println("=== MENU ===");
            System.out.println("[A]dd");
            System.out.println("[V]iew");
            System.out.println("[U]pdate Password");
            System.out.println("[D]elete");
            System.out.println("[Q]uit\n");
            System.out.print("Enter Choice: ");
            String choice = sc.nextLine().toUpperCase();

            switch (choice){
                case "A":
                    AddStudent(connect, sc);
                    break;
                case "V":
                    ViewStudent(connect, sc);
                    break;
                case "U":
                    UpdatePassword(connect, sc);
                    break;
                case "D":
                    DeleteStudent(connect, sc);
                    break;
                case "Q":
                    running = false;
                    break;
                default:
                    System.out.println("\nError input...\n");

            }
        }

       sc.close();

    }

    public static void AddStudent(Connection conn, Scanner sc){
        try{
            System.out.print("\nEnter Email: ");
            String email = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();
            System.out.print("Enter First name: ");
            String firstName = sc.nextLine();
            System.out.print("Enter Last name: ");
            String lastName = sc.nextLine();

            String sql = "INSERT INTO day6.student " + "(email, password, firstName, lastName, dateadded, dateupdated) " + "VALUES(?, ?, ?, ?, NOW(), NOW())";
            
            PreparedStatement pStatement = conn.prepareStatement(sql);

            pStatement.setString(1, email);
            pStatement.setString(2, password);
            pStatement.setString(3, firstName);
            pStatement.setString(4, lastName);

            pStatement.executeUpdate();

            System.out.println("\nStudent added successfully!");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void UpdatePassword(Connection conn, Scanner sc){

        try{
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Password: ");
            String newPassword = sc.nextLine();

            String sql = "UPDATE day6.student " + "SET password = ?, dateupdated = NOW() " + "WHERE studentid = ?";

            PreparedStatement pStatement = conn.prepareStatement(sql);

            pStatement.setString(1, newPassword);
            pStatement.setInt(2, id);

            int rows = pStatement.executeUpdate();

            if (rows > 0){
                System.out.println("\nPassword updated successfully!");
            }
            else{
                System.out.println("\nStudent not found.");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void DeleteStudent(Connection conn, Scanner sc){
        try{
            System.out.print("Enter Student ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM day6.student WHERE studentid = ?";

            PreparedStatement pStatement = conn.prepareStatement(sql);

            pStatement.setInt(1, id);

            int rows = pStatement.executeUpdate();

            if (rows > 0){
                System.out.println("\nStudent deleted successfully!\n");
            }else{
                System.out.println("\nStudent not found.\n");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void ViewStudent(Connection conn, Scanner sc){
        try{
            String sql = "SELECT * FROM day6.student";

            PreparedStatement pStatement = conn.prepareStatement(sql);

            ResultSet rs = pStatement.executeQuery();

            while (rs.next()){
                System.out.println("\n===================================================");
                System.out.println("Student ID: " + rs.getInt("studentid")); 
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Password: " + rs.getString("password"));
                System.out.println("Full name: " + rs.getString("firstName") + " " + rs.getString("lastName"));
                System.out.println("Date added: " + rs.getString("dateadded"));
                System.out.println("Date updated: " + rs.getString("dateupdated"));
                System.out.println("===================================================\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
      

    }
}
