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
                if(n != 3){
                    System.out.println("Invalid number of arguments");
                    return;
                }

                int id = Integer.parseInt(args[2]);
                delete(id);
            }
            case "summary" -> {
                if(n == 1) summary();
                if(n == 3) summary(args);
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

    static void delete(int id){
        File file = new File("data.txt");
        File temp = new File("temp.txt");
        try(BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp))){
            String line;

            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                if(data[0].equals(id+"")){
                    continue;
                }
                bw.write(line + System.lineSeparator());
            }
            System.out.println("Expense deleted successfully");
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        if(file.delete()){
            temp.renameTo(file);
        }
    }

    static void summary(){
        double total = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                total += Double.parseDouble(data[3]);
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        System.out.println("Total expenses: ₱" + total);
    }

    static void summary(String[] args){
        double total = 0;
        if(args[1].equals("--month")){
            try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
                String line;
                String month;
                while((line = br.readLine()) != null){
                    String[] data = line.split(",");
                    String[] date = data[1].split("-");
                    month = date[1];
                    if(month.equals(args[2])){
                        total += Double.parseDouble(data[3]);
                    }
                }
            } catch(IOException e){
                throw new RuntimeException();
            }
            System.out.println("Total expenses for " + args[2] + ": " + total);
        }
    }
}
