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

            for (int i = 0; i < freq.uchars.length; i++){
                System.out.println("U = " + Integer.toBinaryString(freq.uchars[i] & 0xFF)  + ", F = " + freq.fchars[i]);
                //String.format("%8s", Integer.toBinaryString(freq.uchars[i] & 0xFF)).replace(' ','0')
            }

        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file: " + file);
        }
        catch (IOException ex){
            System.out.println("Error reading file + " + file);
        }
    }

    public PriorityQueue Huffman(CollectFreq freq){
        PriorityQueue Q = new PriorityQueue();
        Q = BuildMinHeap(Q, freq);
    }

    public PriorityQueue BuildMinHeap(PriorityQueue Q, CollectFreq freq){
        for (int i =(int)Math.floor(freq.fchars.length/2.0); i>=1; i--){
            Q = MinHeapify(freq, i);
        }
        return Q;
    }
    public PriorityQueue MinHeapify(CollectFreq freq, int i){
        int left = i - 1, right = i + 1;
        int min = FindMin(i, left, right, freq);
        if (min != i)
    }
    public int FindMin(int i, int j, int k, CollectFreq freq){
        int min = freq.fchars[i];
        if (min >= j) min = j;
        if (min >= k) min = k;
        return min;
    }
}
