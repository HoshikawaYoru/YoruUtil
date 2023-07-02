package net.hoshikawayoru.minsed.utilx.data.structure;

import java.util.ArrayList;
import java.util.Arrays;

public class Tuple<E> {
    private ArrayList<E> arrayList = new ArrayList<>();
    public Tuple(E[] elements){
        arrayList.addAll(Arrays.asList(elements));
    }
}
