package com.ncu.main;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.file.StandardCopyOption;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

class Tutorial
{private static final Logger log= Logger.getLogger(Tutorial.class); //Logger object telling which class to be logged
 Properties prop;
 
 public static void main(String ar[]) throws IOException
 {
  PropertyConfigurator.configure("/home/rajan/Downloads/Project/configs/logger/logger.properties"); //Telling how logs will be logged using certain properties
  int fileLength=25; //Setting max filename length to 25
  Scanner sc = new Scanner(System.in);
  Tutorial t=new Tutorial(); 
  try{
  System.out.println("List of database: Database");
  System.out.print("Choose the database:");
  String db = sc.nextLine(); 
  if(db.equals("")){
     throw new EmptyFileNameException("File name cannot be empty.");
  }

  if(db.equals("Database"))
  {
    System.out.println("Tables in Database: Database.csv");
    System.out.print("Choose the table ");
    String tb = sc.nextLine();
    String[] name = tb.split("\\.");  
    if(name[0].length()>fileLength){
      throw new FileNameLengthException("File Name Length Exception");}
    if(!(name[1].equals("csv")==true))  {
        throw new InvalidExtensionException("Extension is not csv");
      }
    if(tb.equals("Database.csv")){
  System.out.println("Choose from the following commands:\n1.Insert\n2.Delete\n3.Select *\n4.Show Database"); // Menu that will ask for options
  int a;
  a = sc.nextInt();
  switch(a){
    case 1: { // Inserts row
         t.insert();
         log.info("Entry added.");
         break;
    }
    case 2: {
     
        File inputFile1 = new File("/home/rajan/Downloads/Project/Database/Database.csv");
        File tempFile1 = new File("/home/rajan/Downloads/Project/Database/Tutorial.csv");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile1));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile1));

        String currentLine;

        String lineToRemove;
        System.out.println("Enter the id you wish to delete ");
        lineToRemove = sc.next();

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.startsWith(lineToRemove)) continue;
            writer.write((currentLine) + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        Files.move(tempFile1.toPath(), inputFile1.toPath(), StandardCopyOption.REPLACE_EXISTING);
        log.info("Entry deleted.");
        break;
    }
    case 3:{ //Shows the table you want to see
      t.display();
      log.info("Table was shown."); 
      break;
    }
    case 4:{ Stream<Path> files = Files.walk(Paths.get("/home/rajan/Downloads/Project/Database/"));// Lists all databases in the folder
        
        files.forEach(System.out::println);
        
        files.close();
        log.info("Database was listed.");
        break;

  }

  default:{}}}else{System.out.println("The table doesn't exist.");}}
}catch(EmptyFileNameException e){
  log.error("This is an empty file name.");
}
catch(InvalidExtensionException e){
  log.error("Wrong extension for table.");
}
catch(FileNameLengthException e){
  log.error("File Name Length exceeded.");
}
}

 public void display() // Display function that shows the table on console
 {
  try
  {
  BufferedReader br=new BufferedReader(new FileReader("/home/rajan/Downloads/Project/Database/Database.csv"));
  String s="";
   while( (s=br.readLine()) !=null )
   {
    String data[]=new String[5];
    data=s.split(",");
    for(int i=0;i<5;i++)
    {
     System.out.print(data[i]+" ");
    }
    System.out.println();
   }
  }
  catch(Exception e){}
  
 }//

 public void insert() // Insert Function that inserts data in the existing csv file
 {
Scanner sc = new Scanner(System.in);
  //employee id first_name last_name salary location
  System.out.println("Enter the ID of employee:");
  String id=sc.nextLine();
  System.out.println("Enter the first name of employee:");
  String fn=sc.nextLine();
  System.out.println("Enter the last name of employee:");
  String ln=sc.nextLine();
  System.out.println("Enter the salary of employee:");
  String sl=sc.nextLine();
  System.out.println("Enter the location of employee:");
  String lc=sc.nextLine();
  
  try
  {
   File f=new File("/home/rajan/Downloads/Project/Database/Database.csv");
   PrintWriter pw=new PrintWriter(new FileOutputStream(f,true));
   pw.append(id+","+fn+","+ln+","+sl+","+lc+"\n");
   pw.close();
  }
  catch(Exception e){}
 }
 
}

class EmptyFileNameException extends Exception{
  public EmptyFileNameException(String msg)
  {
   super(msg); 
  }
}

class InvalidExtensionException extends Exception{
  public InvalidExtensionException(String msg)
  {
   super(msg); 
  }}

  class FileNameLengthException extends Exception{
  public FileNameLengthException(String msg)
  {
    super(msg);
  }
}
