import java.io.*;
import java.nio.file.Files;
import java.util.PriorityQueue;
import java.math.*;

public class henc {
    public static void main(String[] args){
        File file = new File(args[0]);
        File toPath = new File(args[0].concat(".huff"));

        try{

            byte [] buffer = Files.readAllBytes(file.toPath());
            CollectFreq freq = new CollectFreq(buffer); // Build frequency table i.e two unsorted int arrays
            int[] Q;
            Q = Huffman(freq);



            for (int i = 0; i < freq.uchars.length; i++){
                System.out.println("U = " + Integer.toBinaryString(freq.uchars[i] & 0xFF)  + ", F = " + freq.fchars[i]);
                //String.format("%8s", Integer.toBinaryString(freq.uchars[i] & 0xFF)).replace(' ','0')
            }
            for (var i:Q
                 ) {
                System.out.println(i);
            }

        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file: " + file);
        }
        catch (IOException ex){
            System.out.println("Error reading file + " + file);
        }
    }

    public static int[] Huffman(CollectFreq freq){
        int[] Q;
        Q = freq.fchars;
        Q = BuildMinHeap(Q, freq);
        return Q;
    }

    public static int[] BuildMinHeap(int[] Q, CollectFreq freq){
        for (int i =(int)Math.floor(Q.length/2.0); i>0; i--){
            Q = MinHeapify(Q, i);
        }
        return Q;
    }
    public static int[] MinHeapify(int[] Q, int i){
        int left = i - 1, right = i + 1;
        int min = FindMin(i, left, right, Q);
        if (min != i){
            int exchange = Q[i];
            Q[i] = Q[min];
            Q[min] = exchange;
            MinHeapify(Q, min);
        }
        return Q;
    }
    public static int FindMin(int i, int j, int k, int[] Q){
        int min = Q[i];
        int indexofmin = i;
        if (min >= Q[j]){
            min = Q[j];
            indexofmin=j;
        }
        if (min >= Q[k]) indexofmin = k;

        return indexofmin;
    }
}
