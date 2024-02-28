import java.awt.event.*;
import javax.swing.*;

public class Editor extends JFrame {
    
    public void open(Pipe p) {
        initzalizeWindow(p);

        createLabel("Stange ID: " + p.id, 32, 10);

        ///Legierung
        JTextArea area0 = createInputSection("" + p.legierung, "Legierung", 32, 48);
        
        ///Charge
        JTextArea area1 = createInputSection("" + p.charge,"Charge", 32, 112);

        ///Buttons
        JButton applyBtn = createButton("Apply", 32, 200);

        applyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String legierung = area0.getText();
                String charge = area1.getText();

                p.legierung = Float.parseFloat(legierung);
                p.charge = Float.parseFloat(charge);

                dispose();
            }
        });

        JButton cancelBtn = createButton("Cancel", 120, 200);

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ///Exit without saving changes
                dispose();
            }
        });
    }

    /**
     * Creates Window for changing values
     * @param p Stange (Pipe)
     */
    void initzalizeWindow(Pipe p) {
        setLayout(null);

        setTitle("Stange: " + p.id);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(250, 300);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    /**
     * Creates Label with given text at given pos(x & y)
     * @param labelText Label Text
     * @param x
     * @param y
     */
    void createLabel(String labelText, int x, int y) {
        int w = 100;
        int h = 32;

        JLabel header = new JLabel(labelText);
        header.setSize(w, h);
        header.setLocation(x, y);

        add(header);
    }

    /**
     * Creates a Text Area with Label above Text Input
     * @param textInput Textarea placeholder
     * @param headerText Label Text above Input
     * @param x 
     * @param y
     * @return Textarea Element
     */
    JTextArea createInputSection(String textInput, String headerText, int x, int y) {
        int w = 100;
        int h = 32;

        ///Textarea Header Text
        createLabel(headerText, x, y);

        ///Textarea Input
        JTextArea input = new JTextArea(textInput);
        input.setSize(w, h);
        input.setLocation(x, y + h);

        add(input);

        return input;
    }

    /***
     * Creates Button with Text at given position
     * @param buttonText Text to display on button
     * @param x
     * @param y
     * @return Button Element
     */
    JButton createButton(String buttonText, int x, int y) {
        int w = 80;
        int h = 20;

        JButton button = new JButton(buttonText);
        button.setSize(w, h);
        button.setLocation(x, y);

        add(button);

        return button;
    }
}
