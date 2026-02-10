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
//                list method
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
        String description = "";
        double amount = 0.0;

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("--description")){
                description = args[++i];
            }
            if(args[i].equals("--amount")){
                amount = Double.parseDouble(args[++i]);
            }
        }
    }
    static void list(){
    }
    static void delete(){
    }
    static void summary(){
    }
}
