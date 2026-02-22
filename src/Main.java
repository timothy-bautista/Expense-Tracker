import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        parse(args, args.length);
    }

    static void parse(String[] args, int count){
        if(count == 0){
            System.out.println("Error: No command provided");
            System.out.println("Usage: java Main <command>");
            return;
        }

        switch(args[0]){
            case "add" -> {
                if(count != 5){
                    System.out.println("Error: Invalid number of arguments");
                    System.out.println("Usage: java Main add --description <text> --amount <number>");
                    return;
                }

                Service.add(args);
            }
            case "list" -> {
                if(count != 1){
                    System.out.println("Error: Invalid number of arguments");
                    System.out.println("Usage: java Main list");
                    return;
                }

                Service.list();
            }
            case "delete" -> {
                if(count != 3){
                    System.out.println("Error: Invalid number of arguments");
                    System.out.println("Usage: java Main delete --id <id>");
                    return;
                }

                Service.delete(args);
            }
            case "summary" -> {
                if(count != 1 || count != 3){
                    System.out.println("Error: Invalid number of arguments");
                    System.out.println("Usage: java Main summary OR java Main summary --month <MM>");
                    return;
                }
                if(count == 1) Service.summary();
                if(count == 3) Service.summary(args);
            }
            default -> {
                System.out.println("Error: unknown command <command>");
                System.out.println("Usage: java Main <command>");
            }
        }
    }
}
