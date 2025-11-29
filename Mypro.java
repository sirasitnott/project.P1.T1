import java.io.*;
import java.util.Scanner;


public class Mypro {
    public static void main(String[] args) {

        System.out.println("WELCOME TO AIR LINE RESERVATION SYSTEM");
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (i < 4) {
            System.out.println("Please select an option:");
            System.out.println("1. Book a flight");
            System.out.println("2. Cancel a flight");
            System.out.println("3. View flight details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    try {
                        Flight.Flight();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Menu.Add();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("----------Thank you for using the service----------");

                    break;
                case 2:
                
                    try {
                        Menu.Cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:

                    Menu.Viewing();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    i = 4; 
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        }

        scanner.close();
    }
}
