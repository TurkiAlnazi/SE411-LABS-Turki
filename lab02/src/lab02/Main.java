package lab02;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // =========================
        // Exercise 1
        // =========================

        System.out.println("Exercise 1:");

        String[] names = {"Ali", "Mohammed", "Turki"};

        PrintableList<String> printableList =
                new PrintableList<>(names);

        printableList.printItems();


        
        // Exercise 2
       

        System.out.println("\nExercise 2:");

        NumberBox<Integer> integerBox =
                new NumberBox<>(10);

        System.out.println("Integer item: "
                + integerBox.getItem());

        System.out.println("10 + 5 = "
                + integerBox.add(5));


        NumberBox<Double> doubleBox =
                new NumberBox<>(10.5);

        System.out.println("Double item: "
                + doubleBox.getItem());

        System.out.println("10.5 + 2.5 = "
                + doubleBox.add(2.5));


        List<Integer> numbers =
                Arrays.asList(10, 20, 30);

        System.out.println("Sum = "
                + NumberBox.sumNumbers(numbers));


        // =========
        // Exercise 3
       

        System.out.println("\nExercise 3:");

        Pipeline<String, String> pipeline =
                new Pipeline<>();

        Pipeline<String, String> step1 =
                pipeline.add(text -> text.toUpperCase());

        Pipeline<String, Integer> step2 =
                step1.add(text -> text.length());

        int result = step2.execute("hello");

        System.out.println("Final result: " + result);


        // ========= // =========
        // Exercise 4
        // ========

        System.out.println("\nExercise 4:");

        List<String> words =
                Arrays.asList("Java", "Generics", "Lab");

        printList(words);

        List<Double> values =
                Arrays.asList(5.5, 10.5, 20.0);

        System.out.println("Sum = "
                + sumNumbers(values));
    }


    public static void printList(List<?> list) {

        for (Object item : list) {
            System.out.println(item);
        }
    }


    public static double sumNumbers(
            List<? extends Number> list) {

        double sum = 0;

        for (Number number : list) {
            sum += number.doubleValue();
        }

        return sum;
    }
}
