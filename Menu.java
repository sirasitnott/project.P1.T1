import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;


public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static String fname = "";
    public static void Cancel() throws IOException { 

        Scanner sc = new Scanner(System.in); 
        String flightName, customerName;

        System.out.println("---  CANCEL RESERVATION ---");
        System.out.print("Enter Flight Name (e.g., BCE011525): ");
        flightName = sc.nextLine().toUpperCase();
        System.out.print("Enter Customer First Name to search: ");
        customerName = sc.nextLine().toUpperCase();

        // 1. ค้นหาการจองที่ตรงกันใน Customer.txt
        List<String> bookingsToCancel = new ArrayList<>();
        
        
        File customerFile = new File("Customer.txt");
        if (!customerFile.exists()) {
            System.out.println("\nCustomer.txt file not found.");
            
            return; 
        }

        
        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // แยกข้อมูลในแต่ละบรรทัด: ID FNAME LNAME TEL SEATNAME
                String[] parts = line.split(" ");
                // parts[0] = FlightID, parts[1] = FNAME
                if (parts.length >= 5 && parts[0].equals(flightName) && parts[1].equals(customerName)) {
                    bookingsToCancel.add(line);
                }
            }
        } 

        // --- ตรวจสอบผลการค้นหา ---
        if (bookingsToCancel.isEmpty()) {
            System.out.println("\n No reservation found for flight " + flightName + " under name " + customerName + ".");
            return;
        }

        System.out.println("\nFound " + bookingsToCancel.size() + " reservation(s) to cancel:");
        List<String> seatsToUpdate = new ArrayList<>(); 
        for (int i = 0; i < bookingsToCancel.size(); i++) {
            String[] parts = bookingsToCancel.get(i).split(" ");
            String seatName = parts[4];
            seatsToUpdate.add(seatName); 
            
            System.out.println((i + 1) + ". Seat: " + seatName + 
                            " | Customer: " + parts[1] + " " + parts[2] + 
                            " | Tel: " + parts[3]);
        }

        System.out.print("\nDo you confirm to cancel ALL these reservations? (Y/N): ");
        String confirmation = sc.nextLine().toUpperCase();

        if (!confirmation.equals("Y")) {
            System.out.println("Operation cancelled by user.");
            return;
        }
        
        // --- 2. ดำเนินการลบและอัปเดตไฟล์ ---

        // A. ลบการจองออกจาก Customer.txt
        File tempCustomerFile = new File("Customer_temp.txt"); 

        // แก้ไข: ต้องครอบด้วย try-catch หรือประกาศ throws IOException
        try (BufferedReader br = new BufferedReader(new FileReader(customerFile));
            PrintWriter pw = new PrintWriter(new FileWriter(tempCustomerFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                // หากแถวปัจจุบัน **ไม่** อยู่ในรายการที่ต้องการยกเลิก ให้เขียนลงในไฟล์ชั่วคราว
                if (!bookingsToCancel.contains(line)) {
                    pw.println(line);
                }
            }
        } 

        // แทนที่ไฟล์ Customer.txt เดิมด้วยไฟล์ชั่วคราว
        if (!customerFile.delete()) {
            System.err.println("Warning: Could not delete original file Customer.txt.");
        }
        if (!tempCustomerFile.renameTo(customerFile)) {
            System.err.println("Warning: Failed to rename temp file to Customer.txt.");
        }


        // B. อัปเดตสถานะที่นั่งในไฟล์เที่ยวบิน
        String flightFileName = flightName + ".txt";
        File flightFile = new File(flightFileName);
        File tempFlightFile = new File(flightFileName + "_temp");

        if (!flightFile.exists()) {
            System.out.println("Warning: Flight file " + flightFileName + " not found. Could not update seat status.");
        } else {
            // แก้ไข: ต้องครอบด้วย try-catch หรือประกาศ throws IOException
            try (BufferedReader br = new BufferedReader(new FileReader(flightFile));
                PrintWriter pw = new PrintWriter(new FileWriter(tempFlightFile))) {

                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" ");
                    String seatName = parts[0];

                    if (seatsToUpdate.contains(seatName)) {
                        // หากเป็นที่นั่งที่ถูกยกเลิก: เปลี่ยนเป็น "SEATNAME available"
                        String newLine = seatName + " available";
                        pw.println(newLine);
                    } else {
                        // ไม่เกี่ยวข้อง: เขียนบรรทัดเดิม
                        pw.println(line);
                    }
                }
            }
            

            if (!flightFile.delete()) {
                System.err.println("Warning: Could not delete original file " + flightFileName);
            }
            if (!tempFlightFile.renameTo(flightFile)) {
                System.err.println("Warning: Failed to rename temp file to " + flightFileName);
            }
    }

    System.out.println("\n Cancellation successful! " + bookingsToCancel.size() + " reservation(s) removed.");
    System.out.println("Seat status for " + String.join(", ", seatsToUpdate) + " has been changed to 'available'.");
    
    
    }
   //-----------------------------------------------------------------------------------------
    public static void Add()throws Exception{
        
        Scanner sc = SCANNER;
        String flightFileName = Flight.filename;
        String flightID;
        if (Flight.filename != null && !Flight.filename.isEmpty()) {
            flightFileName = Flight.filename;
            flightID = flightFileName.replaceAll("\\.txt$", "");
        } else {
            System.out.print("Enter Flight Name (e.g., BCE011525): ");
            flightID = sc.nextLine().toUpperCase();
            flightFileName = flightID + ".txt";
        }

        String sourceFile = flightFileName; 
        String destFile = "Temp.txt";
        Seat.UpdateSeat(sourceFile, destFile);
        String firstname = "";
        String lastname = "";
        String tel = "";
        int numberofseat = 0;

        System.out.print("First Name : ");
        firstname = sc.nextLine().toUpperCase();
        System.out.print("Last Name : ");
        lastname = sc.nextLine().toUpperCase();
        System.out.print("Telephone : ");
        tel = sc.nextLine();

        try {
            Seat.DisplaySeat();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("How many seats will you reserve : ");
        numberofseat = sc.nextInt();
        sc.nextLine();

        String[] seatname = new String[numberofseat];

        for (int i = 0; i < numberofseat; i++) {
            System.out.print("Select seat " + (i + 1) + " : ");
            seatname[i] = sc.nextLine().toUpperCase();
        }

        String customerID = flightID; //------------------------------------------use selected flight ID

            
            try (FileWriter writer = new FileWriter("Customer.txt", true)) {
                for (String seat : seatname) {
                    String line = String.format("%s %s %s %s %s\n",
                            customerID,
                            firstname,
                            lastname,
                            tel,
                            seat);

                    writer.write(line);
                }

                

            } catch (IOException e) {
                System.err.println(" Error writing Customer.txt: " + e.getMessage());
                e.printStackTrace();
            }

        //อัปเดตสถานะที่นั่งในไฟล์ flight (เช่น BCE011525.txt)
        UpdateSeatStatus(sourceFile, seatname, tel);
            
        
    }
//----------------------------------------------------------------------------------------------
    public static void UpdateSeatStatus(String filename, String[] seats, String tel) {
        try {
            File file = new File(filename);
            List<String> lines = new ArrayList<>();

            try (Scanner fr = new Scanner(file)) {
                while (fr.hasNextLine()) {
                    String line = fr.nextLine();
                    String[] parts = line.split(" ");

                    if (parts.length >= 1) {
                        // ตรวจแต่ละ seat ที่ผู้ใช้จอง
                        for (String seat : seats) {
                            if (parts[0].equalsIgnoreCase(seat)) {
                                line = parts[0] + " unavailable " + tel; // เปลี่ยนสถานะ
                                break;
                            }
                        }
                    }

                    lines.add(line);
                }
            }

            // เขียนกลับลงไฟล์ (overwrite)
            try (PrintWriter pw = new PrintWriter(file)) {
                for (String l : lines) {
                    pw.println(l);
                }
            }

           

        } catch (Exception e) {
            System.out.println("Error updating seat file: " + filename);
            e.printStackTrace();
        }
}
//---------------------------------------------------------------------------------------------   
    public static void Viewing(){

        Scanner sc = new Scanner(System.in);
        
        String tfrom = "";
        String date = "";
        String tto = "";
        String ttime = "";
        String tday = "",tmonth = "",tyear = "";
        System.out.print("Flight Name : ");
        fname = sc.nextLine().toUpperCase();
        
        char from = fname.charAt(0);
        char to = fname.charAt(1);
        char time = fname.charAt(2);
        int dday = Integer.parseInt(fname.substring(3, 5));
        int dmonth = Integer.parseInt(fname.substring(5, 7));
        int dyear = Integer.parseInt(fname.substring(9, 11));

        // System.out.println("Donmueang International Airport (DMK)");
        // System.out.println("Suvarnabhumi Airport (BKK)");
        // System.out.println("Phuket International Airport (HKT)");
        // System.out.println("Chiang Mai International Airport (CNX)");


        if (from  == 'B') {
            tfrom = "Suvarnabhumi Airport (BKK)";
        }else if (from  == 'D'){
            tfrom = "Donmueang International Airport (DMK)";
        }else if(from == 'H'){
            tfrom = "Phuket International Airport (HKT)";
        }else if(from == 'C'){
            tfrom = "Chiang Mai International Airport (CNX)";
        }
        
        if (to == 'D') {
            tto = "Donmueang International Airport (DMK)";
        }else if(to == 'B'){
            tto = "Suvarnabhumi Airport (BKK)";
        }else if(to == 'H'){
            tto = "Phuket International Airport (HKT)";
        }else if(to == 'C'){
            tto = "Chiang Mai International Airport (CNX)";
        }

        if (time == 'M') {
            ttime = "Morning (09:00)";
        }else if (time == 'E') {
            ttime = "Evening (15:00)";
        }


        for(int d = 0; d <= 32 ; d++){
            if (d == dday) {
                if (dday < 10) {
                    tday = "0" + dday; 
                } else {
                    tday = String.valueOf(dday); 
                }
                break;
            }
        }

        for(int m = 0;m <= 13;m++){
            if (m == dmonth) {
                if (dmonth < 10) {
                    tmonth = "0" + dmonth; 
                } else {
                    tmonth = String.valueOf(dmonth); 
                }
                break;
            }
        }

        for(int y = 0;y <= 99;y++){
            if (y == dyear) {
                tyear = "20" + dyear;
                break;
            }
        }

        date = tday + "-" + tmonth + "-" + tyear;
        
        try {
            Seat.DisplaySeatDetel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Your Flight From : "+tfrom+" to "+tto );
        System.out.println("Boarding Time : "+ttime);
        System.out.println("Date : " + date);

    }
//----------------------------------------------------------------------------------------------    
   
//------------------------------------------------------------------------------------------------------------------
    public static void main(String[]args){
        
        // Viewing();



        try {

            Add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



















