import java.io.*;
import java.nio.file.Files;

public class Compress {
    public static void main(String[] args){
        File file = new File(args[0]);
        File toPath = new File(args[0].concat(".huff"));

        try{

            byte [] buffer = Files.readAllBytes(file.toPath());
            CollectFreq freq = new CollectFreq(buffer); // Build frequency table i.e two int arrays

            for (int i = 0; i < freq.uchars.length; i++){
                System.out.println("U = " + freq.uchars[i] + ", F = " + freq.fchars[i]);
            }

        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file: " + file);
        }
        catch (IOException ex){
            System.out.println("Error reading file + " + file);
        }
    }

}
