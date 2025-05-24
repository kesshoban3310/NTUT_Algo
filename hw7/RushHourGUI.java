import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RushHourGUI extends JFrame {
    private RushHour RH;
    private int[] pos;

    public RushHourGUI(RushHour RH, int[] pos) {
        this.RH = RH;
        this.pos = pos;     
        setTitle("Rush Hour Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeGrid();
        setLayout(new GridLayout(6, 6,10,10));
        setVisible(true);
    }

    public Color getColorFromString(String colorName) {
        switch (colorName.toLowerCase()) {
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "pale green":
                return new Color(0, 255, 0, 128);
            case "blue":
                return Color.BLUE;
            case "sky blue":
                return new Color(0, 0,255, 128);
            case "yellow":
                return Color.YELLOW;
            case "black":
                return Color.BLACK;
            case "white":
                return Color.WHITE;
            case "orange":
                return Color.ORANGE;
            case "violet":
                return new Color(128, 0, 128);
            case "beige":
                return new Color(232, 220, 222);
            case "pink":
                return new Color(255, 192, 203);
            // add another colors as needed
            default:
                return Color.GRAY; // default color
        }
    }

    private void initializeGrid() {
        System.out.println("the drawn grid represents ");
        System.out.println("nbCars =" + RH.nbCars);
        System.out.println("color =" + Arrays.toString(RH.color));
        System.out.println("horiz =" + Arrays.toString(RH.horiz));
        System.out.println("len =" + Arrays.toString(RH.len));
        System.out.println("moveOn =" + Arrays.toString(RH.moveOn));


        int cellSize = 100;
    
        // Create a 6x6 grid
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
              
                boolean carPresent = false;
                for (int i = 0; i < RH.nbCars; i++) {
                    if ((RH.horiz[i] && RH.moveOn[i] == row && pos[i] <= col && col < pos[i] + RH.len[i]) ||
                            (!RH.horiz[i] && pos[i] <= row && row < pos[i] + RH.len[i] && RH.moveOn[i] == col)) {
                        carPresent = true;
                        JLabel label = new JLabel(" "+i +" ", SwingConstants.CENTER);
                        // label.setPreferredSize(new Dimension(cellSize - 50, cellSize - 50)); // add
                        label.setOpaque(true);
                        label.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
                        label.setBackground(getColorFromString(RH.color[i])); // use i directly
                        add(label);
                        ;
                    }
                }

                if (!carPresent) {
                    add(new JLabel("  ", SwingConstants.CENTER) {
                        {
                            setOpaque(true);
                            setBackground(Color.WHITE);
                            setPreferredSize(new Dimension(cellSize, cellSize)); // Adjustement
                        }
                    });

                }
            }
        }

    }

    public void update(int[] newPos) {
        this.pos = newPos; 
        repaint(); 
        
    }
   
    public void animateMove(int carIndex, int direction) {
        System.out.println("coucou");
        Timer timer = new Timer(100, new ActionListener() {
            int steps = 0; // compute the number of steps

            @Override
            public void actionPerformed(ActionEvent e) {
                if (steps < 10) { // Move in 10 steps
                    if (RH.horiz[carIndex]) {
                        pos[carIndex] += direction == 1 ? 0.1 : -0.1; // Progressive movement
                    } else {
                        pos[carIndex] += direction == 1 ? 0.1 : -0.1; // Progressive movement
                    }
                    repaint(); // Redraw the interface
                    steps++;
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer
                    pos[carIndex] += (direction == 1) ? 1 : -1; // Updates the final position
                    repaint(); // Redraw the interface one last time
                }
            }
        });
        timer.start(); // Start the timer
    }

    // @Override
    // public void paint(Graphics g) {
    //     super.paint(g);
    //     initializeGrid(); // Redraw the grid
    // }


    // public static void main(String[] args) {
    //     int nbCars = 12;
    //     String[] color = new String[] { "red", "pale green", "yellow", "orange",
    //             "pale violet", "sky blue", "pink", "violet", "green", "black",
    //             "beige", "blue" };
    //     boolean[] horiz = new boolean[] { true, false, true, false, false, true, false,
    //             true, false, true, false, true };
    //     int[] len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
    //     int[] moveOn = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
    //     RushHour RH = new RushHour(nbCars, color, horiz, len, moveOn);
    //     int[] pos = new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 };

    //     // Launch the GUI
    //     SwingUtilities.invokeLater(() -> {
    //         RushHourGUI rushHourGUI = new RushHourGUI(RH, pos);
    //     });
    // }
}
