import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введіть розмір масиву A (n ≤ 400): ");
        int n = scanner.nextInt();
        if (n > 400) {
            System.out.println("Розмір масиву A не повинен перевищувати 400.");
            return;
        }
        
        int[] A = new int[n];
        System.out.println("Введіть елементи масиву A:");
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        
        System.out.print("Введіть розмір масиву B (m ≤ 200): ");
        int m = scanner.nextInt();
        if (m > 200) {
            System.out.println("Розмір масиву B не повинен перевищувати 200.");
            return;
        }
        
        int[] B = new int[m];
        System.out.println("Введіть елементи масиву B:");
        for (int i = 0; i < m; i++) {
            B[i] = scanner.nextInt();
        }
        
        int[] C = new int[n + m];
        int indexC = 0; 
        
        for (int i = 0; i < n; i++) {
            if (!contains(B, m, A[i]) && !contains(C, indexC, A[i])) {
                C[indexC++] = A[i];
            }
        }
        
        for (int i = 0; i < m; i++) {
            if (!contains(A, n, B[i]) && !contains(C, indexC, B[i])) {
                C[indexC++] = B[i];
            }
        }
        
        System.out.println("Симетрична різниця A \\ B ∪ B \\ A:");
        for (int i = 0; i < indexC; i++) {
            System.out.print(C[i] + " ");
        }
    }
    
    public static boolean contains(int[] arr, int size, int key) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == key) {
                return true;
            }
        }
        return false;
    }
}
