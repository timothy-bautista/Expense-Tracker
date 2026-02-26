import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<String> list = Arrays.asList(args);
        parse(list);
    }

    static void parse(List<String> list){
        if(list.isEmpty()){
            Helper.printFail("No command provided", "java Main <command>");
            return;
        }

        switch(list.getFirst()){
            case "add" -> {
                if(list.size() != 5){
                    Helper.printFail("Invalid number of arguments", "java Main add --description <text> --amount <number>");
                    return;
                }

                Service.add(list);
            }
            case "list" -> {
                if(list.size() != 1){
                    Helper.printFail("Invalid number of arguments", "java Main list");
                    return;
                }

                Service.list();
            }
            case "delete" -> {
                if(list.size() != 3){
                    Helper.printFail("Invalid number of arguments", "java Main delete --id <id>");
                    return;
                }

                Service.delete(list);
            }
            case "summary" -> {
                if(list.size() != 1 && list.size() != 3){
                    Helper.printFail("Invalid number of arguments", "java Main summary OR java Main summary --month <MM>");
                    return;
                }
                if(list.size() == 1) Service.summary();
                if(list.size() == 3) Service.summary(list);
            }
            default -> {
                Helper.printFail("unknown command <command>", "java Main <command>");
            }
        }
    }
}
