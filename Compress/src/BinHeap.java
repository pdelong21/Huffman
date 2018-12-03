public class BinHeap {
    Node[] Heap; // Array of node objects
    public BinHeap(int hsize) {this.Heap = new Node[hsize];} // Just requires an initial size for array

    // Build Binary Heap
    public  BinHeap BuildMinHeap(BinHeap X){
        for (int i =(int)Math.floor(X.Heap.length/2.0); i>=0; i--){
            MinHeapify(X, i);
        }
        return X;
    }

    // Min heapify function
    public  BinHeap MinHeapify(BinHeap X, int i){
        // If at the leaf nodes stop
        if(i > X.Heap.length/2){
            return X;
        }
        int left = 2*i + 1, right = 2*i + 2; // Children nodes
        int min = FindMin(i, left, right, X); // Whats the min between the parent and the children?
        // If the min is not itself, swap the parent and the child and MinHeapify again from the min index
        if (min != i){
            X = Swap(i, min, X);
            X = MinHeapify(X, min);
        }
        return X;
    }
    public  int FindMin(int i, int left, int right, BinHeap X){
        // Check if the left & right children are out of bounds
        if(right >= X.Heap.length && left < X.Heap.length){
            // If the left child is not out of bounds compare it and return the min
            if(X.Heap[i].freq < X.Heap[left].freq){
                return i;
            } else return left;
        }
        // if left is out of bounds than return i because we are at a leaf node
        else if(left >= X.Heap.length){
            return i;
        }
        // Parent is less than left child and right child return parent
        if (X.Heap[i].freq < X.Heap[left].freq && X.Heap[i].freq < X.Heap[right].freq) return i;
        // Left child is less than the parent and its sibling
        if (X.Heap[i].freq > X.Heap[left].freq && X.Heap[left].freq <= X.Heap[right].freq) return left;
        // Right child is less than its parent and its sibling
        if (X.Heap[i].freq > X.Heap[right].freq && X.Heap[right].freq < X.Heap[left].freq) return right;
        return i; // By chance all the values are equal to one another return the parent



    }

    // Simple swap of nodes
    public BinHeap Swap(int i, int min, BinHeap X){
        Node swap = X.Heap[i];
        X.Heap[i] = X.Heap[min];
        X.Heap[min] = swap;
        return X;
    }

    public Node ExtractMin(){
        // If there isn't anything to extract return null
        if(this.Heap.length < 1){
            return null;
        }
        Node min = this.Heap[0]; // Copy the min node
        this.Heap[0] = this.Heap[this.Heap.length - 1]; // Make the end node the top
        Node[] newHeap = new Node[this.Heap.length - 1]; // Make a new array
        // Copy the values of the old array up until the end of the new array
        for (int i = 0; i < this.Heap.length - 1; i++){
            newHeap[i] = this.Heap[i];
        }
        this.Heap = newHeap; // Write the new array to the old one
        this.MinHeapify(this, 0); // Maintain MinHeap property
        return min; // return the min node
    }


    public void InsertNode(Node node){
        Node[] newHeap = new Node[this.Heap.length + 1]; // New heap with size + 1 for new node
        // Copy old elements into new array
        for (int j = 0; j < this.Heap.length; j++){
            newHeap[j] = this.Heap[j];
        }

        int i = newHeap.length-1; // insert from bottom -> top
        while((i>1) && newHeap[Parent(i)].freq > node.freq){
            newHeap[i] = newHeap[Parent(i)];
            i = Parent(i);
        }
        newHeap[i] = node;
        this.Heap = newHeap;

    }
    private int Parent(int i){
        // On the right branch of the parent
        if(i % 2 == 0){
            i = (int)Math.floor((i/2.0)-1);
            return i;
        }
        // On the left branch of the parent
        i = (int)Math.floor((i/2.0));
        return i;
    }
}
