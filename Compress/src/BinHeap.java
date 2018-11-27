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
        if (X.Heap[i].freq > X.Heap[left].freq && X.Heap[left].freq < X.Heap[right].freq) return left;
        if (X.Heap[i].freq > X.Heap[right].freq && X.Heap[right].freq < X.Heap[left].freq) return right;
        return i; // By chance all the values are equal to one another



    }
    public BinHeap Swap(int i, int min, BinHeap X){
        Node swap = X.Heap[i];
        X.Heap[i] = X.Heap[min];
        X.Heap[min] = swap;
        return X;
    }
}
