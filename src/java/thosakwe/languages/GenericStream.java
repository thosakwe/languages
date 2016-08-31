package thosakwe.languages;

import java.lang.reflect.Array;
import java.util.List;

public class GenericStream<T> {
    private T[] items;
    private int index = 0;

    public GenericStream() {

    }

    public GenericStream(T[] items) {
        this.items = items;
    }

    public GenericStream(Class c, List<T> list) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(c, list.size());
        this.items = list.toArray(array);
    }

    public T at(int index) {
        return items[index];
    }

    public T consume() {
        return consume(1);
    }

    public T consume(int count) {
        T result = items[index];
        index += count;
        return result;
    }

    public T current() {
        if (index < items.length)
            return items[index];
        else return null;
    }

    public int getIndex() {
        return index;
    }

    public boolean has() {
        return index < items.length;
    }

    /**
     * Determines whether the subset at the current offset matches a given string.
     *
     * @param other An array to search for starting from the current index.
     * @return Returns true or false.
     */
    public boolean is(T[] other) {

        for (int i = index; i < other.length; i++) {
            if (items[i] != other[i])
                return false;
        }

        return true;
    }

    public T peek() {
        return peek(1);
    }

    public T peek(int count) {
        if (index + count < items.length)
            return items[index + count];
        else return null;
    }

    public void seek(int index) {
        this.index = index;
    }
}
