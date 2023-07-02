package net.hoshikawayoru.minsed.util.data.longer;

/**
 * 这个类是 {@link java.lang.Long} 的工具类
 */
public class LongerUtil {
    /**
     * 这个方法会返回long数组的所有元素的相加和
     * @param longs long数组
     * @return 相加后的long
     */
    public static long addAll(long[] longs){
        if (longs == null){
            return -1;
        }

        long out = 0L;

        for (long l : longs){
            out += l;
        }

        return out;
    }
}
