package net.hoshikawayoru.minsed.utilx.data.structure;

public class Tuple {
    private final Object[] objects;

    public Tuple(Object... objects) {
        this.objects = objects;
    }

    public Object get(int index){
        if (index < 0 || index > objects.length - 1){
            return null;
        }
        return objects[index];
    }

    public int length(){
        return objects.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("(");
        stringBuilder.append(objects[0]);

        for (int i = 1; i < objects.length; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(objects[i].toString());
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }
}
