/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hotel_reservation_system;
import java.util.Scanner;
/**
 *
 * @author Thinkpad
 */
public class Hotel_Reservation_System {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean check = false;
        int choice;
        System.out.println("************** HOTEL RESERVATION SYSTEM **************");
        do {
            try {
                System.out.println("Choose:");
                System.out.println("1. Admin.");
                System.out.println("2. Customer.");
                System.out.println("3. Exit.");
                Scanner input = new Scanner(System.in);
                choice = input.nextInt();
                switch (choice) {
                    case 1: {
                        String username = "Admin";
                        String password = "admin";
                        String uname;
                        String pword;
                        boolean adminChk = false;
                        do {
                            System.out.print("Enter Username: ");
                            uname = input.next();
                            System.out.print("Enter Password: ");
                            pword = input.next();
                            if (username.equals(uname) && password.equals(pword)) {
                                Admin.menu();
                                adminChk = false;
                            } else {
                                System.out.println("Incorrect Username or Password.");
                                adminChk = true;
                            }
                        } while (adminChk);
                        break;
                    }
                    case 2: {
                        customers.menu();
                        break;
                    }
                    case 3: {
                        check = true;
                        System.out.println("Exiting system...");
                        break;
                    }
                    default: {
                        System.out.println("Wrong Input! Please enter 1, 2, or 3.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again.");
            }
        } while (!check);
    }
}
