import java.io.*;
import java.nio.file.Files;

public class Compress {
    public static void main(String[] args){
        File file = new File(args[0]);
        File toPath = new File(args[0].concat(".huff"));

        try{

            byte [] buffer = Files.readAllBytes(file.toPath());

        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file: " + file);
        }
        catch (IOException ex){
            System.out.println("Error reading file + " + file);
        }
    }

    public int[] freq(Byte[] barray){
        int[] f = new int[barray.length];
        return f;
    }
}
