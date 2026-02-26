import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    static int generateId() {
        List<String[]> list = readAllExpenses();
        int maxId = 0;
        try {
            for (int i = 0; i < list.size(); i++) {
                if (Integer.parseInt(list.get(i)[0]) > maxId) {
                    maxId = Integer.parseInt(list.get(i)[0]);
                }
            }
            return maxId + 1;
        } catch (Exception e) {
            printFail("Error reading the file", "");
        }
        return 0;
    }

    static String getArgValue(List<String> args, String flag){
        int index = args.indexOf(flag);
        if(index == -1 || index == args.size()-1){
            System.err.println("Error: no such argument " + flag);
            return null;
        }
        return args.get(index+1);
    }

    static List<String[]> readAllExpenses(){
        List<String[]> expenses = new ArrayList<>();
        File file = new File(Service.filename);
        if(!file.exists()){
            System.err.println("Error: file does not exist");
            return expenses;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    expenses.add(line.split(","));
                }
            }
        } catch(IOException e){
            System.err.println("Error reading data.csv");
        }
        return expenses;
    }

    static void printFail(String errorMessage, String usageMessage){
        System.out.println("Error: " + errorMessage);
        if(!usageMessage.isEmpty()){
            System.out.println("Usage: " + usageMessage);
        }
    }
}
