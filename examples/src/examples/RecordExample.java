package examples;

import java.util.Comparator;
import java.util.stream.Stream;

public class RecordExample {

	public static void main(String[] args) {
		System.out.println(longest(Stream.of("Harry","Li","Charles","Angelina","Uma")));
	}
	
	private static String longest(Stream<String> stream) {
		record StrLen(String s, int l) {}
		return stream.map(s -> new StrLen(s, s.length()))
				.max(Comparator.comparing(StrLen::l))
				.map(strlen -> strlen.s)
				.orElse(null);
	}

}
