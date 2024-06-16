import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomList<T> extends AbstractList<T> {
    class Node {
        T value;
        Node next;
    }

    Node start;
    Node end;

    public CustomList() {
        start = end = null;
    }

    @Override
    public int size() {
        if(start == end) {
            return 0;
        } else {
            int counter = 1;
            Node temp = start;
            while(temp != end) {
                temp = temp.next;
                counter++;
            }
            return counter;
        }
    }

    @Override
    public T get(int index) {
        if((start == null) || (size() <= index)) {
            throw new NoSuchElementException();
        }
        Node temp = start;
        for(int j = 0 ; j < index ; j++) {
            temp = temp.next;
        }
        return temp.value;
    }

    public void addLast(T value) {
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = null;

        if(start == null) {
            start = end = newNode;
        } else {
            end.next = newNode;
            end = newNode;
        }
    }

    public T getLast() {
        if(end == null) {
            throw new NoSuchElementException();
        }
        return end.value;
    }

    public void addFirst(T value) {
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = start;

        if(start == null) {
            start = newNode;
            end = newNode;
        } else {
            start = newNode;
        }


    }
    public T getFirst() {
        if(start == null) {
            throw new NoSuchElementException();
        }
        return start.value;


    }

    public T removeFirst() {
        if (start == null) {
            throw new NoSuchElementException();
        }
        T startValue = start.value;
        if(start == end) {
            start = end = null;
            return startValue;
        } else {
            start = start.next;
            return startValue;
        }
    }

    public T removeLast() {
        if (start == null) {
            throw new NoSuchElementException();
        }
        T endValue = end.value;
        if(start == end) {
            start = null;
            end = null;
            return endValue;
        } else {
            Node temp = start;
            while(temp.next != end) {
                temp = temp.next;
            }
            temp.next = null;
            end = temp;
        }
        return endValue;
    }

    public boolean add(T t) {
        addLast(t);
        return true;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node temp = start;
            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public T next() {
                T value = temp.value;
                temp = temp.next;
                return value;
            }
        };
    }

    public Stream<T> stream() {
        Stream.Builder<T> streamBuilder = Stream.builder();
        for(T item : this) {
            streamBuilder.accept(item);
        }
        return streamBuilder.build();
    }

    public static <T> List<T> filterByClass(List<T> list, Class<? extends T> clazz) {
        List<T> filteredList = new CustomList<>();
        for(T obj : list) {
            if(clazz.isInstance(obj)) {
                filteredList.add(obj);
            }
        }
        return filteredList;
    }

    public static <T extends Comparable<T>> Predicate<T> isInBounds(T lowerBound, T upperBound) {
        return num -> num.compareTo(lowerBound) >= 0 && num.compareTo(upperBound) <= 0;
    }

    public static <T extends Comparable<T>> int countElements(List<T> list, T lowerBound, T upperBound) {
        List<T> filteredList = list
                .stream()
                .filter(CustomList.isInBounds(lowerBound, upperBound))
                .toList();
        CustomList<T> resultList = new CustomList<>();
        resultList.addAll(filteredList);
        return resultList.size();
    }

    public static <T extends Number> Comparator<T> compareSums(Collection<T> a, Collection<T> b) {
        return Comparator.comparingDouble(t -> a.stream().mapToDouble(Number::doubleValue).sum() - b.stream().mapToDouble(Number::doubleValue).sum());
    }
}