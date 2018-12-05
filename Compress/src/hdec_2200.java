/* PATRICK DELONG cs435 2200 mp */

import java.io.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Stack;

public class hdec_2200 {
    static LinkedList<HNode_2200> prefixes = new LinkedList<>(); // Use this to pushback leaf nodes after there prefixes have been set
    static int[] freq = new int[256]; // Insert the nodes back into a node array with the (int)char as there index
    static HNode_2200[] pfix = new HNode_2200[256]; // Insert the nodes back into a node array with the (int)char as there index

    public static void main(String[] args) {
        File target = new File(args[0]);  // This is the target file to decompress
        String target_out = (args[0].substring(0,args[0].length()-5));
        byte [] buffer = toByteArray(target);   // Read file into a byte array
        ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.length);
        byteBuffer.put(buffer);
        byteBuffer.flip();
        // Get first integer which tells us where to stop
        int length_binary = byteBuffer.getInt();
        // Array of frequencies at every ith index which is the ascii char, reconstructed the frequency table
        for(int i=0; i< freq.length; i++){
            freq[i] = byteBuffer.getInt();
        }
        BinHeap_2200 X = new BinHeap_2200(GetLength());
        CollectFreq_2200 freqObj = new CollectFreq_2200(null,true, freq);
        InsertNodes(X, freqObj); // Insert nodes from freq array to the binary heap
        HNode_2200 T = Huffman(X); // Build Huffman Tree, returns root node of the tree...
        ChangePfix(T, T.left); // Set the overall path of the left side of the tree to get to the leaf nodes
        ChangePfix(T, T.right); // Set the overall path of the right side of the tree to get to the leaf nodes
        Decode(byteBuffer, length_binary, T, target_out);


    }

    private static void Decode(ByteBuffer buffer, int len_stop, HNode_2200 T, String file){
        Stack<String> strings = new Stack<>();
        Stack<Integer> chars = new Stack<>();
        Stack<Byte> toWrite= new Stack<>();
        byte[] barray = new byte[buffer.limit()-(freq.length*4)-4];

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < barray.length; i++){
            barray[i] = buffer.get();
        }
        // Get the strings of 0's and 1's and push them into the stack reversed so there in order when popped
        for (int i = barray.length-1; i >= 0; i--){
            // format the string because we can't read spaces, only 0's and 1's
            String s = String.format("%8s", Integer.toBinaryString(barray[i] & 0xFF)).replace(' ', '0');
            strings.push(s);
        }
        // build the string
        while(!strings.empty()){
            stringBuilder.append(strings.pop());
        }
        // build stack of 1's and 0's for traversing tree
        for (int i = len_stop; i>= 0; i--){
            chars.push(Character.getNumericValue(stringBuilder.charAt(i)));
        }
        HNode_2200 trav = T;
        int dir = chars.pop();
        while(!chars.empty()){
            if(dir == 1){
                trav = trav.right;
            }
            else if(dir == 0){
                trav = trav.left;
            }
            // We hit the end of the original string, everything else is now garbage
            // We have hit the leaf
            if((trav.left == null) && (trav.right == null)){
                toWrite.push((byte)trav.ch);
                trav = T;
            }
            dir = chars.pop();

        }
        byte[] ba = new byte[toWrite.capacity()];

        for (int i = toWrite.capacity()-1;i >=0 ;i--) {
            if(!toWrite.empty()) ba[i] = toWrite.pop();
        }
        WriteOut(file, ba);

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
    private static void ChangePfix(HNode_2200 Parent, HNode_2200 child){
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
    private static void InsertNodes(BinHeap_2200 X, CollectFreq_2200 freq){
        int j = 0;
        for (int i = 0; i < freq.uchars.length; i++){
            if(freq.uchars[i] > 0){
                X.Heap[j] = new HNode_2200();
                X.Heap[j].freq = freq.uchars[i];
                X.Heap[j].ch = i;
                j++;
            }

        }
    }

    private static int GetLength() {
        int i = 0;
        for (var f : freq
        ) {
            if (f > 0) {
                i++;
            }
        }
        return i;
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

    public static HNode_2200 Huffman(BinHeap_2200 X){
        // Build the MinHeap - Priority Queue
        X.BuildMinHeap(X);
        while(X.Heap.length != 1){
            HNode_2200 z = new HNode_2200(); // Initialize new node, parent
            HNode_2200 left = X.ExtractMin(); // left child
            left.pfix = left.pfix.concat("0");
            HNode_2200 right = X.ExtractMin(); // right child
            right.pfix = right.pfix.concat("1");
            z.left = left; // connect
            z.right = right;// connect
            z.freq = left.freq + right.freq; // sum frequencies
            X.InsertNode(z); // insert back into the minheap
        }
        return X.ExtractMin(); // last node, root of T
    }
}
