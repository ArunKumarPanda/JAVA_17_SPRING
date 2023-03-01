import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;

// Description
// -----------
//
// The sample shows how an event can be used to measure the time of an operation
// by invoking the begin and end method. 
//
// How to run
// ----------
//
// $ java -XX:StartFlightRecording:filename=02.jfr Measure_Time.java 
// 
//
//
public class Measure_Time {
	
	@Name("com.sample.Duration")
	@Label("Duration")
	static class DurationEvent extends Event {
	}

	public static void main(String... args) throws Exception {
		DurationEvent event = new DurationEvent();
		event.begin();
		Thread.sleep(42);
		event.commit();
	}
}