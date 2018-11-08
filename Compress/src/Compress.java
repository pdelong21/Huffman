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
            OutputStream oi = new FileOutputStream(toPath);
            for (int i=0; i < buffer.length; i++){
                oi.write(String.format("%8s", Integer.toBinaryString(buffer[i] & 0xFF)).replace(' ','0').getBytes());
            }
            oi.close();
        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file: " + file);
        }
        catch (IOException ex){
            System.out.println("Error reading file + " + file);
        }
    }
}
