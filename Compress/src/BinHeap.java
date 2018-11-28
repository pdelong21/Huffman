public class BinHeap {
    Node[] Heap;
    public BinHeap(int hsize) {this.Heap = new Node[hsize];}

    public  BinHeap BuildMinHeap(BinHeap X){
        for (int i =(int)Math.floor(X.Heap.length/2.0); i>=0; i--){
            MinHeapify(X, i);

        }
        return X;
    }
    public  BinHeap MinHeapify(BinHeap X, int i){
        if(i > X.Heap.length/2){
            return X;
        }
        int left = 2*i + 1, right = 2*i + 2;
        int min = FindMin(i, left, right, X);
        if (min != i){
            X = Swap(i, min, X);
            X = MinHeapify(X, min);
        }
        return X;
    }
    public  int FindMin(int i, int left, int right, BinHeap X){
        if(right >= X.Heap.length && left < X.Heap.length){
            if(X.Heap[i].freq < X.Heap[left].freq){
                return i;
            } else return left;
        } else if(left >= X.Heap.length){
            return i;
        }
        if (X.Heap[i].freq < X.Heap[left].freq && X.Heap[i].freq < X.Heap[right].freq) return i;
        if (X.Heap[i].freq > X.Heap[left].freq && X.Heap[left].freq <= X.Heap[right].freq) return left;
        if (X.Heap[i].freq > X.Heap[right].freq && X.Heap[right].freq <= X.Heap[left].freq) return right;
        return i; // By chance all the values are equal to one another



    }
    public BinHeap Swap(int i, int min, BinHeap X){
        Node swap = X.Heap[i];
        X.Heap[i] = X.Heap[min];
        X.Heap[min] = swap;
        return X;
    }

    public Node ExtractMin(){
        if(this.Heap.length < 1){
            return null;
        }
        Node min = this.Heap[0];
        this.Heap[0] = this.Heap[this.Heap.length - 1];
        Node[] newHeap = new Node[this.Heap.length - 1];
        for (int i = 0; i < this.Heap.length - 1; i++){
            newHeap[i] = this.Heap[i];
        }
        this.Heap = newHeap;
        this.MinHeapify(this, 0);
        //System.out.println(this.Heap.length);
        return min;
    }

    public void InsertNode(Node node){
        Node[] newHeap = new Node[this.Heap.length + 1];
        for (int j = 0; j < this.Heap.length; j++){
            newHeap[j] = this.Heap[j];
        }

        int i = newHeap.length-1;
        while((i>0) && newHeap[Parent(i)].freq > node.freq){
            newHeap[i] = newHeap[Parent(i)];
            i = Parent(i);
        }
        newHeap[i] = node;
        this.Heap = newHeap;

    }
    private int Parent(int i){
        if(i % 2 == 0){
            i = (int)Math.floor((i/2.0)-1);
            return i;
        }
        i = (int)Math.floor((i/2.0));
        return i;
    }
}
