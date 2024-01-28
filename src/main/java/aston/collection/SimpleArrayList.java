package aston.collection;

import java.util.*;

/**
 * Класс реализует работу списка(динамического массива)
 *
 * @author STEPAN ZAZYAN
 * @version 1.0
 */

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;

    private int size = 0;

    private int modCount = 0;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    /**
     * Этот метод должен увеличить размер списка вдвое
     */
    private void extendSize() {
        if (container.length == 0) {
            container = Arrays.copyOf(container, container.length + 1);
        } else {
            container = Arrays.copyOf(container, container.length * 2);
        }
    }

    /**
     * Этот метод должен добавить значение в список
     *
     * @param value принимает значение value
     */
    @Override
    public void add(T value) {
        if (container.length == size) {
            extendSize();
        }
        container[size++] = value;
        modCount++;
    }

    /**
     * Этот метод должен добавить значение в список по индексу
     *
     * @param index    принимает целочисленный индекс
     * @param value принимает добавляемое значение
     * @return возвращает добавленное значение
     */
    @Override
    public T addByIndex(int index, T value) {
        if (container.length == size) {
            extendSize();
        }
        System.arraycopy(
                container,
                index,
                container,
                index + 1,
                container.length - index - 1
        );
        set(index, value);
        size++;
        modCount++;
        return value;
    }

    /**
     * Этот метод должен обновить значение в списке по индексу:
     *
     * @param index    принимает целочисленный индекс
     * @param newValue Принимает значение
     * @return старое значение по индексу
     */
    @Override
    public T set(int index, T newValue) {
        T oldValue = get(index);
        container[index] = newValue;
        return oldValue;
    }

    /**
     * Этот метод должен удалить значение в списке по индексу:
     *
     * @param index принимает целочисленный индекс
     * @return удаленное значение
     */
    @Override
    public T remove(int index) {
        T value = get(index);
        System.arraycopy(
                container,
                index + 1, //со следующего элемента 5
                container,
                index, //с текущего элемента 4
                container.length - index - 1 // 10 - 4 - 1
        );
        container[container.length - 1] = null;
        size--;
        modCount++;
        return value;
    }

    /**
     * Этот метод должен удалить первое найденное значение:
     *
     * @param value принимает значение
     * @return удаленное значение
     */
    @Override
    public T removeByValue(T value) {
        for (int i = 0; i < container.length; i++) {
            if (container[i].equals(value)) {
                value = remove(i);
                break;
            }
        }
        return value;
    }

    /**
     * Этот метод должен найти значение в списке по индексу:
     *
     * @param index принимает целочисленный номер индекса
     * @return найденное значение
     */
    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    /**
     * Этот метод должен вернуть размер списка:
     *
     * @return возвращает размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Этот метод должен вернуть размер списка:
     */
    @Override
    public void clear() {
        container = null;
        size = 0;
        modCount = 0;
    }

    /**
     * Этот метод должен вернуть объект итератор для обхода списка:
     *
     * @return возвращает итератор
     */
    @Override
    public Iterator<T> iterator() {
        int expectedModCount = modCount;
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[index++];
            }
        };
    }
}