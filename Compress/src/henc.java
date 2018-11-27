import java.io.*;

public class henc {
    public static void main(String[] args){
        File file = new File(args[0]);
        File toPath = new File(args[0].concat(".huff"));

        byte [] buffer = toByteArray(file);
        CollectFreq freq = new CollectFreq(buffer); // Build frequency table i.e two unsorted int arrays

        // Construct Bin Heap, really just an array of nodes
        BinHeap X = new BinHeap(freq.fchars.length);
        for (int i = 0; i < freq.uchars.length; i++){
            //System.out.println("U = " + Integer.toBinaryString(freq.uchars[i] & 0xFF)  + ", F = " + freq.fchars[i]);
            X.Heap[i] = new Node(freq.fchars[i], (char)freq.uchars[i]);
            //String.format("%8s", Integer.toBinaryString(freq.uchars[i] & 0xFF)).replace(' ','0')
        }
        for (var i:X.Heap
        ) {
            System.out.print(i.freq);
            System.out.print(":");
            System.out.println(i.ch);
        }
        System.out.println();
        X = Huffman(X);

        for (var i:X.Heap
        ) {
            System.out.print(i.freq);
            System.out.print(":");
            System.out.println(i.ch);
        }


    }

    public static byte[] toByteArray(File file){
        byte[] barray = new byte[(int) file.length()];

        try{
            FileInputStream is = new FileInputStream(file);
            is.read(barray);
            is.close();

        } catch(FileNotFoundException e){
            System.out.println("Unable to open file: " + file);
        } catch (IOException e){
            System.out.println("Error reading file + " + file);
        }
        return barray;

    }

    public static BinHeap Huffman(BinHeap X){
        X.BuildMinHeap(X);
        return X;
    }
/*
    public static BinHeap BuildMinHeap(BinHeap Q, BinHeap X){
        for (int i =(int)Math.floor(X.Heap.length/2.0); i>=1; i--){
            Q = MinHeapify(Q, i);

        }
        return Q;
    }
    public static BinHeap MinHeapify(BinHeap Q, int i){
        int left = i - 1, right = i + 1;
        int min = FindMin(i, left, right, Q);
        if (min != i){
            Q = Swap(i, min, Q);
            Q = MinHeapify(Q, i);
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
    */
}
