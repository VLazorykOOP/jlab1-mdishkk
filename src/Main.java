import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
 
public class Main extends JFrame {
    private static final String[] FONT_NAMES = {"Serif", "SansSerif", "Monospaced", "Dialog", "DialogInput"};
    private MovingStringPanel movingStringPanel;
 
    public Main() {
        setTitle("Рядок, що рухається по діагоналі");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        movingStringPanel = new MovingStringPanel("Привіт, світ!");
        add(movingStringPanel);
 
        JButton changeFontButton = new JButton("Змінити шрифт");
        changeFontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movingStringPanel.changeFontRandomly();
            }
        });
        add(changeFontButton, BorderLayout.SOUTH);
 
        setVisible(true);
    }
 
    public static void main(String[] args) {
        new Main();
    }
 
    class MovingStringPanel extends JComponent {
        private String text;
        private int x = 0;
        private int y = 0;
        private int dx = 2;
        private int dy = 2;
        private Font font;
        private Random random = new Random();
        private JLabel label;
 
        public MovingStringPanel(String text) {
            this.text = text;
            setLayout(null);
 
            label = new JLabel(text);
            font = new Font(FONT_NAMES[0], Font.PLAIN, 24);
            label.setFont(font);
            label.setSize(label.getPreferredSize());
            add(label);
 
            Timer timer = new Timer(30, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    moveLabel();
                }
            });
            timer.start();
        }
 
        private void moveLabel() {
            x += dx;
            y += dy;
 
            int width = getWidth();
            int height = getHeight();
 
            int labelWidth = label.getWidth();
            int labelHeight = label.getHeight();
 
            if (x < 0 || x + labelWidth > width) {
                dx = -dx;
                randomizeTextCase();
                changeFontRandomly();
            }
            if (y < 0 || y + labelHeight > height) {
                dy = -dy;
                randomizeTextCase();
                changeFontRandomly();
            }
 
            label.setLocation(x, y);
 
            repaint();
        }
 
        private void randomizeTextCase() {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (random.nextBoolean()) {
                    chars[i] = Character.toUpperCase(chars[i]);
                } else {
                    chars[i] = Character.toLowerCase(chars[i]);
                }
            }
            text = new String(chars);
            label.setText(text);
        }
 
        public void changeFontRandomly() {
            int index = random.nextInt(FONT_NAMES.length);
            font = new Font(FONT_NAMES[index], Font.PLAIN, 24);
            label.setFont(font);
            label.setSize(label.getPreferredSize());
        }
    }
}