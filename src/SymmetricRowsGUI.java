import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class Main6 extends ArithmeticException {
    public Main6(String message) {
        super(message);
    }
}

public class SymmetricRowsGUI extends JFrame {
    private JTextField filePathField;
    private JButton loadButton;
    private JTable matrixTable;
    private JTable resultTable;
    private DefaultTableModel matrixTableModel;
    private DefaultTableModel resultTableModel;
    private JLabel statusLabel;

    public SymmetricRowsGUI() {
        setTitle("Знаходження симетричних рядків у матриці");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel filePathLabel = new JLabel("Шлях до файлу:");
        filePathField = new JTextField(30);
        loadButton = new JButton("Завантажити та обробити");

        topPanel.add(filePathLabel);
        topPanel.add(filePathField);
        topPanel.add(loadButton);

        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new GridLayout(2, 1));

        matrixTableModel = new DefaultTableModel();
        matrixTable = new JTable(matrixTableModel);
        JScrollPane matrixScrollPane = new JScrollPane(matrixTable);
        matrixScrollPane.setBorder(BorderFactory.createTitledBorder("Матриця"));

        resultTableModel = new DefaultTableModel(new Object[]{"Номери рядків"}, 0);
        resultTable = new JTable(resultTableModel);
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createTitledBorder("Симетричні рядки"));

        tablesPanel.add(matrixScrollPane);
        tablesPanel.add(resultScrollPane);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Статус: Очікування дії користувача.");
        statusPanel.add(statusLabel);

        add(topPanel, BorderLayout.NORTH);
        add(tablesPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAndProcessFile();
            }
        });
    }

    private void loadAndProcessFile() {
        String filePath = filePathField.getText().trim();
        if (filePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Будь ласка, введіть шлях до файлу.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line == null) {
                throw new NumberFormatException("Файл порожній.");
            }

            int n = Integer.parseInt(line.trim());

            if (n <= 0 || n > 20) {
                throw new Main6("Розмір матриці n повинен бути в межах від 1 до 20.");
            }

            int[][] matrix = new int[n][n];

            for (int i = 0; i < n; i++) {
                line = br.readLine();
                if (line == null) {
                    throw new NumberFormatException("Недостатньо рядків у файлі для заповнення матриці.");
                }
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length != n) {
                    throw new NumberFormatException("Рядок " + (i + 1) + " має неправильну кількість елементів.");
                }
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Integer.parseInt(tokens[j]);
                }
            }

            displayMatrix(matrix);

            ArrayList<Integer> symmetricRows = findSymmetricRows(matrix);

            displayResults(symmetricRows);

            statusLabel.setText("Статус: Обробка завершена успішно.");

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Файл не знайдено: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Статус: Файл не знайдено.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Невірний формат даних: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Статус: Невірний формат даних.");
        } catch (Main6 e) {
            JOptionPane.showMessageDialog(this, "Помилка розміру матриці: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Статус: Помилка розміру матриці.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Помилка вводу/виводу: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Статус: Помилка вводу/виводу.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Сталася непередбачена помилка: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Статус: Невідома помилка.");
        }
    }

    private void displayMatrix(int[][] matrix) {
        matrixTableModel.setRowCount(0);
        matrixTableModel.setColumnCount(0);

        int n = matrix.length;

        for (int j = 0; j < n; j++) {
            matrixTableModel.addColumn("Cт. " + (j + 1));
        }

        for (int i = 0; i < n; i++) {
            Object[] row = new Object[n];
            for (int j = 0; j < n; j++) {
                row[j] = matrix[i][j];
            }
            matrixTableModel.addRow(row);
        }
    }

    private ArrayList<Integer> findSymmetricRows(int[][] matrix) {
        ArrayList<Integer> symmetricRows = new ArrayList<>();

        int[] sequence1 = {1, 2, 3, 3, 2, 1};
        int[] sequence2 = {1, 2, 3, 5, 3, 2, 1};

        for (int i = 0; i < matrix.length; i++) {
            int[] currentRow = matrix[i];

            if (matchesSequence(currentRow, sequence1)) {
                symmetricRows.add(i + 1);
                continue;
            }

            if (matchesSequence(currentRow, sequence2)) {
                symmetricRows.add(i + 1);
            }
        }

        return symmetricRows;
    }

    private boolean matchesSequence(int[] row, int[] sequence) {
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

    private void displayResults(ArrayList<Integer> symmetricRows) {
        resultTableModel.setRowCount(0);

        if (symmetricRows.isEmpty()) {
            resultTableModel.addRow(new Object[]{"Жоден рядок не відповідає умовам."});
        } else {
            for (Integer rowNum : symmetricRows) {
                resultTableModel.addRow(new Object[]{rowNum});
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SymmetricRowsGUI().setVisible(true);
            }
        });
    }
}
