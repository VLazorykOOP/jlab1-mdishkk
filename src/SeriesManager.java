import java.util.Objects;
import java.util.Scanner;

abstract class Series {
    protected double firstTerm; 

    public Series(double firstTerm) {
        this.firstTerm = firstTerm;
    }

    public abstract double getTerm(int n);

    public abstract double getSum(int n);

    public abstract String getType();
}

class Linear extends Series {
    private double difference; 

    public Linear(double firstTerm, double difference) {
        super(firstTerm);
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

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }
}

class Exponential extends Series {
    private double ratio; 

    public Exponential(double firstTerm, double ratio) {
        super(firstTerm);
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

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}

public class SeriesManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість прогресій: ");
        int n = scanner.nextInt();
        scanner.nextLine(); 

        Series[] seriesArray = new Series[n];

        for (int i = 0; i < n; i++) {
            System.out.printf("Виберіть тип прогресії для об'єкта %d (1 - Арифметична, 2 - Геометрична): ", i + 1);
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Введіть перший член прогресії: ");
            double firstTerm = scanner.nextDouble();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Введіть різницю прогресії: ");
                    double difference = scanner.nextDouble();
                    scanner.nextLine(); 
                    seriesArray[i] = new Linear(firstTerm, difference);
                    break;
                case 2:
                    System.out.print("Введіть співвідношення прогресії: ");
                    double ratio = scanner.nextDouble();
                    scanner.nextLine(); 
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
