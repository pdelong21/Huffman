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
                unique[value]++; // value is the index and the actual value of at the index is the frequency
            }
            return unique;

        } else{
            return arr;
        }

    }

}
