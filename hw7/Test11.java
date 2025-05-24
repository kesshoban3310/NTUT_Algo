public class Test11 {

    public static boolean TestSuccess() {
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
        assert(s0!=null) : "\n the State constructor returns a null object";
        assert (!s0.success()) : "\nerror : success(s0) should be false, not true";
        State s1 = new State(RH, new int[] { 4, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 });
        assert (s1.success()) : "\nerror : success(s1) should be true and not false";

        State s = new State(s0, 11, 1);
		assert(s.prev == s0) : "\n the prev field is incorrectly initialized";
		assert(s.pos[11]-s0.pos[11]==1) : "\n the value of pos is incorrectly set after calling State(s0, 11, 1)";
		s = new State(s, 3, 1); s = new State(s, 11, -1); s = new State(s, 3, -1);
        
        boolean test = true;
        for (int i=0;i<nbCars;i++){
            if (s.pos[i]!= s0.pos[i])
                test = false;
        }
        assert(test) : "\n State(s,c,d) does not work properly";
        
        int[] movingCars = {11, 9, 8, 6, 3, 0, 1, 2, 2, 1, 1, 0, 3, 2, 4, 7, 10, 11, 10, 9, 1, 7, 7, 4, 4, 5, 10, 10, 5, 5, 4, 4, 7, 7, 7, 6, 4, 4, 1, 11, 11, 11, 4, 0, 0, 0};
		int[] moves = {1, 1, -1, 1, -1, -1, 1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, 1, -1, 1, 1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, -1, 1, 1, -1, -1, -1, -1, 1, 1, 1, 1};
		for(int i = 0; i < movingCars.length; i++) {
			s = new State(s, movingCars[i], moves[i]);
		}
		assert(s.success()):"\n State(s,c,d) does not work properly";
        return true;

    }

    public static void main(String[] args) {

        // For the assert's to be activated
        if (!Test11.class.desiredAssertionStatus()) {
            System.err.println("You must pass the option -ea to the virtual machine Java.");
            System.exit(1);
        }

        System.out.println("Question 1.1");
        System.out.print("Testing the constructor «State» and the method «success»");
        TestSuccess();
        System.out.println("[OK]");
    }

}
