public class Node {
    int freq;
    char ch;
    Node left;
    Node right;
    //String rep;
    public Node(int f, char c){
        this.freq = f;
        this.ch = c;
        //this.rep = Integer.toBinaryString((int)c & 0xFF);
    }


}
