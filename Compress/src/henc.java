import java.io.*;
import java.util.LinkedList;
import java.util.Stack;

public class henc {
    static LinkedList<Node> prefixes = new LinkedList<>();
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
        ChangePfix(T, T.left);
        ChangePfix(T, T.right);
        for (var i: prefixes
             ) {
            System.out.println(i.pfix + ":" + i.ch);
        }


    }
    /*
    public static void printd(Node t, char ch) {


        if (t.ch == ch) {
            System.out.println(t.ch);
            return;
        }

        System.out.println("awed");
        if (t.left != null){
           // System.out.print("0");
            printd(t.left, ch);
         }
        if(t.right != null){
        //System.out.print("1");
        printd(t.right, ch);
        }

    }

    private static void Print(Node T){
        if(T == null){
            return;
        }
        if(T.ch == '\u0000'){
            System.out.print(T.pfix);
        }
        else{
            System.out.println(T.pfix + T.ch);
        }
        Print(T.left);

        Print(T.right);

    }
    */

    private static void ChangePfix(Node Parent, Node child){
        // We hit a leaf
        child.pfix = Parent.pfix.concat(child.pfix);
        if(child.ch != '\u0000') prefixes.add(child);


        if(child.left != null){
            ChangePfix(child, child.left);
        }
        if(child.right != null){
            ChangePfix(child, child.right);
        }



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
            left.pfix = left.pfix.concat("0");
            Node right = X.ExtractMin();
            right.pfix = right.pfix.concat("1");
            z.left = left;
            z.right = right;
            z.freq = left.freq + right.freq;
            X.InsertNode(z);
        }
        return X.ExtractMin();
    }

}
