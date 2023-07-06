package net.hoshikawayoru.minsed.utilx.algorithm.confound;

import net.hoshikawayoru.minsed.util.objects.ObjectUtil;

public class HBEC {
    public static Long[] confound(Long[] longs){
        if (longs == null){
            return null;
        }

        Long[] out = new Long[longs.length];

        long pos1 = 0;
        long pos2;

        System.arraycopy(longs, 0, out, 0, longs.length);

        for (long l : longs) {
            pos2 = Math.abs(l) > (longs.length - 1) ? Math.abs(l) % (longs.length - 1) : Math.abs(l);

            out = (Long[]) ObjectUtil.transposition(out, pos1, pos2);
            pos1++;

        }

        return out;
    }
}
