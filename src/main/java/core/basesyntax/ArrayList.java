package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        elements = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public void add(T value) {
        ensureCapacity();
        elements[size++] = value;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        ensureCapacity();

        // Зсуваємо елементи вручну
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return elements[index];
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        elements[index] = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removed = elements[index];

        // Зсуваємо елементи вліво вручну
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null; // очищаємо останній елемент
        size--;
        return removed;
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            if ((element == null && elements[i] == null)
                    || (element != null && element.equals(elements[i]))) {
                return remove(i);
            }
        }
        throw new NoSuchElementException("No such element: " + element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // ---------- Приватні допоміжні методи ----------

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new ArrayListIndexOutOfBoundsException("Index " + index + " is out of bounds for add()");
        }
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = (int) (elements.length * 1.5);
            if (newCapacity == elements.length) {
                newCapacity++; // гарантія, що збільшиться хоча б на 1
            }

            T[] newArray = (T[]) new Object[newCapacity];

            // копіюємо вручну всі елементи
            for (int i = 0; i < size; i++) {
                newArray[i] = elements[i];
            }

            elements = newArray;
        }
    }
}
