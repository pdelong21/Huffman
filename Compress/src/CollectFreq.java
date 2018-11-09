public class CollectFreq {
    public int[] uchars;
    public int[] fchars;
    public CollectFreq(byte[] buffer){
        this.uchars = GetUniqueChars(buffer);
    }

    private int[] GetUniqueChars(byte[] buffer){
        int[] unique = new int[buffer.length]; // will be in the worst case no unique chars...unlikely
        int[] freq = new int[buffer.length];

        int j = 0;
        for (int i = 0; i < buffer.length; i++){
            int value = Byte.toUnsignedInt(buffer[i]); // gives us the integer value of the character
            int index = CharInArray(value, unique);
            if (index != -1){
                freq[index] ++; // keep track of freq
            }
            else{
                unique[j] = value;
                freq[j] ++;
                j++;
            }
        }
        this.fchars = Trim(freq);
        return Trim(unique);
    }
    private  int CharInArray(int target, int[] CheckIn){
        for (int i = 0; i < CheckIn.length; i++){
            if (target == CheckIn[i]){
                return i; // return the index if its already in the array
            }
        }
        return -1; // index will always return positive so this means nothing was found
    }
    private int[] Trim(int[] targetArray){
        int size = 0;
        while(size < targetArray.length){
            if(targetArray[size] != 0) size++;
            else break;
        }
        int[] TrimmedArray = new int[size];
        for (int i = 0; i < TrimmedArray.length; i++){
            TrimmedArray[i] = targetArray[i];
        }
        return TrimmedArray;

    }
}
