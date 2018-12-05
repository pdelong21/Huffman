/* PATRICK DELONG cs435 2200 mp */

public class CollectFreq_2200 {
    public int[] uchars; // Unique characters read
    public CollectFreq_2200(byte[] buffer, boolean deocoding, int[] arr){
        this.uchars = GetUniqueChars(buffer, deocoding, arr);
    } // Requires input of a byte array

    private int[] GetUniqueChars(byte[] buffer, boolean decoding, int[] arr){
        if(!decoding){
            int[] unique = new int[256]; // will be in the worst case no unique chars
            for (var b: buffer
            ) {
                int value = Byte.toUnsignedInt(b);
                unique[value]++;
            }
            return unique;

        } else{
            return arr;
        }

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
