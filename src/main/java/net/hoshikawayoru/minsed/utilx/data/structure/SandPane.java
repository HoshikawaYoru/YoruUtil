package net.hoshikawayoru.minsed.utilx.data.structure;

import java.util.Collection;
import java.util.HashMap;

public class SandPane<E> {
    private HashMap<E, E> hashMap = new HashMap<>();

    public boolean isExist(E element){
        if (hashMap.get(element) != null){
            return true;
        }else {
            return false;
        }
    }

    public void add(E element){
        hashMap.put(element, element);
    }

    public void remove(E element){
        hashMap.remove(element);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Collection<E> collection = hashMap.values();



        stringBuilder.append("(");

        int i = 0;

        for (E element : collection){
            if (i == collection.size() - 1){
                stringBuilder.append(element);
                stringBuilder.append(")");
                continue;
            }

            stringBuilder.append(element);
            stringBuilder.append(", ");

            i++;
        }

        return stringBuilder.toString();
    }
}
