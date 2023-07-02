package net.hoshikawayoru.minsed.util.longer;

public class LongerUtil {
    public static Long addAll(Long... l){
        Long out = 0L;

        for (Long l1 : l){
            out += l1;
        }
        
        return out;
    }
}
