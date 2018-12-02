import java.io.*;

public class henc {
    public static void main(String[] args){
        File target = new File(args[0]);  // This is the target file to compress
        File target_out = new File(args[0].concat(".huff"));

        byte [] buffer = toByteArray(target);   // Read file into a byte array
        CollectFreq freq = new CollectFreq(buffer); // Build frequency table i.e two unsorted int arrays

        BinHeap X = new BinHeap(freq.fchars.length); // Construct Bin Heap
        for (int i = 0; i < freq.uchars.length; i++){
            //System.out.println("U = " + Integer.toBinaryString(freq.uchars[i] & 0xFF)  + ", F = " + freq.fchars[i]);
            X.Heap[i] = new Node(); // Allocate node
            X.Heap[i].freq = freq.fchars[i]; // Set node freq
            X.Heap[i].ch = (char)freq.uchars[i]; // Set node character
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
        Node T = Huffman(X); // Build Huffman Tree, returns root node of the tree...
        System.out.println(T.freq);
        Node trav = T.left;
        while(trav != null){
            System.out.print(trav.freq);
            System.out.print(":");
            System.out.println(trav.ch);
            trav = trav.right;
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

    }

    public static byte[] toByteArray(File file){
        byte[] barray = new byte[(int) file.length()]; // Buffer
        try{
            FileInputStream is = new FileInputStream(file); // Create an input stream
            is.read(barray); // Read the file into the buffer
            is.close();

        } catch(FileNotFoundException e){
            System.out.println("Unable to open file: " + file);
        } catch (IOException e){
            System.out.println("Error reading file + " + file);
        }
        return barray;

    }

    public static Node Huffman(BinHeap X){
        // Build the MinHeap - Priority Queue
        X.BuildMinHeap(X);
        while(X.Heap.length != 1){
            Node z = new Node(); // Initialize new node
            Node left = X.ExtractMin();
            Node right = X.ExtractMin();
            z.left = left;
            z.right = right;
            z.freq = left.freq + right.freq;
            X.InsertNode(z);
        }
        return X.ExtractMin();
    }

}
