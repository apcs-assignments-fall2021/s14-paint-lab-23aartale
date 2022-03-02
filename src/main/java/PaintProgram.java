import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaintProgram implements ActionListener {
    LTPanel rPanel, bPanel, gPanel;
    JFrame frame;
    DrawingPanel dPanel;
    JPanel buttonPanel, colorPanel;
    JButton pencilButton, eraserButton, blackButton, redButton, blueButton,
            greenButton, clearButton, sprayButton, customButton, pickButton,
            markerButton;

    // This is the PaintProgram constructor which sets up the JFrame
    // and all other components and containers
    // ** Code to be edited in Part C **
    public PaintProgram() {
        // Set up JFrame using BorderLayout
        frame = new JFrame("Paint Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add DrawingPanel to CENTER
        dPanel = new DrawingPanel();
        frame.add(dPanel);

        // Create buttonPanel and buttons
        buttonPanel = new JPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        pencilButton = new JButton("Pencil");
        pencilButton.addActionListener(this);
        buttonPanel.add(pencilButton);

        eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(this);
        buttonPanel.add(eraserButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        sprayButton = new JButton("Spray");
        sprayButton.addActionListener(this);
        buttonPanel.add(sprayButton);

        colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        frame.add(colorPanel, BorderLayout.EAST);

        blackButton = new JButton("Black");
        blackButton.addActionListener(this);
        colorPanel.add(blackButton);

        redButton = new JButton("Red");
        redButton.addActionListener(this);
        colorPanel.add(redButton);

        blueButton = new JButton("Blue");
        blueButton.addActionListener(this);
        colorPanel.add(blueButton);

        greenButton = new JButton("Green");
        greenButton.addActionListener(this);
        colorPanel.add(greenButton);

        pickButton = new JButton("Pick");
        pickButton.addActionListener(this);
        colorPanel.add(pickButton);


        rPanel = new LTPanel("R = ", 5);
        colorPanel.add(rPanel);

        bPanel = new LTPanel("B = ", 5);
        colorPanel.add(bPanel);

        gPanel = new LTPanel("G = ", 5);
        colorPanel.add(gPanel);

        customButton = new JButton("Custom");
        customButton.addActionListener(this);
        colorPanel.add(customButton);

        markerButton = new JButton("Marker");
        markerButton.addActionListener(this);
        buttonPanel.add(markerButton);



        // Set the size and set the visibility
        frame.pack();
        frame.setVisible(true);
    }

    // This the code that is called when any button is pressed
    // We should have a separate case for each button
    // ** Code to be edited in Part B **
    public void actionPerformed(ActionEvent ae) {
        // If pencilButton is pressed, set drawingPanel mode to "Pencil"
        if (ae.getActionCommand().equals("Pencil")) {
            dPanel.setMode("Pencil");
        }
        else if (ae.getActionCommand().equals("Black")) {
            dPanel.setColor(Color.BLACK);
        }
        else if (ae.getActionCommand().equals("Red")) {
            dPanel.setColor(Color.RED);
        }
        else if (ae.getActionCommand().equals("Blue")) {
            dPanel.setColor(Color.BLUE);
        }
        else if (ae.getActionCommand().equals("Green")) {
            dPanel.setColor(Color.GREEN);
        }
        if (ae.getActionCommand().equals("Eraser")){
            dPanel.setMode("Eraser");
        }
        if (ae.getActionCommand().equals("Clear")){
            dPanel.clearFrame();
        }
        if (ae.getActionCommand().equals("Spray")){
            dPanel.setMode("Spray");
        }
        if (ae.getActionCommand().equals("Custom")){
            Color c = new Color(Integer.parseInt(rPanel.getText()), Integer.parseInt(gPanel.getText()),Integer.parseInt(bPanel.getText()));
            dPanel.setColor(c);
        }
        if (ae.getActionCommand().equals("Pick")) {
            dPanel.setMode("Pick");
        }
        if (ae.getActionCommand().equals("Marker")) {
            dPanel.setMode("Marker");
        }
    }

    // Main method just creates a PaintProgram object
    public static void main(String[] args) {
        PaintProgram x = new PaintProgram();
    }

    class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
        // DrawingPanel has the following instance variables:

        // A 2D array which stores whether or not
        // each pixel should be painted
        // ** To be used in Part B **
        private boolean[][] isPainted;

        // A 2D array which stores the Java Colors
        // of each pixel
        // ** To be used in Part C **
        private Color[][] colors;

        // The mode is a String that we can use to keep track of
        // what should happen if the user presses the mouse
        // ** To be used in Part B **
        private String mode;

        // This keeps track of the current selected color
        // ** To be used in Part C **
        private Color color;

        // These are constant values
        private static final int WIDTH = 500;
        private static final int HEIGHT = 500;

        // Constructor sets up DrawingPanel
        // ** You should never need to edit this code **
        public DrawingPanel() {
            // Set background color
            setBackground(Color.WHITE);

            // Add mouse listeners
            addMouseListener(this);
            addMouseMotionListener(this);

            // Initialize instance variables
            isPainted = new boolean[WIDTH][HEIGHT];
            colors = new Color[WIDTH][HEIGHT];
            mode = "Pencil";
            color = Color.BLACK;
        }

        public void clearFrame(){
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    isPainted[x][y] = false;

                }
            }
            repaint();
        }

        public void setMode(String mode) {
            this.mode = mode;
        }


        public void setColor(Color color) {
            this.color = color;
        }


        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Loop through the 2D array and draw a 1x1 rectangle
            // on each pixel that is currently painted
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (isPainted[x][y]) {
                        g.setColor(colors[x][y]);
                        g.drawRect(x, y, 1, 1);
                    }
                }
            }
        }

        // MouseListener methods
        // This is the method that is called when the mouse
        // is pressed. This is where most of your code will go
        // ** Code to be edited in Part B **
        public void mousePressed(MouseEvent e) {
            // Check the current mode
            // * If "pencil" mode, we should mark the current
            //   pixel as painted
            if (mode.equals("Pencil")) {
                // Check that mouse is in bounds of panel
                if (e.getX() >= 0 && e.getX() < WIDTH &&
                    e.getY() >= 0 && e.getY() < HEIGHT) {
                    // Set current pixel as painted
                    isPainted[e.getX()][e.getY()] = true;
                    colors[e.getX()][e.getY()] = color;
                }
            }

            if (mode.equals("Eraser")) {
                if (e.getX() >= 0 && e.getX() < WIDTH && e.getY() >= 0 && e.getY() < HEIGHT) {
                    for (int r = e.getX() - 5; r < e.getX() + 5; r++) {
                        for (int c = e.getY() - 5; c < e.getY() + 5; c++) {
                            isPainted[r][c] = false;
                        }
                    }
                }
            }

            if (mode.equals("Spray")){
                if (e.getX() >= 0 && e.getX() < WIDTH && e.getY() >= 0 && e.getY() < HEIGHT) {
                    for (int r = e.getX() - 5; r < e.getX() + 5; r++) {
                        for (int c = e.getY() - 5; c < e.getY() + 5; c++) {
                            if (Math.random() < 0.2){
                                isPainted[r][c] = true;
                                colors[r][c] = color;
                        }
                            else{
                                isPainted[r][c] = false;
                            }
                        }
                    }
                }
            }

            if (mode.equals("Pick")){
                color = colors[e.getX()][e.getY()];
            }

            if (mode.equals("Marker")){
                if (e.getX() >= 0 && e.getX() < WIDTH && e.getY() >= 0 && e.getY() < HEIGHT) {
                    for (int r = e.getX() - 5; r < e.getX() + 5; r++) {
                        for (int c = e.getY() - 5; c < e.getY() + 5; c++) {
                            isPainted[r][c] = true;
                            colors[r][c] = color;
                        }
                    }
                }
            }

            repaint();
        }

        // This is a MouseMotionListener method
        // We have this method so that we don't need to click each
        // pixel that we want to draw
        // ** You should never need to edit this code **
        public void mouseDragged(MouseEvent e) {
            mousePressed(e);
        }

        // The remaining MouseListener and MouseMotionLister
        // methods are left blank
        // ** You should never need to edit this code **
        public void mouseReleased(MouseEvent e) {
            // This method is intentionally blank
        }

        // ** You should never need to edit this code **
        public void mouseEntered(MouseEvent e) {
            // This method is intentionally blank
        }

        // ** You should never need to edit this code **
        public void mouseExited(MouseEvent e) {
            // This method is intentionally blank
        }

        // ** You should never need to edit this code **
        public void mouseClicked(MouseEvent e) {
            // This method is intentionally blank
        }

        // ** You should never need to edit this code **
        public void mouseMoved(MouseEvent e) {
            // This method is intentionally blank
        }
    }
}
