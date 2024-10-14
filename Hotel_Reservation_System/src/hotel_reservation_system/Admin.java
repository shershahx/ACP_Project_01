/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_reservation_system;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author Thinkpad
 */
public class Admin {
    public static void menu() {
        boolean check = false;
        do {
            try {
                int select;
                System.out.println("************** Admin Menu ***************");
                System.out.println("1. Add Room.");
                System.out.println("2. Delete Room.");
                System.out.println("3. Update Room.");
                System.out.println("4. View Reservation.");
                System.out.println("5. View Rooms.");
                System.out.println("6. Logout.");
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                switch (select) {
                    case 1: {
                        addRoom();
                        break;
                    }
                    case 2: {
                        deleteRoom();
                        break;
                    }
                    case 3: {
                        editRoom();
                        break;
                    }
                    case 4: {
                        viewReservation();
                        break;
                    }
                    case 5: {
                        viewRooms();
                        break;
                    }
                    case 6: {
                        check = true;
                        System.out.println("Logging out...");
                        break;
                    }
                    default: {
                        System.out.println("Wrong Input! Please enter between 1 & 6.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again.");
            }
        } while (!check);
    }

    public static void addRoom() {
        File file = new File("rooms.txt");
        try (BufferedReader roomReader = new BufferedReader(new FileReader("rooms.txt"));
             BufferedWriter roomWriter = new BufferedWriter(new FileWriter("rooms.txt", true))) {

            Scanner input = new Scanner(System.in);
            System.out.print("Enter Room Number: ");
            int roomNo = input.nextInt();
            String roomDetails;
            boolean roomExists = false;

            while ((roomDetails = roomReader.readLine()) != null) {
                String[] roomData = roomDetails.split(",");
                if (Integer.parseInt(roomData[0]) == roomNo) {
                    roomExists = true;
                    break;
                }
            }

            if (roomExists) {
                System.out.println("Room number already exists. Please enter a unique room number.");
                return;
            }

            System.out.print("Enter Bed Type: ");
            String bedType = input.next();
            System.out.print("Enter Room Price Per Night: ");
            double price = input.nextDouble();
            System.out.print("Does Room have AC (yes/no): ");
            String ac = input.next();

            roomWriter.write(roomNo + "," + bedType + "," + price + "," + ac);
            roomWriter.newLine();
            System.out.println("Room added successfully!");

        } catch (IOException e) {
            System.out.println("Error while adding room: " + e.getMessage());
        }
    }

    public static void deleteRoom() {
        try {
            File file = new File("rooms.txt");
            File tempFile = new File("temp_rooms.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            Scanner input = new Scanner(System.in);
            System.out.print("Enter Room Number to delete: ");
            int roomNo = input.nextInt();

            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] roomData = currentLine.split(",");
                if (Integer.parseInt(roomData[0]) == roomNo) {
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
                System.out.println("Room deleted successfully!");
            } else {
                System.out.println("Room not found!");
            }
        } catch (IOException e) {
            System.out.println("Error while deleting room: " + e.getMessage());
        }
    }

    public static void editRoom() {
        try {
            File file = new File("rooms.txt");
            File tempFile = new File("temp_rooms.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            Scanner input = new Scanner(System.in);
            System.out.print("Enter Room Number to update: ");
            int roomNo = input.nextInt();

            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] roomData = currentLine.split(",");
                if (Integer.parseInt(roomData[0]) == roomNo) {
                    found = true;
                    System.out.print("Enter new Bed Type (Single/Double): ");
                    String newBedType = input.next();
                    System.out.print("Enter new Room Price Per Night: ");
                    double newPrice = input.nextDouble();
                    System.out.print("Is the Room with AC (yes/no): ");
                    String newAC = input.next();
                    writer.write(roomNo + "," + newBedType + "," + newPrice + "," + newAC);
                    writer.newLine();
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
                System.out.println("Room updated successfully!");
            } else {
                System.out.println("Room not found!");
            }
        } catch (IOException e) {
            System.out.println("Error while updating room: " + e.getMessage());
        }
    }

    public static void viewReservation() {
        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"))) {
            String currentLine;
            System.out.println("************** List of Reservations **************");
            System.out.println("Username\tPhone Number\tEmail\t\tRoom Number");

            while ((currentLine = reader.readLine()) != null) {
                String[] reservationDetails = currentLine.split(",");
                System.out.println(reservationDetails[0] + "\t" + reservationDetails[1] + "\t" + reservationDetails[2] + "\t" + reservationDetails[3]);
            }
        } catch (IOException e) {
            System.out.println("Error while viewing reservations: " + e.getMessage());
        }
    }

    public static void viewRooms() {
        try (BufferedReader reader = new BufferedReader(new FileReader("rooms.txt"))) {
            String currentLine;
            System.out.println("Room No\tBed Type\tPrice\tAC");

            while ((currentLine = reader.readLine()) != null) {
                String[] roomDetails = currentLine.split(",");
                System.out.println(roomDetails[0] + "\t" + roomDetails[1] + "\t\t" + roomDetails[2] + "\t" + roomDetails[3]);
            }
        } catch (IOException e) {
            System.out.println("Error while viewing rooms: " + e.getMessage());
        }
    }
}
