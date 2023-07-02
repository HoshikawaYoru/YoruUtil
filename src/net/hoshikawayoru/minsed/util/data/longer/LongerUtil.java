package net.hoshikawayoru.minsed.util.data.longer;

/**
 * 这个类是 {@link java.lang.Long} 的工具类
 */
public class LongerUtil {
    /**
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

    /**
     * @param longs long数组
     * @return 相减后的long
     */
    public static long removeAll(long[] longs){
        if (longs == null){
            return -1L;
        }

        long out = longs.clone()[0] * 2;

        for (long l : longs){
            out -= l;
        }

        return out;
    }
}
