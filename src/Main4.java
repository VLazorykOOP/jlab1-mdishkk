import java.util.Scanner;

public class Main4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введіть розмір матриці n (n ≤ 20): ");
        int n = scanner.nextInt();
        
        if (n > 20 || n <= 0) {
            System.out.println("Розмір матриці повинен бути від 1 до 20.");
            return;
        }
        
        int[][] A = new int[n][n];
        
        System.out.println("Введіть елементи матриці A (" + n + "x" + n + "):");
        for (int i = 0; i < n; i++) {
            System.out.println("Рядок " + (i + 1) + ":");
            for (int j = 0; j < n; j++) {
                A[i][j] = scanner.nextInt();
            }
        }
        
        int[] sequence1 = {1, 2, 3, 3, 2, 1};
        int[] sequence2 = {1, 2, 3, 5, 3, 2, 1};
        
        StringBuilder matchingRows = new StringBuilder();
        
        for (int i = 0; i < n; i++) {
            int[] currentRow = A[i];
            
            if (matchesSequence(currentRow, sequence1)) {
                matchingRows.append((i + 1)).append(" ");
                continue;
            }
            
            if (matchesSequence(currentRow, sequence2)) {
                matchingRows.append((i + 1)).append(" ");
            }
        }
        
        if (matchingRows.length() > 0) {
            System.out.println("Номери рядків, що утворюють задані симетричні послідовності:");
            System.out.println(matchingRows.toString().trim());
        } else {
            System.out.println("Жоден рядок не утворює задані симетричні послідовності.");
        }
    }
    
    public static boolean matchesSequence(int[] row, int[] sequence) {
        if (row.length != sequence.length) {
            return false;
        }
    
        for (int i = 0; i < sequence.length; i++) {
            if (row[i] != sequence[i]) {
                return false;
            }
        }
        
        return true;
    }
}

