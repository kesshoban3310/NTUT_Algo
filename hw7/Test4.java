public class Test4{

    public static boolean TestPrintSolution() {
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
        State s0 = new State(RH, pos);
        State s = RH.solveBFS(s0);
        RH.printSolution(s);
        return true;
    }

    

    public static void main(String[] args) {

        // For the assert's to be activated
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("You must pass the option -ea to the virtual machine Java.");            
            System.exit(1);
        }

        System.out.println("Question 4");
        System.out.print("Testing the method printSolution : ");
        TestPrintSolution();
        // System.out.println("[OK]");

        

    }

}

