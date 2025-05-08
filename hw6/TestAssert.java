
public class TestAssert {

	public static void main(String[] args) {
		//To ensure that asserts are enabled
		if (!TestAssert.class.desiredAssertionStatus()) {
	        System.err.println("You must pass the -ea option to the Java Virtual Machine.");
	        System.exit(1);
	      }
		System.out.println("Asserts are active.");
		
	}

}
