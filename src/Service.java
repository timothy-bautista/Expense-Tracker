import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class Service {
    public static String filename = "data.csv";

    static void add(List<String> list){
        String description = Helper.getArgValue(list, "--description");
        String amountStr = Helper.getArgValue(list, "--amount");
        double amount;

        if(description == null || amountStr == null){
            Helper.printFail("Missing arguments", "java Main add --description <text> --amount <number>");
            return;
        }

        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e){
            Helper.printFail("--amount must be a valid number", "java Main add --description <text> --amount <number>");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))){
            Expense expense = new Expense(description, amount);
            bw.write(expense.toString());

            System.out.println("Expense added successfully (ID: " + expense.getId() + ")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void list(){
        File file = new File(filename);
        if(!file.exists()){
            Helper.printFail("File does not exist", "");
            return;
        }

        System.out.println("ID\tDate\t\tDescription\tAmount");
        for(String[] data : Helper.readAllExpenses()){
            System.out.println(data[0] + "\t" + data[1] + "\t" + data[2] + "\t\t" + data[3]);
        }
    }

    static void delete(List<String> list){
        File file = new File(filename);
        File temp = new File("temp.csv");
        String idString = Helper.getArgValue(list, "--id");
        int id;

        if(idString == null){
            Helper.printFail("Missing arguments", "java Main delete --id <id>");
            return;
        } else {
            try {
                id = Integer.parseInt(idString);
                if(id <= -1){
                    Helper.printFail("ID must be greater than 0", "java Main delete --id <id>");
                    return;
                }
            } catch (NumberFormatException e){
                Helper.printFail("ID must be an integer", "java Main delete --id <id>");
                return;
            }
        }


        try(BufferedWriter bw = new BufferedWriter(new FileWriter(temp))){
            boolean found = false;
            for(String[] line : Helper.readAllExpenses()){
                if(line[0].equals(idString)){
                    found = true;
                    continue;
                }
                bw.write(line[0] + line[1] + line[2] + line[3] + System.lineSeparator());
            }
            if(found){
                System.out.println("Expense deleted successfully");
            } else {
                System.out.println("ID not found");
                return;
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
        File file = new File(filename);
        if(!file.exists()){
            Helper.printFail("File does not exist", "");
            return;
        }

        for (String[] line : Helper.readAllExpenses()) {
            total += Double.parseDouble(line[3]);
        }

        System.out.println("Total expenses: ₱" + total);
    }

    static void summary(List<String> list){
        double total = 0;
        String year = Integer.toString(LocalDate.now().getYear());
        String monthStr = Helper.getArgValue(list, "--month");
        File file = new File(filename);
        int month;
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                           "September", "October", "November", "December"};

        if(!file.exists()){
            Helper.printFail("File does not exist", "");
            return;
        }

        if(monthStr == null){
            Helper.printFail("Missing arguments", "java Main summary --month <MM>");
            return;
        }

        try {
            month = Integer.parseInt(monthStr);
            if(month < 1 || month > 12){
                Helper.printFail("Month must be between 01 and 12", "java Main summary --month <MM>");
                return;
            }
        } catch (NumberFormatException e){
            Helper.printFail("Month must be an integer", "java Main summary --month <MM>");
            return;
        }

        for(String[] line : Helper.readAllExpenses()){
            String[] date = line[1].split("-");
            if(date[0].equals(year) && Integer.parseInt(date[1]) == month){
                total += Double.parseDouble(line[3]);
            }
        }
        System.out.println("Total expenses for " + months[month-1] + ": ₱" + total);
    }
}
