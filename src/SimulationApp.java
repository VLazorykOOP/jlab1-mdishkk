import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

abstract class Document {
    protected String name;
    protected int x, y; 

    public Document(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void show() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Документ: " + name + " | Позиція: (" + x + ", " + y + ")";
    }

    public void draw(Graphics g) {
        g.drawString(name, x, y);
    }
}

class Receipt extends Document {
    public Receipt(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public String toString() {
        return "Квитанція: " + name + " | Позиція: (" + x + ", " + y + ")";
    }
}

class Invoice extends Document {
    public Invoice(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public String toString() {
        return "Накладна: " + name + " | Позиція: (" + x + ", " + y + ")";
    }
}

class Account extends Document {
    public Account(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public String toString() {
        return "Рахунок: " + name + " | Позиція: (" + x + ", " + y + ")";
    }
}

class DocumentFile extends Document {
    public DocumentFile(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public String toString() {
        return "Документ: " + name + " | Позиція: (" + x + ", " + y + ")";
    }
}

abstract class BaseAI extends Thread {
    protected List<Document> documents;
    protected volatile boolean running = true; 
    protected volatile boolean paused = false; 
    protected final Object pauseLock = new Object(); 
    protected Random rand = new Random();

    public BaseAI(List<Document> documents) {
        this.documents = documents;
    }

    public void stopAI() {
        running = false;
        resumeAI();
    }

    public void pauseAI() {
        paused = true;
    }

    public void resumeAI() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public void setThreadPriority(int priority) {
        this.setPriority(priority);
    }

    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            performBehavior();

            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void performBehavior();
}

class ReceiptAI extends BaseAI {

    public ReceiptAI(List<Document> documents) {
        super(documents);
    }

    @Override
    protected void performBehavior() {
        for (Document doc : documents) {
            if (doc instanceof Receipt) {
                doc.x += rand.nextInt(11) - 5;
                doc.y += rand.nextInt(11) - 5;
                doc.x = Math.max(0, Math.min(800, doc.x));
                doc.y = Math.max(0, Math.min(600, doc.y));
            }
        }
    }
}

class InvoiceAI extends BaseAI {

    public InvoiceAI(List<Document> documents) {
        super(documents);
    }

    @Override
    protected void performBehavior() {
        for (Document doc : documents) {
            if (doc instanceof Invoice) {
                doc.x += rand.nextInt(11) - 5;
                doc.y += rand.nextInt(11) - 5;
                doc.x = Math.max(0, Math.min(800, doc.x));
                doc.y = Math.max(0, Math.min(600, doc.y));
            }
        }
    }
}

class AccountAI extends BaseAI {

    public AccountAI(List<Document> documents) {
        super(documents);
    }

    @Override
    protected void performBehavior() {
        for (Document doc : documents) {
            if (doc instanceof Account) {
                doc.y += rand.nextInt(11) - 5;
                doc.y = Math.max(0, Math.min(600, doc.y));
            }
        }
    }
}

class DocumentFileAI extends BaseAI {

    public DocumentFileAI(List<Document> documents) {
        super(documents);
    }

    @Override
    protected void performBehavior() {
        for (Document doc : documents) {
            if (doc instanceof DocumentFile) {
                doc.x += rand.nextInt(11) - 5;
                doc.x = Math.max(0, Math.min(800, doc.x));
            }
        }
    }
}

public class SimulationApp extends JFrame {
    private DrawingPanel drawingPanel;
    private ControlPanel controlPanel;

    private List<Document> documents = new ArrayList<>();

    private ReceiptAI receiptAI;
    private InvoiceAI invoiceAI;
    private AccountAI accountAI;
    private DocumentFileAI documentFileAI;

    public SimulationApp() {
        setTitle("Симуляція Документів");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initDocuments();

        drawingPanel = new DrawingPanel(documents);
        add(drawingPanel, BorderLayout.CENTER);

        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.EAST);

        initAIThreads();

        startAIThreads();

        Timer timer = new Timer(100, e -> drawingPanel.repaint());
        timer.start();
    }

    private void initDocuments() {
        documents.add(new Receipt("Квитанція 1", 100, 100));
        documents.add(new Invoice("Накладна 1", 200, 200));
        documents.add(new Account("Рахунок 1", 300, 300));
        documents.add(new DocumentFile("Документ 1", 400, 400));
    }

    private void initAIThreads() {
        receiptAI = new ReceiptAI(documents);
        invoiceAI = new InvoiceAI(documents);
        accountAI = new AccountAI(documents);
        documentFileAI = new DocumentFileAI(documents);
    }

    private void startAIThreads() {
        receiptAI.start();
        invoiceAI.start();
        accountAI.start();
        documentFileAI.start();
    }

    class DrawingPanel extends JPanel {
        private List<Document> documents;

