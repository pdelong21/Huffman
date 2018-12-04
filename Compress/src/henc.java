import java.io.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Stack;

public class henc {
    static LinkedList<Node> prefixes = new LinkedList<>(); // Use this to pushback leaf nodes after there prefixes have been set
    static Node[] pfix = new Node[256]; // Insert the nodes back into a node array with the (int)char as there index
    public static void main(String[] args){

        File target = new File(args[0]);  // This is the target file to compress
        String target_out = (args[0].concat(".huff"));

        byte [] buffer = toByteArray(target);   // Read file into a byte array
        CollectFreq freq = new CollectFreq(buffer); // Build frequency table i.e two unsorted int arrays
        BinHeap X = new BinHeap(GetLength(freq)); // Allocate Bin Heap space
        InsertNodes(X, freq); // Insert nodes from freq array to the binary heap
        Node T = Huffman(X); // Build Huffman Tree, returns root node of the tree...
        ChangePfix(T, T.left); // Set the overall path of the left side of the tree to get to the leaf nodes
        ChangePfix(T, T.right); // Set the overall path of the right side of the tree to get to the leaf nodes

        // Now lets encode into a new file
        Encode(buffer, target_out);

        /*
        for (var i: prefixes
             ) {
            System.out.print(pfix[i.ch].pfix);
            System.out.print(":");
            System.out.println((char)pfix[i.ch].ch);
        }
        */



    }
    private static void Encode(byte[] buffer, String t_out){
        int enc_data_size = 0;
        int length_to_store = 0;
        Stack<String> strings = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();

        // push onto a stack in reverse order so when we pop its in order
        for(int i = buffer.length-1; i >=0; i--){
            //enc_data = enc_data.concat(pfix[Byte.toUnsignedInt(b)].pfix);
            strings.push(pfix[Byte.toUnsignedInt(buffer[i])].pfix);
        }
        // calculate the size of the string that we need
        for (var i:strings
             ) {
            enc_data_size += i.length();
        }
        while(!strings.empty()){
            stringBuilder.append(strings.pop());
        }

        length_to_store = stringBuilder.length(); // the mark of the last bit that matters

        // fill the rest of the last byte if it is not divisible by 8
        System.out.println("here");
        while(stringBuilder.length()%8 != 0){
            stringBuilder.append("0");
        }
        ByteBuffer bbuf = ByteBuffer.allocate(4 + (pfix.length*4) + (stringBuilder.length()/8));
        bbuf.putInt(length_to_store); // First 4 bytes is the int that marks where to stop
        // fill in the 0 --> 255 character frequencies so we can decompress
        for (var n:pfix
             ) {
            if(n==null) bbuf.putInt(0);
            else bbuf.putInt(n.freq);
        }
        // split string into 8 bit chunks of data
        String[] sarray = SArray(stringBuilder);

        // put each byte into the buffer that has the encoded data
        for (var s:sarray
             ) {
            int rep = Integer.parseInt(s,2);
            byte b = (byte) rep;
            bbuf.put(b);
        }
        bbuf.flip();
        byte[] ba = new byte[bbuf.remaining()];
        bbuf.get(ba);
        // write out to file now
        WriteOut(t_out, ba);

    }

    private static void WriteOut(String t_file, byte[] buffer){
        try{
            FileOutputStream fo = new FileOutputStream(t_file);
            fo.write(buffer);
            fo.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static String[] SArray(StringBuilder s){
        String[] a = new String[s.length()/8];
        int j = 0;
        for (int i= 0; i<s.length(); i++){
            if((i+1)%8 == 0){
                a[j] = s.substring(i-7,i+1);
                j++;
            }
        }
        return a;
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

        }
    }


    private static void ChangePfix(Node Parent, Node child){
        // We hit a leaf
        child.pfix = Parent.pfix.concat(child.pfix);
        if(child.ch != '\u0000') {
            pfix[child.ch] = child;
            prefixes.add(child);
        }
        else if(child.left == null && child.right == null){
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
