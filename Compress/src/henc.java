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
        /*
        for (var i:X.Heap
        ) {
            System.out.print(i.freq);
            System.out.print(":");
            System.out.println(i.ch);
            //  System.out.print(":");
            //System.out.println(i.rep);
        }
        */
        X = Huffman(X);
        for (var i:X.Heap
        ) {
            System.out.print(i.freq);
            System.out.print(":");
            System.out.println(i.ch);
            //  System.out.print(":");
            //System.out.println(i.rep);
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
        // Build the MinHeap - Priority Queue
        X.BuildMinHeap(X);

        for (int i = 0; i < X.Heap.length - 1; i++){
            i++;
            Node z = new Node(0, '\u0000'); // Initialize new node
            Node left = X.ExtractMin();
            Node right = X.ExtractMin();
            z.left = left;
            z.right = right;
            z.freq = left.freq + right.freq;
            X.InsertNode(z);


        }
        //X.ExtractMin(); // Extract root of T
        return X;
    }

}
