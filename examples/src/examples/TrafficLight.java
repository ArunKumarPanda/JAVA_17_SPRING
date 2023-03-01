package examples;

public sealed interface TrafficLight{}

record RedLight() implements TrafficLight{}
record GreenLight() implements TrafficLight{}
record YellowLight() implements TrafficLight{}