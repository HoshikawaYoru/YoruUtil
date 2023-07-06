package net.hoshikawayoru.minsed.util.objects;

public class ObjectUtil {
    public static Object[] transposition(Object[] objects, long pos1, long pos2){
        if (objects == null){
            return null;
        }

        Object[] out = new Object[objects.length];

        Object obj1 = objects[(int) pos1];
        Object obj2 = objects[(int) pos2];

        System.arraycopy(objects, 0, out, 0, objects.length);

        out[(int) pos1] = obj2;
        out[(int) pos2] = obj1;

        return out;
    }
}
