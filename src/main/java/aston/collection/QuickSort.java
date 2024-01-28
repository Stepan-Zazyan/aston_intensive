package aston.collection;

/**
 * Класс реализует работу быстрой сортировки списка
 * @author STEPAN ZAZYAN
 * @version 1.0
 */

public class QuickSort<T extends Comparable<T>> {

    /**
     * Этот метод должен добавить значение в список
     * @param simpleArrayList принимает список
     * @param first принимает начальную позицию сортировки
     * @param last принимает конечную позицию сортировки
     */
    public void quickSort(SimpleArrayList<T> simpleArrayList, int first, int last) {
        if (simpleArrayList.size() == 0 || first >= last) {
            return;
        }

        int middle = first + (last - first) / 2;
        T value = simpleArrayList.get(middle);

        int i = first;
        int j = last;
        while (i <= j) {
            while (value.compareTo(simpleArrayList.get(i)) > 0) {
                i++;
            }
            while (value.compareTo(simpleArrayList.get(j)) < 0) {
                j--;
            }

            if (i <= j) {
                T temp = simpleArrayList.get(i);
                simpleArrayList.set(i, simpleArrayList.get(j));
                simpleArrayList.set(j, temp);
                i++;
                j--;
            }
        }

        if (first < j) {
            quickSort(simpleArrayList, first, j);
        }
        if (last > i) {
            quickSort(simpleArrayList, i, last);
        }
    }
}
