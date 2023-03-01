//sealed in single file --> no need for permits
public sealed interface TrafficLight {}

record RedLight() implements TrafficLight{}
record GreenLight() implements TrafficLight{}
record YellowLight() implements TrafficLight{}