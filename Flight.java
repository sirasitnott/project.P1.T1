import java.io.*;
import java.util.Scanner;   
import java.time.LocalDate;

public class Flight {

    public static String filename = "";
    public static String date = "";

    public static void Flight()throws Exception {

        Scanner sc = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        String from = "";
        String to = "";
        
        //----------
        System.out.println("----------The airport where the service is available----------");
        System.out.println("Donmueang International Airport (DMK)");
        System.out.println("Suvarnabhumi Airport (BKK)");
        System.out.println("Phuket International Airport (HKT)");
        System.out.println("Chiang Mai International Airport (CNX)");

        System.out.println("----------Enter airport code----------");
        System.out.print("From (e.g.,CNX) : ");
        from = sc. nextLine();
        System.out.print("To (e.g.,HKT) : ");
        to = sc. nextLine();

        from = from.toUpperCase();
        to = to.toUpperCase();

        
        int ty = today.getYear() , tm = today.getMonthValue(), td = today.getDayOfMonth();

        System.out.print("Date of travel (DD-MM-YYYY) : ");
        date = sc.nextLine();
        
        int y = Integer.parseInt(date.substring(6, 10));
        int d = Integer.parseInt(date.substring(0, 2));
        int m = Integer.parseInt(date.substring(3, 5));
      
        while(true) {
            if (y > ty || (y == ty && m > tm) || (y == ty && m == tm && d >= td)) {
                break;
            } else {
                System.out.println("Unable to enter past date please enter date again");
            }
            System.out.print("Date of travel (DD-MM-YYYY) : ");
            date = sc.nextLine();

            y = Integer.parseInt(date.substring(6, 10));
            d = Integer.parseInt(date.substring(0, 2));
            m = Integer.parseInt(date.substring(3, 5));

        }
        
        char c1 = from.charAt(0);
        char c2 = to.charAt(0);
        String c3 = date.substring(0, 2); 
        String c4 = date.substring(3, 5);
        String c5 = date.substring(8, 10);

        String morningFlight = c1 + "" + c2+"M"+c3+c4+"09"+c5;
        String eveningFlight = c1 + "" + c2+"E"+c3+c4+"15"+c5;

        while (true) {
            System.out.println("Select Available flights on " + date + ":");
            System.out.println("1.Morning flight: " + morningFlight);
            System.out.println("2.Evening flight: " + eveningFlight);
            System.out.print("Enter your choice (1 or 2): ");
            int flightChoice = sc.nextInt();
 
            if (flightChoice == 1) {
                filename = morningFlight + ".txt";
                break ;
            } else if (flightChoice == 2) {
                filename = eveningFlight + ".txt";
                break ;
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }

        java.io.File file = new java.io.File(filename);
        
            if (file.exists()) {

            } else {
                file.createNewFile();
                try {
                Thread.sleep(1500);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
                Seat.CreateSeat();
            }
        
    }
     
    

    public static void main(String[]aStrings){
        try {
            Flight.Flight();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


