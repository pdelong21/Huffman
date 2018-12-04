public class CollectFreq {
    public int[] uchars; // Unique characters read
    public int[] fchars; // Frequency of unique characters
    public CollectFreq(byte[] buffer){
        this.uchars = GetUniqueChars(buffer);
    } // Requires input of a byte array

    private int[] GetUniqueChars(byte[] buffer){
        int[] unique = new int[256]; // will be in the worst case no unique chars...unlikely
        /*
        int[] freq = new int[256]; // worst case we need a place for every ascii character

        int j = 0; // our counter for inserting unique characters
        for (int i = 0; i < buffer.length; i++){
            int value = Byte.toUnsignedInt(buffer[i]); // gives us the integer value of the character
            int index = CharInArray(value, unique); // check to see if it is already in the array
            // if CharinArray is in the array take note of its frequency
            if (index != -1){
                freq[index] ++; // keep track of freq
            }
            // otherwise take note of the char, frequency, and increment j
            else{
                unique[j] = value;
                freq[j] ++;
                j++;
            }
        }
        this.fchars = Trim(freq);
        return Trim(unique);
        */
        for (var b: buffer
             ) {
            int value = Byte.toUnsignedInt(b);
            unique[value]++;
        }
        return unique;
    }
    private  int CharInArray(int target, int[] CheckIn){
        for (int i = 0; i < CheckIn.length; i++){
            if (target == CheckIn[i]){
                return i; // return the index if its already in the array
            }
        }
        return -1; // index will always return positive so this means nothing was found
    }
    // Will look for the first null value and then trim by keeping everything before the first null occurrence
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
