/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_reservation_system;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Thinkpad
 */
public class customers {
    public static void menu() {
        boolean check = false;
        do {
            try {
                int selects;
                System.out.println("************** Customer Menu **************");
                System.out.println("1. Registration.");
                System.out.println("2. Login.");
                System.out.println("3. Exit.");
                Scanner input = new Scanner(System.in);
                selects = input.nextInt();

                switch (selects) {
                    case 1: {
                        register();
                        break;
                    }
                    case 2: {
                        login();
                        break;
                    }
                    case 3: {
                        check = true;
                        System.out.println("Exiting customer menu...");
                        break;
                    }
                    default: {
                        System.out.println("Wrong Input! Please enter between 1 & 3.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again.");
            }
        } while (!check);
    }

    public static void register() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Username: ");
            String username = input.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = input.nextLine();
            System.out.print("Enter Email: ");
            String email = input.nextLine();
            System.out.print("Enter Password: ");
            String password = input.nextLine();

            writer.write(username + "," + phone + "," + email + "," + password);
            writer.newLine();
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.out.println("Error while registering: " + e.getMessage());
        }
    }

    public static void login() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Username: ");
            String username = input.nextLine();
            System.out.print("Enter Password: ");
            String password = input.nextLine();

            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] customerDetails = currentLine.split(",");
                if (customerDetails[0].equals(username) && customerDetails[3].equals(password)) {
                    System.out.println("Login successful!");
                    reservationMenu(username);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Invalid username or password!");
            }
        } catch (IOException e) {
            System.out.println("Error while logging in: " + e.getMessage());
        }
    }

    public static void reservationMenu(String username) {
        boolean check = false;
        do {
            try {
                int select;
                System.out.println("************** Reservation Menu **************");
                System.out.println("1. Make a Reservation.");
                System.out.println("2. Edit Reservation.");
                System.out.println("3. Cancel Reservation.");
                System.out.println("4. View Invoice.");
                System.out.println("5. Logout.");
                Scanner input = new Scanner(System.in);
                select = input.nextInt();

                switch (select) {
                    case 1: {
                        makeReservation(username);
                        break;
                    }
                    case 2: {
                        editReservation(username);
                        break;
                    }
                    case 3: {
                        cancelReservation(username);
                        break;
                    }
                    case 4: {
                        viewInvoice(username);
                        break;
                    }
                    case 5: {
                        check = true;
                        System.out.println("Logging out...");
                        break;
                    }
                    default: {
                        System.out.println("Wrong Input! Please enter between 1 & 5.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again.");
            }
        } while (!check);
    }

    public static void makeReservation(String username) {
    try (BufferedReader roomReader = new BufferedReader(new FileReader("rooms.txt"));
         BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {

        Scanner input = new Scanner(System.in);
        System.out.println("Available Rooms:");
        String roomDetails;
        ArrayList<Integer> availableRooms = new ArrayList<>();
        double roomPrice = 0;

        // Display available rooms
        while ((roomDetails = roomReader.readLine()) != null) {
            String[] roomData = roomDetails.split(",");
            int roomNo = Integer.parseInt(roomData[0]);
            roomPrice = Double.parseDouble(roomData[2]); // Store price temporarily
            System.out.println("Room No: " + roomNo + ", Bed Type: " + roomData[1] + ", Price: $" + roomPrice + ", AC: " + roomData[3]);
            availableRooms.add(roomNo);
        }

        System.out.print("Enter Room Number to reserve: ");
        int roomNo = input.nextInt();

        if (!availableRooms.contains(roomNo)) {
            System.out.println("Invalid Room Number! The room does not exist.");
            return;
        }

        BufferedReader reservationReader = new BufferedReader(new FileReader("reservations.txt"));
        boolean isRoomReserved = false;
        String reservationDetails;
        
        // Check if the room is already reserved
        while ((reservationDetails = reservationReader.readLine()) != null) {
            String[] reservationData = reservationDetails.split(",");
            if (reservationData[3].equals(String.valueOf(roomNo))) {
                isRoomReserved = true;
                break;
            }
        }
        reservationReader.close();

        if (isRoomReserved) {
            System.out.println("Room is already reserved. Please choose another room.");
        } else {
            System.out.print("Enter Phone Number: ");
            String phone = input.next();
            System.out.print("Enter Email: ");
            String email = input.next();

            // Display invoice
            System.out.println("\n--- Invoice ---");
            System.out.println("Customer Name: " + username);
            System.out.println("Phone Number: " + phone);
            System.out.println("Email: " + email);
            System.out.println("Room Number: " + roomNo);
            System.out.println("Amount: $" + roomPrice); // Display the room price
            System.out.println("---------------");

            System.out.print("Enter Account Number for Payment: ");
            String accountNumber = input.next(); // Collecting account number
            
            // Save reservation
            writer.write(username + "," + phone + "," + email + "," + roomNo);
            writer.newLine();
            System.out.println("Reservation successful! Payment processed.");
        }

    } catch (IOException e) {
        System.out.println("Error while making reservation: " + e.getMessage());
    }
}





    public static void editReservation(String username) {
        try {
            File file = new File("reservations.txt");
            File tempFile = new File("temp_reservations.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            Scanner input = new Scanner(System.in);
            System.out.print("Enter Room Number to edit: ");
            int roomNo = input.nextInt();
            boolean found = false;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] reservationData = currentLine.split(",");
                if (reservationData[0].equals(username) && reservationData[3].equals(String.valueOf(roomNo))) {
                    
                    System.out.print("Enter new Phone Number: ");
                    String newPhone = input.next();
                    System.out.print("Enter new Email: ");
                    String newEmail = input.next();

                    writer.write(newEmail + ","+ username + "," + newPhone);
                    writer.newLine();
                    found = true;
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }

            writer.close();
            reader.close();

            if (found) {
                file.delete();
                tempFile.renameTo(file);
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("Reservation not found!");
            }

        } catch (IOException e) {
            System.out.println("Error while editing reservation: " + e.getMessage());
        }
    }

    public static void cancelReservation(String username) {
        try {
            File file = new File("reservations.txt");
            File tempFile = new File("temp_reservations.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            Scanner input = new Scanner(System.in);
            System.out.print("Enter Room Number to cancel: ");
            int roomNo = input.nextInt();
            boolean found = false;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] reservationData = currentLine.split(",");
                if (reservationData[0].equals(username) && reservationData[3].equals(String.valueOf(roomNo))) {
                    found = true;
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }

            writer.close();
            reader.close();

            if (found) {
                file.delete();
                tempFile.renameTo(file);
                System.out.println("Reservation cancelled successfully!");
            } else {
                System.out.println("Reservation not found!");
            }

        } catch (IOException e) {
            System.out.println("Error while cancelling reservation: " + e.getMessage());
        }
    }

    public static void viewInvoice(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"));
             BufferedReader roomReader = new BufferedReader(new FileReader("rooms.txt"))) {

            String reservationDetails;
            String roomDetails;
            double totalAmount = 0;

            while ((reservationDetails = reader.readLine()) != null) {
                String[] reservationData = reservationDetails.split(",");
                if (reservationData[0].equals(username)) {
                    System.out.println("Customer Name: " + reservationData[0]);
                    System.out.println("Phone Number: " + reservationData[1]);
                    System.out.println("Email: " + reservationData[2]);
                    System.out.println("Room Number: " + reservationData[3]);

                    while ((roomDetails = roomReader.readLine()) != null) {
                        String[] roomData = roomDetails.split(",");
                        if (roomData[0].equals(reservationData[3])) {
                            System.out.println("Room Price Per Night: $" + roomData[2]);
                            totalAmount += Double.parseDouble(roomData[2]);
                            break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error while viewing invoice: " + e.getMessage());
        }
    }
}