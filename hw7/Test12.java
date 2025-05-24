import javax.swing.SwingUtilities;

public class Test12 {

    public static boolean TestEquals() {
        int nbCars = 12;
        String[] color = new String[] { "red", "pale green", "yellow", "orange",
                "pale violet", "sky blue", "pink", "violet", "green", "black",
                "beige", "blue" };
        boolean[] horiz = new boolean[] { true, false, true, false, false, true, false,
                true, false, true, false, true };
        int[] len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
        int[] moveOn = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
        RushHour RH = new RushHour(nbCars, color, horiz, len, moveOn);

        State s0 = new State(RH, new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 });
        State s1 = new State(RH, new int[] { 4, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 });
        assert (!s1.equals(s0)) : "\nerror : equals is not well implemented; you should only compare the pos field and not the prev";

        State s = new State(s0, 11, 1);
		s = new State(s, 3, 1); s = new State(s, 11, -1); s = new State(s, 3, -1);
		assert(s.equals(s0)) : "\nerror : equals is not well implemented; you should only compare the pos field and not the prev";
        return true;
    }

    public static boolean TestFree(){
        int nbCars = 12;
        String[] color = new String[] { "red", "pale green", "yellow", "orange",
                "pale violet", "sky blue", "pink", "violet", "green", "black",
                "beige", "blue" };
        boolean[] horiz = new boolean[] { true, false, true, false, false, true, false,
                true, false, true, false, true };
        int[] len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
        int[] moveOn = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
        RushHour RH = new RushHour(nbCars, color, horiz, len, moveOn);
        int[] pos = new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 };
         
        // lanch the GUI for debugging
        SwingUtilities.invokeLater(() -> { 
            new RushHourGUI(RH, pos);
        });

        State s0 = new State(RH, pos);
        boolean[][] free = s0.free();
        assert(free.length==6): "The result of free() is not of size 6*6";
        assert(free[0].length==6): "The result of free() is not a two-dimensional array of size 6*6";
        boolean[][] freeTest = {
            {true, true, false, false, false, false},
            {false, true, false, false, false, false},
            {false, false, false, false, true, true},
            {true, false, true, false, false, false},
            {false, false, false, false, true, false},
            {false, false, false, false, true, false}
        };

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++)
				assert(free[i][j] == freeTest[i][j]) : "\n free["+i+"]["+j+"] should return "+freeTest[i][j]+" but return "+free[i][j];
		}

        return true;
    }

    public static void main(String[] args) {

        // For the assert's to be activated
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("You must pass the option -ea to the virtual machine Java.");
            System.exit(1);
        }

        System.out.println("Question 1.2");
        System.out.print("Testing the method equals : ");
        TestEquals();
        System.out.println("[OK]");
        System.out.print("Testing the method free : ");
        TestFree();
        System.out.println("[OK]");
    }

}

