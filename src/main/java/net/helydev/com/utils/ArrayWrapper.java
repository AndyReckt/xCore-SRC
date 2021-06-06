package net.helydev.com.utils;

import net.minecraft.util.org.apache.commons.lang3.Validate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ArrayWrapper<E>
{
    private E[] _array;

    @SafeVarargs
    public ArrayWrapper(final E... array) {
        this.setArray(array);
    }

    public static <T> T[] toArray(final Iterable<? extends T> iterable, final Class<T> clazz) {
        int size = -1;
        if (iterable instanceof Collection) {
            size = ((Collection<Object>)iterable).size();
        }
        if (size < 0) {
            size = 0;
            final Iterator<T> iterator = (Iterator<T>) iterable.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                ++size;
            }
        }
        final Object[] array = (Object[]) Array.newInstance(clazz, size);
        int n = 0;
        final Iterator<T> iterator2 = (Iterator<T>) iterable.iterator();
        while (iterator2.hasNext()) {
            array[n++] = iterator2.next();
        }
        return (T[])array;
    }

    public E[] getArray() {
        return this._array;
    }

    public void setArray(final E[] array) {
        Validate.notNull((Object)array, "The array must not be null.");
        this._array = array;
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof ArrayWrapper && Arrays.equals(this._array, ((ArrayWrapper)o)._array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this._array);
    }
}
