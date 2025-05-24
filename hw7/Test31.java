
public class Test31 {

    public static boolean TestDFS() {
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
        State s = RH.solveDFS(s0);
        assert(s.success()) : "\n The returned state does not correspond to a winning configuration";
        State t = s;
        while(s != null) {
            t=s;
            s = s.prev;
        }
        assert(t.equals(s0)) : "\n The returned state does not start from s0 (or the prev fields were incorrectly instantiated))";
        return true;
    }

    

    public static void main(String[] args) {

        // For the assert's to be activated
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("You must pass the option -ea to the virtual machine Java.");
            System.exit(1);
        }

        System.out.println("Question 3.1");
        System.out.print("Testing the method solveDFS : ");
        TestDFS();
        System.out.println("[OK]");
    }

}


