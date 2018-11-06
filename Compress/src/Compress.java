import java.io.*;

public class Compress {
    public static void main(String[] args){
        String file = args[0];
        byte [] buffer;
        File a_file = new File(file);
        try{
            FileInputStream inputStream = new FileInputStream(file);
            buffer = new byte[(int)a_file.length()];
	    int bytesRead;
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

	    while ((bytesRead = inputStream.read(buffer)) != -1){
		byteOut.write(buffer, 0, bytesRead);
	    }
            
            inputStream.close();
	    System.out.println(buffer);
            
        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file: " + file);
        }
        catch (IOException ex){
            System.out.println("Error reading file + " + file);
        }
    }
}
