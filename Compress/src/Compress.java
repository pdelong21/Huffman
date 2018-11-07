import java.io.*;
import java.nio.file.Files;

public class Compress {
    public static void main(String[] args){
        File file = new File(args[0]);
        File toPath = new File(args[0].concat(".huff"));
        try{
            //FileOutputStream foi = new FileOutputStream(toPath);
            //ByteArrayOutputStream boi = new ByteArrayOutputStream(foi);
            byte [] buffer = Files.readAllBytes(file.toPath());
            for (int i=0; i < buffer.length; i++){
                System.out.println((char)buffer[i]);
                //oi.write(buffer[i]);
            }
            //oi.close();
        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file: " + file);
        }
        catch (IOException ex){
            System.out.println("Error reading file + " + file);
        }
    }
}