        public DrawingPanel(List<Document> documents) {
            this.documents = documents;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Document doc : documents) {
                if (doc instanceof Receipt) {
                    g.setColor(Color.BLUE);
                } else if (doc instanceof Invoice) {
                    g.setColor(Color.GREEN);
                } else if (doc instanceof Account) {
                    g.setColor(Color.RED);
                } else if (doc instanceof DocumentFile) {
                    g.setColor(Color.ORANGE);
                }
                g.fillOval(doc.x, doc.y, 20, 20);
                g.setColor(Color.BLACK);
                g.drawString(doc.name, doc.x, doc.y);
            }
        }
    }

    class ControlPanel extends JPanel {
        public ControlPanel() {
            setLayout(new GridLayout(0, 1, 10, 10));
            setPreferredSize(new Dimension(200, 0));

            JButton stopButton = new JButton("Зупинити AI");
            JButton resumeButton = new JButton("Відновити AI");

            stopButton.addActionListener(e -> {
                receiptAI.pauseAI();
                invoiceAI.pauseAI();
                accountAI.pauseAI();
                documentFileAI.pauseAI();
            });

            resumeButton.addActionListener(e -> {
                receiptAI.resumeAI();
                invoiceAI.resumeAI();
                accountAI.resumeAI();
                documentFileAI.resumeAI();
            });

            add(stopButton);
            add(resumeButton);

            // Випадаючі списки для виставлення пріоритетів кожного AI
            add(new JLabel("Пріоритет Receipt AI:"));
            JComboBox<String> priorityReceipt = new JComboBox<>(new String[]{"MIN_PRIORITY", "NORM_PRIORITY", "MAX_PRIORITY"});
            priorityReceipt.setSelectedIndex(1);
            priorityReceipt.addActionListener(e -> {
                String selected = (String) priorityReceipt.getSelectedItem();
                if (selected.equals("MIN_PRIORITY")) {
                    receiptAI.setThreadPriority(Thread.MIN_PRIORITY);
                } else if (selected.equals("NORM_PRIORITY")) {
                    receiptAI.setThreadPriority(Thread.NORM_PRIORITY);
                } else if (selected.equals("MAX_PRIORITY")) {
                    receiptAI.setThreadPriority(Thread.MAX_PRIORITY);
                }
            });
            add(priorityReceipt);

            add(new JLabel("Пріоритет Invoice AI:"));
            JComboBox<String> priorityInvoice = new JComboBox<>(new String[]{"MIN_PRIORITY", "NORM_PRIORITY", "MAX_PRIORITY"});
            priorityInvoice.setSelectedIndex(1);
            priorityInvoice.addActionListener(e -> {
                String selected = (String) priorityInvoice.getSelectedItem();
                if (selected.equals("MIN_PRIORITY")) {
                    invoiceAI.setThreadPriority(Thread.MIN_PRIORITY);
                } else if (selected.equals("NORM_PRIORITY")) {
                    invoiceAI.setThreadPriority(Thread.NORM_PRIORITY);
                } else if (selected.equals("MAX_PRIORITY")) {
                    invoiceAI.setThreadPriority(Thread.MAX_PRIORITY);
                }
            });
            add(priorityInvoice);

            add(new JLabel("Пріоритет Account AI:"));
            JComboBox<String> priorityAccount = new JComboBox<>(new String[]{"MIN_PRIORITY", "NORM_PRIORITY", "MAX_PRIORITY"});
            priorityAccount.setSelectedIndex(1);
            priorityAccount.addActionListener(e -> {
                String selected = (String) priorityAccount.getSelectedItem();
                if (selected.equals("MIN_PRIORITY")) {
                    accountAI.setThreadPriority(Thread.MIN_PRIORITY);
                } else if (selected.equals("NORM_PRIORITY")) {
                    accountAI.setThreadPriority(Thread.NORM_PRIORITY);
                } else if (selected.equals("MAX_PRIORITY")) {
                    accountAI.setThreadPriority(Thread.MAX_PRIORITY);
                }
            });
            add(priorityAccount);

            add(new JLabel("Пріоритет Document AI:"));
            JComboBox<String> priorityDocument = new JComboBox<>(new String[]{"MIN_PRIORITY", "NORM_PRIORITY", "MAX_PRIORITY"});
            priorityDocument.setSelectedIndex(1);
            priorityDocument.addActionListener(e -> {
                String selected = (String) priorityDocument.getSelectedItem();
                if (selected.equals("MIN_PRIORITY")) {
                    documentFileAI.setThreadPriority(Thread.MIN_PRIORITY);
                } else if (selected.equals("NORM_PRIORITY")) {
                    documentFileAI.setThreadPriority(Thread.NORM_PRIORITY);
                } else if (selected.equals("MAX_PRIORITY")) {
                    documentFileAI.setThreadPriority(Thread.MAX_PRIORITY);
                }
            });
            add(priorityDocument);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationApp app = new SimulationApp();
            app.setVisible(true);
        });
    }
}
