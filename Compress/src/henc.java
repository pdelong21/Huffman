import java.io.*;
import java.nio.file.Files;

public class henc {
    public static void main(String[] args){
        File file = new File(args[0]);
        File toPath = new File(args[0].concat(".huff"));

        try{

            byte [] buffer = Files.readAllBytes(file.toPath());
            CollectFreq freq = new CollectFreq(buffer); // Build frequency table i.e two unsorted int arrays
            int[] Q;



            for (int i = 0; i < freq.uchars.length; i++){
                System.out.println("U = " + Integer.toBinaryString(freq.uchars[i] & 0xFF)  + ", F = " + freq.fchars[i]);
                //String.format("%8s", Integer.toBinaryString(freq.uchars[i] & 0xFF)).replace(' ','0')
            }
            Q = Huffman(freq);

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
            Q = Swap(i, min, Q);
            Q = MinHeapify(Q, min);
        }
        return Q;
    }
    public static int FindMin(int i, int left, int right, int[] Q){
        if (Q[i] < Q[left] && Q[i] < Q[right]) return i;
        if (Q[i] > Q[left] && Q[left] < Q[right]) return left;
        if (Q[i] > Q[right] && Q[right] < Q[left]) return right;
        return i; // By chance all the values are equal to one another

    }
    public static int[] Swap(int i, int min, int[] Q){
        int swap = Q[i];
        Q[i] = Q[min];
        Q[min] = swap;
        return Q;
    }
}
