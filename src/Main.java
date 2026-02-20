import java.io.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        parse(args, args.length);
    }

    static void parse(String[] args, int n){
        switch(args[0]){
            case "add" -> {
                if(n != 5){
                    System.out.println("Invalid number of arguments");
                    return;
                }
                add(args);
            }
            case "list" -> {
                list();
            }
            case "delete" -> {
//                delete method
            }
            case "summary" -> {
//                summary method;
            }
        }
    }



    static void add(String[] args){
        int currentId = Helper.idGenerator();
        String description = "";
        double amount = 0.0;
        String filename = "data.txt";
        LocalDate date = LocalDate.now();

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("--description")){
                description = args[++i];
            }
            if(args[i].equals("--amount")){
                amount = Double.parseDouble(args[++i]);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))){
            bw.write(++currentId + "," + date + "," + description + "," + amount + "\n");
            System.out.println("Expense added successfully (ID: " + currentId + ")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void list(){
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            System.out.println("ID\tDate\t\tDescription\tAmount");
            String list;
            while((list = br.readLine()) != null){
                String[] data = list.split(",");
                System.out.println(data[0] + "\t" + data[1] + "\t" + data[2] + "\t\t₱" + data[3]);
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    static void delete(){
    }
    static void summary(){
    }
}
