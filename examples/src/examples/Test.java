package examples;


public class Test {

	public static void main(String[] args) {

String data = """
	{
		"name": "Linda",
		"age": 24
	}
""";
System.out.println(data);
		//System.out.println(getValueYield("b"));
	}
	
	public static int getValue(String mode) {
		int result = -1;
		switch(mode) {
			case "a": result = 1; break;
			case "b": result = 2; break;
			default: result = 3;
		}
		
		return result;
	}
	
	// java 12 Switch expression
	public static int getValueArrow(String mode) {
		int result = switch(mode) {
			case "a", "b" -> 1;
			case "c"-> 2;
			default -> 3;
		};
		
		return result;
	}

	// java 13 switch expression ==> yield to return a value
		public static int getValueYield(String mode) {
			int result = switch(mode) {
				case "a", "b" -> {
					System.out.println("Case a and b");
					yield 1;
				}
				case "c"-> 2;
				default -> 3;
			};
			
			return result;
		}
}
