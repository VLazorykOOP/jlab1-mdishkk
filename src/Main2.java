import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Частина 1: Вхідні дані дійсного типу, результат – дійсного
        System.out.println("Частина 1: Вхідні дані дійсного типу, результат – дійсного");
        System.out.print("Введіть a (дійсне число): ");
        double a1 = scanner.nextDouble(); // Введення дійсного числа a
        System.out.print("Введіть b (дійсне число): ");
        double b1 = scanner.nextDouble(); // Введення дійсного числа b

        // Обчислення чисельника: a * b^2 - 2b + 1
        double numerator1 = a1 * Math.pow(b1, 2) - 2 * b1 + 1;
        // Обчислення знаменника: 3a^2 + 2b
        double denominator1 = 3 * Math.pow(a1, 2) + 2 * b1;
        // Обчислення результату: чисельник / знаменник
        double result1 = numerator1 / denominator1;
        // Виведення результату
        System.out.println("Результат (дійсне): " + result1);

        // Частина 2: Вхідні дані цілого типу, результат – дійсного
        System.out.println("\nЧастина 2: Вхідні дані цілого типу, результат – дійсного");
        System.out.print("Введіть a (ціле число): ");
        int a2 = scanner.nextInt(); // Введення цілого числа a
        System.out.print("Введіть b (ціле число): ");
        int b2 = scanner.nextInt(); // Введення цілого числа b

        // Обчислення чисельника: a * b^2 - 2b + 1
        double numerator2 = a2 * Math.pow(b2, 2) - 2 * b2 + 1;
        // Обчислення знаменника: 3a^2 + 2b
        double denominator2 = 3 * Math.pow(a2, 2) + 2 * b2;
        // Обчислення результату: чисельник / знаменник
        double result2 = numerator2 / denominator2;
        // Виведення результату
        System.out.println("Результат (дійсне): " + result2);

        // Частина 3: Вхідні дані дійсного типу, результат – цілого
        System.out.println("\nЧастина 3: Вхідні дані дійсного типу, результат – цілого");
        System.out.print("Введіть a (дійсне число): ");
        double a3 = scanner.nextDouble(); // Введення дійсного числа a
        System.out.print("Введіть b (дійсне число): ");
        double b3 = scanner.nextDouble(); // Введення дійсного числа b

        // Обчислення чисельника: a * b^2 - 2b + 1
        double numerator3 = a3 * Math.pow(b3, 2) - 2 * b3 + 1;
        // Обчислення знаменника: 3a^2 + 2b
        double denominator3 = 3 * Math.pow(a3, 2) + 2 * b3;
        // Обчислення результату як дійсного числа
        double result3_double = numerator3 / denominator3;
        // Перетворення результату на ціле число (усічення дробової частини)
        int result3 = (int) result3_double;
        // Виведення результату
        System.out.println("Результат (ціле): " + result3);
    }
}
