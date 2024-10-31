import java.util.Scanner;

class Document {
    protected String documentNumber; 
    protected String date;          

    public Document(String documentNumber, String date) {
        this.documentNumber = documentNumber;
        this.date = date;
    }

    public void Show() {
        System.out.println("Номер документа: " + documentNumber);
        System.out.println("Дата документа: " + date);
    }
}

class Receipt extends Document {
    private double amount; 

    public Receipt(String documentNumber, String date, double amount) {
        super(documentNumber, date);
        this.amount = amount;
    }

    @Override
    public void Show() {
        System.out.println("Тип документа: Квитанція");
        super.Show();
        System.out.println("Сума: " + amount);
        System.out.println("---------------------------");
    }
}

class Invoice extends Document {
    private String clientName; 
    private double amount;     

    public Invoice(String documentNumber, String date, String clientName, double amount) {
        super(documentNumber, date);
        this.clientName = clientName;
        this.amount = amount;
    }

    @Override
    public void Show() {
        System.out.println("Тип документа: Накладна");
        super.Show();
        System.out.println("Ім'я клієнта: " + clientName);
        System.out.println("Сума: " + amount);
        System.out.println("---------------------------");
    }
}

class Bill extends Document {
    private String dueDate; 
    private double amount;  

    public Bill(String documentNumber, String date, String dueDate, double amount) {
        super(documentNumber, date);
        this.dueDate = dueDate;
        this.amount = amount;
    }

    @Override
    public void Show() {
        System.out.println("Тип документа: Рахунок");
        super.Show();
        System.out.println("Термін оплати: " + dueDate);
        System.out.println("Сума: " + amount);
        System.out.println("---------------------------");
    }
}

public class DocumentManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість документів: ");
        int n = scanner.nextInt();
        scanner.nextLine(); 

        Document[] documents = new Document[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Виберіть тип документа (1 - Квитанція, 2 - Накладна, 3 - Рахунок): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Введіть номер документа: ");
            String docNumber = scanner.nextLine();

            System.out.print("Введіть дату документа (ДД.ММ.РРРР): ");
            String date = scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введіть суму квитанції: ");
                    double receiptAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    documents[i] = new Receipt(docNumber, date, receiptAmount);
                    break;
                case 2:
                    System.out.print("Введіть ім'я клієнта: ");
                    String clientName = scanner.nextLine();
                    System.out.print("Введіть суму накладної: ");
                    double invoiceAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    documents[i] = new Invoice(docNumber, date, clientName, invoiceAmount);
                    break;
                case 3:
                    System.out.print("Введіть термін оплати (ДД.ММ.РРРР): ");
                    String dueDate = scanner.nextLine();
                    System.out.print("Введіть суму рахунку: ");
                    double billAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    documents[i] = new Bill(docNumber, date, dueDate, billAmount);
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                    i--; 
                    break;
            }
            System.out.println("Документ успішно додано!\n");
        }

        scanner.close();

        System.out.println("Введені документи:");
        for (Document doc : documents) {
            doc.Show();
        }
    }
}
