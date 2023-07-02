package net.hoshikawayoru.minsed.utilx.data.structure;

import java.util.ArrayList;
import java.util.Arrays;


public class Tuple<E> {
    private ArrayList<E> arrayList = new ArrayList<>();

    public Tuple(E[] elements) {
        arrayList.addAll(Arrays.asList(elements));
    }


    public E get(int index) {
        return arrayList.get(index);
    }


    public int length() {
        return arrayList.size();
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("(");
        stringBuilder.append(arrayList.get(0));

        for (int i = 1; i < arrayList.size(); i++) {
            stringBuilder.append(", ");
            stringBuilder.append(arrayList.get(i));
        }

        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
