import java.util.Objects;
import java.util.Scanner;

interface Series {
    double getTerm(int n);

    double getSum(int n);

    String getType();
}

class Linear implements Series {
    private double firstTerm; 
    private double difference; 

    public Linear(double firstTerm, double difference) {
        this.firstTerm = firstTerm;
        this.difference = difference;
    }

    @Override
    public double getTerm(int n) {
        return firstTerm + (n - 1) * difference;
    }

    @Override
    public double getSum(int n) {
        return (n / 2.0) * (2 * firstTerm + (n - 1) * difference);
    }

    @Override
    public String getType() {
        return "Арифметична прогресія";
    }

    @Override
    public String toString() {
        return String.format("Тип прогресії: %s, Перший член: %.2f, Різниця: %.2f",
                getType(), firstTerm, difference);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Linear)) return false;
        Linear other = (Linear) obj;
        return Double.compare(firstTerm, other.firstTerm) == 0 &&
               Double.compare(difference, other.difference) == 0;
    }

    public double getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(double firstTerm) {
        this.firstTerm = firstTerm;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }
}

class Exponential implements Series {
    private double firstTerm; 
    private double ratio;      

    public Exponential(double firstTerm, double ratio) {
        this.firstTerm = firstTerm;
        this.ratio = ratio;
    }

    @Override
    public double getTerm(int n) {
        return firstTerm * Math.pow(ratio, n - 1);
    }

    @Override
    public double getSum(int n) {
        if (ratio == 1) {
            return firstTerm * n;
        }
        return firstTerm * (Math.pow(ratio, n) - 1) / (ratio - 1);
    }

    @Override
    public String getType() {
        return "Геометрична прогресія";
    }

    @Override
    public String toString() {
        return String.format("Тип прогресії: %s, Перший член: %.2f, Співвідношення: %.2f",
                getType(), firstTerm, ratio);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Exponential)) return false;
        Exponential other = (Exponential) obj;
        return Double.compare(firstTerm, other.firstTerm) == 0 &&
               Double.compare(ratio, other.ratio) == 0;
    }

    public double getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(double firstTerm) {
        this.firstTerm = firstTerm;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}

public class Main8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість прогресій: ");
        int n;
        while (true) {
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                if (n > 0) {
                    scanner.nextLine(); 
                    break;
                } else {
                    System.out.print("Кількість прогресій повинна бути додатнім числом. Спробуйте ще раз: ");
                }
            } else {
                System.out.print("Некоректне введення. Введіть ціле число: ");
                scanner.next(); 
            }
        }

        Series[] seriesArray = new Series[n];

        for (int i = 0; i < n; i++) {
            System.out.printf("Виберіть тип прогресії для об'єкта %d (1 - Арифметична, 2 - Геометрична): ", i + 1);
            int choice;
            while (true) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); 
                    if (choice == 1 || choice == 2) {
                        break;
                    } else {
                        System.out.print("Невірний вибір. Введіть 1 для Арифметичної або 2 для Геометричної прогресії: ");
                    }
                } else {
                    System.out.print("Некоректне введення. Введіть 1 або 2: ");
                    scanner.next();
                }
            }

            System.out.print("Введіть перший член прогресії: ");
            double firstTerm;
            while (true) {
                if (scanner.hasNextDouble()) {
                    firstTerm = scanner.nextDouble();
                    scanner.nextLine(); 
                    break;
                } else {
                    System.out.print("Некоректне введення. Введіть число: ");
                    scanner.next(); 
                }
            }

            switch (choice) {
                case 1:
                    System.out.print("Введіть різницю прогресії: ");
                    double difference;
                    while (true) {
                        if (scanner.hasNextDouble()) {
                            difference = scanner.nextDouble();
                            scanner.nextLine(); 
                            break;
                        } else {
                            System.out.print("Некоректне введення. Введіть число: ");
                            scanner.next(); 
                        }
                    }
                    seriesArray[i] = new Linear(firstTerm, difference);
                    break;
                case 2:
                    System.out.print("Введіть співвідношення прогресії: ");
                    double ratio;
                    while (true) {
                        if (scanner.hasNextDouble()) {
                            ratio = scanner.nextDouble();
                            scanner.nextLine(); 
                            break;
                        } else {
                            System.out.print("Некоректне введення. Введіть число: ");
                            scanner.next(); 
                        }
                    }
                    seriesArray[i] = new Exponential(firstTerm, ratio);
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    i--; 
                    continue;
            }
            System.out.println("Прогресія успішно додана!\n");
        }

        scanner.close();

        System.out.println("\nВведені прогресії:");
        for (int i = 0; i < seriesArray.length; i++) {
            System.out.printf("Прогресія %d: %s\n", i + 1, seriesArray[i].toString());
            System.out.printf("10-й член: %.2f\n", seriesArray[i].getTerm(10));
            System.out.printf("Сума перших 10 членів: %.2f\n", seriesArray[i].getSum(10));
            System.out.println("---------------------------");
        }

        if (n >= 2) {
            System.out.println("Перевірка рівності перших двох прогресій:");
            System.out.println("Прогресія 1: " + seriesArray[0].toString());
            System.out.println("Прогресія 2: " + seriesArray[1].toString());
            if (seriesArray[0].equals(seriesArray[1])) {
                System.out.println("Прогресії однакові.");
            } else {
                System.out.println("Прогресії різні.");
            }
        }
    }
}

