package net.hoshikawayoru.minsed.util.integer;

public class IntegerUtil {
    public static Integer getHighestByte(Integer[] integers){
        int highestInteger = Integer.MIN_VALUE;

        for (Integer integer : integers){
            if (integer > highestInteger){
                highestInteger = integer;
            }
        }
        return highestInteger;
    }
    public static Integer getLowestByte(Integer[] integers){
        int lowestInteger = Integer.MAX_VALUE;

        for (Integer integer : integers){
            if (integer < lowestInteger){
                lowestInteger = integer;
            }
        }
        return lowestInteger;
    }
}
