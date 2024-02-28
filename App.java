import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class App extends JFrame {

    ///Pipes
    ArrayList<Pipe> pipes = new ArrayList<Pipe>();

    int amount = 6; //Total Amount of Pipes
    int maxAmountPerRack = 5; ///Totals Amount of Pipes per Runge
    int size = 32; ///Pipe Radius

    ///Runge
    int rungeWidth = 8; //Balken
    int posX = 132;
    int posY;

    ///Truck
    BufferedImage img;
    int truckX;
    int truckY;


    public void createApp() {
        setLayout(null);

        setTitle("Meine Applikation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        setVisible(true);


        //After Window Created
        initApp();
    }

    void initApp() {

        ///Get Img
        img = Main.truckImg;

        ///Get Size
        int wWidth = getWidth();
        int wHeight = getHeight();
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();


        //Center of screen
        truckX = wWidth / 2 - imgWidth / 2;
        truckY = wHeight / 2 - imgHeight / 2;

        ///Check if total height of load is above screen
        if (maxAmountPerRack * size > 160) {
            int diff = (maxAmountPerRack * size) - 160;
            truckY += diff;
        }

        ///Pipes Position
        posX += 32;
        posY = truckY - (maxAmountPerRack * size) + imgHeight / 2;

        ///Create Pipes List
        pipes = createPipes(amount, size, posX, posY);


        ///Check if mouse clicked on graphics
        addMouseListener(new MouseListener() {
         
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) { //Dbl Click
                    for (int i = 0; i < pipes.size(); i++) {
                        Pipe p = pipes.get(i);
    
                        int mouseX = e.getX();
                        int mouseY = e.getY();
    
                        if (mouseX > p.x && mouseX < p.x + p.size
                        &&  mouseY > p.y && mouseY < p.y + p.size) {
                            new Editor().open(p); ///Open Editor Window with selected pipe
                        }
                    }
                }
            }

            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void paint(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;

        //Draw Truck
        g.drawImage(img, truckX, truckY, null);
        

        ///Draw Pipes
        g.setColor(Color.gray);
        for (int i = 0; i < pipes.size(); i++) {
            Pipe p = pipes.get(i);

            int dx = p.x;
            int dy = p.y;

            g.fillOval(dx, dy, p.size, p.size);
        }


        ///Draw Runge
        g.setColor(Color.pink);

        //Calculate how many supportes needed
        int getSpace = (int) Math.ceil((double) amount / maxAmountPerRack) + 1;

        ///Draw Supporters between runges
        for (int i = 0; i < getSpace; i++) {
            int startX = posX - rungeWidth;

            int dx = startX + (i * size) + (i * rungeWidth);

            g.fillRect(dx, posY, rungeWidth, maxAmountPerRack * size);
        }
    }

    /**
     * Creates a List of Pipes with size and position for drawing on screen
     * @param amount How many pipes created
     * @param size Radius of the pipes
     * @param x x-Position of drawing point
     * @param y y-Position of drawing Point
     * @return Returns the full created list
     */
    ArrayList<Pipe> createPipes(int amount, int size, int x, int y) {
        ArrayList<Pipe> pipes = new ArrayList<Pipe>();

        int dem = 0;
        int temp_i = -1;

        ///Create Pipes
        for (int i = 0; i < amount; i++) {
            temp_i++;

            int dx = x, dy = y;

            //Check if reached max
            if (i > 0 && i % maxAmountPerRack == 0) {
                dem++;
                temp_i -= maxAmountPerRack;

            }

            ///Update Position of Pipes
            dx += (dem * (size + rungeWidth));
            dy = y + (temp_i * size);


            ///Creating List
            Pipe p = new Pipe();
            p.x = dx;
            p.y = dy;
            p.size = size;
            p.id = i;

            pipes.add(p);
        }

        return pipes;
    }
}

class Pipe {
    public int x;
    public int y;
    public int size;
    public int id;
    public Float legierung = .0f;
    public Float charge = .0f;
}