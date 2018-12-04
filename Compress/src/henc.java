import java.io.*;
import java.util.LinkedList;
import java.util.Stack;

public class henc {
    static LinkedList<Node> prefixes = new LinkedList<>(); // Use this to pushback leaf nodes after there prefixes have been set
    static Node[] pfix = new Node[256]; // Insert the nodes back into a node array with the (int)char as there index
    public static void main(String[] args){

        File target = new File(args[0]);  // This is the target file to compress
        File target_out = new File(args[0].concat(".huff"));

        byte [] buffer = toByteArray(target);   // Read file into a byte array
        CollectFreq freq = new CollectFreq(buffer); // Build frequency table i.e two unsorted int arrays
        BinHeap X = new BinHeap(GetLength(freq)); // Allocate Bin Heap
        InsertNodes(X, freq);
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
        ChangePfix(T, T.left); // Set the overall path of the left side of the tree to get to the leaf nodes
        ChangePfix(T, T.right); // Set the overall path of the right side of the tree to get to the leaf nodes
        for (var i: prefixes
             ) {
            System.out.println(pfix[i.ch].pfix + ":" + (char)pfix[i.ch].ch);
        }


    }

    private static int GetLength(CollectFreq freq){
        int i = 0;
        for (var f:freq.uchars
             ) {
            if(f > 0){
                i++;
            }
        }
        return i;
    }
    private static void InsertNodes(BinHeap X, CollectFreq freq){
        int j = 0;
        for (int i = 0; i < freq.uchars.length; i++){
            //System.out.println("U = " + Integer.toBinaryString(freq.uchars[i] & 0xFF)  + ", F = " + freq.fchars[i]);
            if(freq.uchars[i] > 0){
                X.Heap[j] = new Node();
                X.Heap[j].freq = freq.uchars[i];
                X.Heap[j].ch = i;
                j++;
            }
            //X.Heap[i] = new Node(); // Allocate node
            //X.Heap[i].freq = freq.fchars[i]; // Set node freq
            //X.Heap[i].ch = freq.uchars[i]; // Set node character
            //String.format("%8s", Integer.toBinaryString(freq.uchars[i] & 0xFF)).replace(' ','0')
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
        if(child.ch != '\u0000') {
            pfix[child.ch] = child;
            prefixes.add(child);
        }


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
            Node z = new Node(); // Initialize new node, parent
            Node left = X.ExtractMin(); // left child
            left.pfix = left.pfix.concat("0");
            Node right = X.ExtractMin(); // right child
            right.pfix = right.pfix.concat("1");
            z.left = left; // connect
            z.right = right;// connect
            z.freq = left.freq + right.freq; // sum frequencies
            X.InsertNode(z); // insert back into the minheap
        }
        return X.ExtractMin(); // last node, root of T
    }

}
