import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Service {
    private static String filename = "data.csv";
    private static Expense expense;

    static void add(String[] arguments){
        List<String> list = Arrays.asList(arguments);
        String description = "";
        double amount = 0.0;

        if(!list.contains("--description")){
            System.out.println("Error: missing --description");
            return;
        }

        if(!list.contains("--amount")){
            System.out.println("Error: missing --amount");
            return;
        }

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equals("--description")){
                if(i+1 < list.size()){
                    if(list.get(i+1).equals("--amount")){
                        System.out.println("Error: --description requires a value");
                        System.out.println("Usage: java Main add --description <text> --amount <number>");
                        return;
                    }
                    description = list.get(++i);
                }
            }
            if(list.get(i).equals("--amount")){
                if(i+1 < list.size()){
                    if(list.get(i+1).equals("--description")){
                        System.out.println("Error: --amount requires a value");
                        System.out.println("Usage: java Main add --description <text> --amount <number>");
                        return;
                    }
                    try {
                        amount = Double.parseDouble(list.get(++i));
                    } catch (NumberFormatException e){
                        System.out.println("Error: --amount must be a valid number");
                        System.out.println("Usage: java Main add --description <text> --amount <number>");
                        return;
                    }
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))){
            expense = new Expense(description, amount);
            bw.write(expense.toString());
            System.out.println("Expense added successfully (ID: " + expense.getId() + ")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void list(){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
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

    static void delete(String[] arguments){
        File file = new File(filename);
        File temp = new File("temp.csv");
        List<String> list = Arrays.asList(arguments);
        int id = 0;

        if(!list.contains("--id")){
            System.out.println("Error: missing --id");
            return;
        }

        try {
            id = Integer.parseInt(list.get(2));
            if(id <= -1){
                System.out.println("Error: id must be greater than 0");
                return;
            }
        } catch (NumberFormatException e){
            System.out.println("Error: id must be an integer");
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp))){
            boolean found = false;
            String line;

            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                if(data[0].equals(Integer.toString(id))){
                    found = true;
                    continue;
                }
                bw.write(line + System.lineSeparator());
            }
            if(found){
                System.out.println("Expense deleted successfully");
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        if(file.delete()){
            temp.renameTo(file);
        }
    }

    static void summary(){
        double total = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
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
        List<String> list = Arrays.asList(args);
        double total = 0;
        int month = 0;
        String monthName = "";
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};

        if(!list.contains("--month")){
            System.out.println("Error: missing --month");
            return;
        }

        if(!list.get(1).equals("--month")){
            System.out.println("Error: --month requires a value");
            return;
        }

        try {
            month = Integer.parseInt(list.get(2));
            if(month < 1 || month > 12){
                System.out.println("Error: month must be between 01 and 12");
                return;
            }
            monthName = months[month-1];
        } catch (NumberFormatException e){
            System.out.println("Error: month must be an integer");
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                String[] date = data[1].split("-");
                if(month == Integer.parseInt(date[1])){
                    total += Double.parseDouble(data[3]);
                }
            }
        } catch(IOException e){
            throw new RuntimeException();
        }
        System.out.println("Total expenses for " + monthName + ": ₱" + total);
    }
}
