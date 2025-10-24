import functions.*;
import functions.basic.*;
import functions.meta.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        // Создание тестовых данных
        FunctionPoint[] points1 = {
                new FunctionPoint(0.0, 0.0),
                new FunctionPoint(1.0, 1.0),
                new FunctionPoint(2.0, 8.0)
        };

        FunctionPoint[] points2 = {
                new FunctionPoint(0.0, 0.0),
                new FunctionPoint(1.0, 1.0),
                new FunctionPoint(2.0, 8.0)
        };

        FunctionPoint[] points3 = {
                new FunctionPoint(0.0, 0.0),
                new FunctionPoint(2.0, 8.0)  // Пропущена точка
        };

        FunctionPoint[] points4 = {
                new FunctionPoint(0.0, 0.0),
                new FunctionPoint(1.0, 1.001), // Незначительное изменение
                new FunctionPoint(2.0, 8.0)
        };

        // Создание объектов для тестирования
        ArrayTabulatedFunction arrayFunc1 = new ArrayTabulatedFunction(points1);
        ArrayTabulatedFunction arrayFunc2 = new ArrayTabulatedFunction(points2);
        ArrayTabulatedFunction arrayFunc3 = new ArrayTabulatedFunction(points3);
        ArrayTabulatedFunction arrayFunc4 = new ArrayTabulatedFunction(points4);

        LinkedListTabulatedFunction linkedFunc1 = new LinkedListTabulatedFunction(points1);
        LinkedListTabulatedFunction linkedFunc2 = new LinkedListTabulatedFunction(points2);
        LinkedListTabulatedFunction linkedFunc3 = new LinkedListTabulatedFunction(points3);

        System.out.println("Тест toString()");
        testToString(arrayFunc1, linkedFunc1);

        System.out.println("\nТест equals()");
        testEquals(arrayFunc1, arrayFunc2, arrayFunc3, linkedFunc1, linkedFunc2, linkedFunc3);

        System.out.println("\nТест hashCode()");
        testHashCode(arrayFunc1, arrayFunc2, arrayFunc4, linkedFunc1, linkedFunc2);

        System.out.println("\nТест clone()");
        testClone(arrayFunc1, linkedFunc1);
    }

    // Тестирование метода toString()
    private static void testToString(ArrayTabulatedFunction arrayFunc,
                                     LinkedListTabulatedFunction linkedFunc) {
        System.out.println("ArrayTabulatedFunction toString(): " + arrayFunc.toString());
        System.out.println("LinkedListTabulatedFunction toString(): " + linkedFunc.toString());
    }

    // Тестирование метода equals()
    private static void testEquals(ArrayTabulatedFunction array1, ArrayTabulatedFunction array2,
                                   ArrayTabulatedFunction array3, LinkedListTabulatedFunction linked1,
                                   LinkedListTabulatedFunction linked2, LinkedListTabulatedFunction linked3) {

        System.out.println("Сравнение одинаковых объектов");
        System.out.println("array1.equals(array2): " + array1.equals(array2)); // true
        System.out.println("linked1.equals(linked2): " + linked1.equals(linked2)); // true

        System.out.println("\nСравнение разных объектов");
        System.out.println("array1.equals(array3): " + array1.equals(array3)); // false
        System.out.println("linked1.equals(linked3): " + linked1.equals(linked3)); // false

        System.out.println("\nСравнение объектов разных классов");
        System.out.println("array1.equals(linked1): " + array1.equals(linked1)); // true
        System.out.println("linked1.equals(array1): " + linked1.equals(array1)); // true

        System.out.println("\nСравнение с null");
        System.out.println("array1.equals(null): " + array1.equals(null)); // false

        System.out.println("\nСравнение с самим собой");
        System.out.println("array1.equals(array1): " + array1.equals(array1)); // true
    }

    // Тестирование метода hashCode()
    private static void testHashCode(ArrayTabulatedFunction array1, ArrayTabulatedFunction array2,
                                     ArrayTabulatedFunction array4, LinkedListTabulatedFunction linked1,
                                     LinkedListTabulatedFunction linked2) {

        System.out.println("Хэш-коды одинаковых объектов");
        int arrayHash1 = array1.hashCode();
        int arrayHash2 = array2.hashCode();
        int linkedHash1 = linked1.hashCode();
        int linkedHash2 = linked2.hashCode();

        System.out.println("Array1 hashCode: " + arrayHash1);
        System.out.println("Array2 hashCode: " + arrayHash2);
        System.out.println("Linked1 hashCode: " + linkedHash1);
        System.out.println("Linked2 hashCode: " + linkedHash2);

        System.out.println("array1.hashCode == array2.hashCode: " + (arrayHash1 == arrayHash2)); // true
        System.out.println("linked1.hashCode == linked2.hashCode: " + (linkedHash1 == linkedHash2)); // true

        System.out.println("\nПроверка согласованности equals/hashCode");
        System.out.println("Если equals=true, то hashCode равны: " +
                (array1.equals(array2) == (arrayHash1 == arrayHash2))); // true

        System.out.println("\nХэш-код после незначительного изменения");
        int arrayHash4 = array4.hashCode();
        System.out.println("Array4 (с изменением) hashCode: " + arrayHash4);
        System.out.println("Array1.hashCode == Array4.hashCode: " + (arrayHash1 == arrayHash4)); // false
        System.out.println("Изменение хэш-кода: " + Math.abs(arrayHash1 - arrayHash4));
    }

    // Тестирование метода clone()
    private static void testClone(ArrayTabulatedFunction arrayFunc,
                                  LinkedListTabulatedFunction linkedFunc) {

        System.out.println("Тестирование ArrayTabulatedFunction clone()");
        ArrayTabulatedFunction arrayClone = (ArrayTabulatedFunction) arrayFunc.clone();

        System.out.println("Исходный Array: " + arrayFunc);
        System.out.println("Клон Array: " + arrayClone);
        System.out.println("Равенство до изменения: " + arrayFunc.equals(arrayClone)); // true

        // Замена оригинала
        arrayFunc.setPointY(0, 999.0);
        System.out.println("После изменения оригинала:");
        System.out.println("Исходный Array Y[0]: " + arrayFunc.getPointY(0)); // 999.0
        System.out.println("Клон Array Y[0]: " + arrayClone.getPointY(0)); // 0.0
        System.out.println("Равенство после изменения: " + arrayFunc.equals(arrayClone)); // false
        System.out.println("Разные объекты в памяти: " + (arrayFunc != arrayClone)); // true

        System.out.println("\nТестирование LinkedListTabulatedFunction clone()");
        LinkedListTabulatedFunction linkedClone = (LinkedListTabulatedFunction) linkedFunc.clone();

        System.out.println("Исходный Linked: " + linkedFunc);
        System.out.println("Клон Linked: " + linkedClone);
        System.out.println("Равенство до изменения: " + linkedFunc.equals(linkedClone)); // true

        // Замена оригинала
        linkedFunc.setPointY(1, 888.0);
        System.out.println("После изменения оригинала:");
        System.out.println("Исходный Linked Y[1]: " + linkedFunc.getPointY(1)); // 888.0
        System.out.println("Клон Linked Y[1]: " + linkedClone.getPointY(1)); // 1.0
        System.out.println("Равенство после изменения: " + linkedFunc.equals(linkedClone)); // false
        System.out.println("Разные объекты в памяти: " + (linkedFunc != linkedClone)); // true

        System.out.println("\nПроверка глубокого копирования");
        System.out.println("Array: изменение оригинала не затронуло клон - " +
                (arrayFunc.getPointY(0) != arrayClone.getPointY(0))); // true
        System.out.println("Linked: изменение оригинала не затронуло клон - " +
                (linkedFunc.getPointY(1) != linkedClone.getPointY(1))); // true
    }
}