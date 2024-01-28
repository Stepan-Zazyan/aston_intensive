package aston.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleArrayListTest {

    private SimpleList<Integer> list;

    @BeforeEach
    public void initData() {
        list = new SimpleArrayList<>(3);
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    void checkIterator() {
        assertThat(list.size()).isEqualTo(3);
        assertThat(list).hasSize(3);
    }

    @Test
    void whenAddThenSizeIncrease() {
        list.add(4);
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    void whenRemoveThenGetValueAndSizeDecrease() {
        assertThat(list.remove(1)).isEqualTo(2);
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void whenRemoveByValueThenGetValueAndSizeDecrease() {
        assertThat(list.removeByValue(2)).isEqualTo(2);
    }

    @Test
    void whenAddAndGetByCorrectIndex() {
        list.add(4);
        assertThat(list.get(3)).isEqualTo(4);
    }

    @Test
    void whenGetByIncorrectIndexThenGetException() {
        assertThatThrownBy(() -> list.get(5))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenRemoveByIncorrectIndexThenGetException() {
        assertThatThrownBy(() -> list.remove(5))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenSetThenGetOldValueAndSizeNotChanged() {
        assertThat(list.set(1, 22)).isEqualTo(2);
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    void whenAddByIndexThenGetValueAndSizeChanged() {
        assertThat(list.addByIndex(1, 22)).isEqualTo(22);
        assertThat(list.get(2)).isEqualTo(2);
        assertThat(list.size()).isNotEqualTo(3);
    }

    @Test
    void whenClearAndSizeEqualsZero() {
        list.clear();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void whenGetIteratorFromEmptyListThenHasNextReturnFalse() {
        list = new SimpleArrayList<>(5);
        assertThat(list.iterator().hasNext()).isFalse();
    }

    @Test
    void whenGetIteratorFromEmptyListThenNextThrowException() {
        list = new SimpleArrayList<>(5);
        assertThatThrownBy(list.iterator()::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenCheckIterator() {
        Iterator<Integer> iterator = list.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenNoPlaceThenMustIncreaseCapacity() {
        assertThat(list.size()).isEqualTo(3);
        IntStream.range(3, 10).forEach(v -> list.add(v));
        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    void whenAddAfterGetIteratorThenMustBeException() {
        Iterator<Integer> iterator = list.iterator();
        list.add(4);
        assertThatThrownBy(iterator::next)
                .isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    void whenRemoveAfterGetIteratorThenMustBeException() {
        Iterator<Integer> iterator = list.iterator();
        list.remove(0);
        assertThatThrownBy(iterator::next)
                .isInstanceOf(ConcurrentModificationException.class);
    }

}