package net.hoshikawayoru.minsed.utilx.data.structure;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 这个类是元组的实现
 *
 * @param <E> 元素
 */
public class Tuple<E> {
    private ArrayList<E> arrayList = new ArrayList<>();

    /**
     * 元组是不可修改的.
     *
     * @param elements 元素
     */
    public Tuple(E[] elements) {
        arrayList.addAll(Arrays.asList(elements));
    }

    /**
     * 通过索引获取元素
     *
     * @param index 索引
     * @return 元素
     */
    public E get(int index) {
        return arrayList.get(index);
    }
}
