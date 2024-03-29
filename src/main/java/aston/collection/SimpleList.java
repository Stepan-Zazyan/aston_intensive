package aston.collection;

/**
 * Интерфейс описывает методы списка(динамического массива)
 * @author STEPAN ZAZYAN
 * @version 1.0
 */

public interface SimpleList<T> extends Iterable<T> {
    void add(T value);
    T addByIndex(int index, T value);
    T set(int index, T newValue);
    void clear();
    T remove(int index);
    T removeByValue(T value);
    T get(int index);
    int size();
}