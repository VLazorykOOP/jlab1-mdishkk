import java.util.Random;

class SimulationArea {
    public static final int WIDTH = 800;  
    public static final int HEIGHT = 600; 

    public static final int LEFT_UPPER_X = 0;
    public static final int LEFT_UPPER_Y = 0;
    public static final int LEFT_UPPER_WIDTH = WIDTH / 2;
    public static final int LEFT_UPPER_HEIGHT = HEIGHT / 2;

    public static final int RIGHT_LOWER_X = WIDTH / 2;
    public static final int RIGHT_LOWER_Y = HEIGHT / 2;
    public static final int RIGHT_LOWER_WIDTH = WIDTH / 2;
    public static final int RIGHT_LOWER_HEIGHT = HEIGHT / 2;
}

abstract class Entity extends Thread {
    protected double x; 
    protected double y; 
    protected double targetX; 
    protected double targetY; 
    protected double speed; 

    protected Random rand = new Random();

    public Entity(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        setTarget();
    }

    protected abstract void setTarget();

    public void run() {
        if (isInTargetArea()) {
            System.out.println(getEntityType() + " вже в цільовій області. Не рухається.");
            return;
        }

        System.out.println(getEntityType() + " почав рух від (" + x + ", " + y + ") до (" + targetX + ", " + targetY + ")");

        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double directionX = dx / distance;
        double directionY = dy / distance;

        while (distance > speed) {
            x += directionX * speed;
            y += directionY * speed;
            distance -= speed;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getEntityType() + " рухається до (" + x + ", " + y + ")");
        }

        x = targetX;
        y = targetY;
        System.out.println(getEntityType() + " прибув до кінцевої точки (" + x + ", " + y + ")");
    }

    protected abstract boolean isInTargetArea();

    protected abstract String getEntityType();
}

class LegalEntity extends Entity {

    public LegalEntity(double x, double y, double speed) {
        super(x, y, speed);
    }

    @Override
    protected void setTarget() {
        this.targetX = SimulationArea.LEFT_UPPER_X + rand.nextDouble() * SimulationArea.LEFT_UPPER_WIDTH;
        this.targetY = SimulationArea.LEFT_UPPER_Y + rand.nextDouble() * SimulationArea.LEFT_UPPER_HEIGHT;
    }

    @Override
    protected boolean isInTargetArea() {
        return x >= SimulationArea.LEFT_UPPER_X &&
               x <= SimulationArea.LEFT_UPPER_X + SimulationArea.LEFT_UPPER_WIDTH &&
               y >= SimulationArea.LEFT_UPPER_Y &&
               y <= SimulationArea.LEFT_UPPER_Y + SimulationArea.LEFT_UPPER_HEIGHT;
    }

    @Override
    protected String getEntityType() {
        return "Юридична особа";
    }
}

class PhysicalEntity extends Entity {

    public PhysicalEntity(double x, double y, double speed) {
        super(x, y, speed);
    }

    @Override
    protected void setTarget() {
        this.targetX = SimulationArea.RIGHT_LOWER_X + rand.nextDouble() * SimulationArea.RIGHT_LOWER_WIDTH;
        this.targetY = SimulationArea.RIGHT_LOWER_Y + rand.nextDouble() * SimulationArea.RIGHT_LOWER_HEIGHT;
    }

    @Override
    protected boolean isInTargetArea() {
        return x >= SimulationArea.RIGHT_LOWER_X &&
               x <= SimulationArea.RIGHT_LOWER_X + SimulationArea.RIGHT_LOWER_WIDTH &&
               y >= SimulationArea.RIGHT_LOWER_Y &&
               y <= SimulationArea.RIGHT_LOWER_Y + SimulationArea.RIGHT_LOWER_HEIGHT;
    }

    @Override
    protected String getEntityType() {
        return "Фізична особа";
    }
}

public class Simulation {
    public static void main(String[] args) {
        double speed = 10.0; 

        Entity legal1 = new LegalEntity(500, 400, speed); 
        Entity legal2 = new LegalEntity(50, 50, speed);   
        Entity legal3 = new LegalEntity(400, 300, speed); 

        Entity physical1 = new PhysicalEntity(200, 100, speed); 
        Entity physical2 = new PhysicalEntity(700, 550, speed); 
        Entity physical3 = new PhysicalEntity(400, 300, speed); 

        legal1.start();
        legal2.start();
        legal3.start();
        physical1.start();
        physical2.start();
        physical3.start();

        try {
            legal1.join();
            legal2.join();
            legal3.join();
            physical1.join();
            physical2.join();
            physical3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Симуляція завершена.");
    }
}
