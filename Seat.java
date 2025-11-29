import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Seat {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";


//---------------------------------------------------------------------------------------------------------------
   public static void CreateSeat() throws IOException {

        String filename = Flight.filename;
        
        try (PrintStream out = new PrintStream(new File(filename))) {
            
            for (int i = 1; i <= 20; i++) {
                out.println("B" + i + " available");
            }
            
            for (int i = 1; i <= 60; i++) {
                out.println("A" + i + " available");
            }
            
        } 
    }
//------------------------------------------------------------------------------------------------------------------
    public static void DisplaySeat() throws IOException {

        String sourceFile = Flight.filename; //-----------------------------------------แก้ Flight.filename();
        String destFile = "Temp.txt";       // กำหนดชื่อไฟล์ปลายทาง
        UpdateSeat(sourceFile, destFile);
        System.out.println("----------CHOOSE A SEAT----------");
        List<String[]> bSeatsData = new ArrayList<>(); 
        List<String[]> aSeatsData = new ArrayList<>(); 

        // String filename = Flight.filename;
        String filename = Flight.filename; //------------------------------แก้ Flight.filename;
        
        try (Scanner fiScanner = new Scanner(new File(filename))) { 
            while (fiScanner.hasNextLine()) {
                String line = fiScanner.nextLine().trim(); 
                String[] array_txt = line.split(" ");
                
            
                if (array_txt.length < 2) {
                    continue; 
                }

                String seatNumber = array_txt[0];
                
                if (seatNumber.charAt(0) == 'B') {
                    bSeatsData.add(array_txt);
                }
            }
        } 

        int seatCount = 0;
        for (String[] seatData : bSeatsData) {
            String seatNumber = seatData[0];
            String status = seatData[1];
            
            if (status.equalsIgnoreCase("available")) {
                System.out.print(ANSI_GREEN + "[" + seatNumber + "] " + ANSI_RESET);
            } else {
                System.out.print(ANSI_RED + "[XX] " + ANSI_RESET);
            }
            
            seatCount++;
            
            if (seatCount % 4 == 0) {
                System.out.println();
            }
        }

        if (seatCount % 4 != 0) {
             System.out.println();
        }
        
        System.out.println(ANSI_RED + "[Exit]" + ANSI_RESET);

        try (Scanner fiScanner = new Scanner(new File(filename))) {
            while (fiScanner.hasNextLine()) {
                String line = fiScanner.nextLine().trim(); 
                String[] array_txt = line.split(" ");
                
                if (array_txt.length < 2) {
                    continue; 
                }
                
                String seatNumber = array_txt[0];
                
                if (seatNumber.charAt(0) == 'A') {
                    aSeatsData.add(array_txt);
                }
            }
        } 
        
        seatCount = 0;
        for (String[] seatData : aSeatsData) {
            String seatNumber = seatData[0];
            String status = seatData[1];
            
            if (status.equalsIgnoreCase("available")) {
                System.out.print(ANSI_GREEN + "[" + seatNumber + "] " + ANSI_RESET);
            } else {
                System.out.print(ANSI_RED + "[XX] " + ANSI_RESET);
            }
            
            seatCount++;
            
            if (seatCount % 6 == 0) {
                System.out.println();
            }
        }
        
        if (seatCount % 6 != 0) {
             System.out.println();
        }
    }
//------------------------------------------------------------------------------------------------------------------
    public static void UpdateSeat(String sourceFilePath ,String destinationFilePath){
        try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        java.io.File file = new java.io.File(destinationFilePath);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {
            
            
            if (!file.exists()) {
                 file.createNewFile();
            }

            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                
                String newLine = line;

                if (line.matches("^([AB]\\d+\\s+)available(\\s+\\d{10})$")) {
                    
                    newLine = line.replaceAll("^([AB]\\d+\\s+)available(\\s+\\d{10})$", "$1unavailable$2");
                } 
                
                else if (line.matches("^([AB]\\d+\\s+)unavailable\\s*$")) { 
                    
                    
                    newLine = line.replaceAll("unavailable\\s*$", "available");
                }
                
                // เขียนบรรทัดผลลัพธ์ลงในไฟล์ชั่วคราว
                writer.write(newLine);
                writer.newLine(); // ขึ้นบรรทัดใหม่ในไฟล์ปลายทาง
            }
            
            
        } catch (IOException e) {
            System.err.println("เกิดข้อผิดพลาดในการอ่าน/เขียนไฟล์ชั่วคราว: " + e.getMessage());
            return; // หยุดการทำงานถ้าเกิดข้อผิดพลาดในการสร้างไฟล์ชั่วคราว
        }
        
        // ส่วนการย้ายไฟล์กลับ
        try {
            Path source = Paths.get(destinationFilePath);
            Path target = Paths.get(sourceFilePath);
            
            // ใช้ Files.move แทน Files.copy 
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            
            
            
        } catch (IOException e) {
            System.err.println("เกิดข้อผิดพลาดในการย้ายไฟล์กลับไปยังต้นฉบับ: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
//--------------------------------------------------------------------------------------------------------------------
   public static void DisplaySeatDetel() throws IOException {

        String sourceFile = Menu.fname+".txt"; //-----------------------------------------แก้ Menu.fname+".txt";
        String destFile = "Temp.txt";
        UpdateSeat(sourceFile, destFile);
        System.out.println("----------SEAT DETEL----------");
        List<String[]> bSeatsData = new ArrayList<>(); 
        List<String[]> aSeatsData = new ArrayList<>(); 

        // String filename = Flight.filename;
        String filename = Menu.fname+".txt"; //------------------------------แก้ Menu.fname+".txt";
        
        try (Scanner fiScanner = new Scanner(new File(filename))) { 
            while (fiScanner.hasNextLine()) {
                String line = fiScanner.nextLine().trim(); 
                String[] array_txt = line.split(" ");
                
            
                if (array_txt.length < 2) {
                    continue; 
                }

                String seatNumber = array_txt[0];
                
                if (seatNumber.charAt(0) == 'B') {
                    bSeatsData.add(array_txt);
                }
            }
        } 

        int seatCount = 0;
        for (String[] seatData : bSeatsData) {
            String seatNumber = seatData[0];
            String status = seatData[1];
            
            if (status.equalsIgnoreCase("available")) {
                System.out.print(ANSI_GREEN + "[" + seatNumber + "] " + ANSI_RESET);
            } else {
                System.out.print(ANSI_RED + "[XX] " + ANSI_RESET);
            }
            
            seatCount++;
            
            if (seatCount % 4 == 0) {
                System.out.println();
            }
        }

        if (seatCount % 4 != 0) {
             System.out.println();
        }
        
        System.out.println(ANSI_RED + "[Exit]" + ANSI_RESET);

        try (Scanner fiScanner = new Scanner(new File(filename))) {
            while (fiScanner.hasNextLine()) {
                String line = fiScanner.nextLine().trim(); 
                String[] array_txt = line.split(" ");
                
                if (array_txt.length < 2) {
                    continue; 
                }
                
                String seatNumber = array_txt[0];
                
                if (seatNumber.charAt(0) == 'A') {
                    aSeatsData.add(array_txt);
                }
            }
        } 
        
        seatCount = 0;
        for (String[] seatData : aSeatsData) {
            String seatNumber = seatData[0];
            String status = seatData[1];
            
            if (status.equalsIgnoreCase("available")) {
                System.out.print(ANSI_GREEN + "[" + seatNumber + "] " + ANSI_RESET);
            } else {
                System.out.print(ANSI_RED + "[XX] " + ANSI_RESET);
            }
            
            seatCount++;
            
            if (seatCount % 6 == 0) {
                System.out.println();
            }
        }
        
        if (seatCount % 6 != 0) {
             System.out.println();
        }
    }
//--------------------------------------------------------------------------------------------------------------------

public static void main(String[] args) {
        
        try {
            String sourceFile = "BCE011525.txt"; //-----------------------------------------แก้ Flight.filename();
            String destFile = "Temp.txt";       // กำหนดชื่อไฟล์ปลายทาง
            UpdateSeat(sourceFile, destFile);
            DisplaySeat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    




