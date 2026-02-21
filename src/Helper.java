import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Helper {
    static int idGenerator(){
        String currentLine;
        String lastLine = null;
        try(BufferedReader br = new BufferedReader(new FileReader("data.csv"))){
            while((currentLine = br.readLine()) != null){
                lastLine = currentLine;
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        if(lastLine != null){
            String[] parts = lastLine.split(",");
            return Integer.parseInt(parts[0]);
        } else {
            return 0;
        }
    }
}
