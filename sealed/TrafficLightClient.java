public class TrafficLightClient {
    public static void main(String[] args) {
        TrafficLight light = new YellowLight();
        switch(light) {
            case RedLight r -> System.out.println("Wait!!");
            case YellowLight y -> System.out.println("Ready!!!");
            case GreenLight g -> System.out.println("Go!!!");
        }
    }
}

// javac --source 17 --enable-preview -Xlint:preview *.java
// java --enable-preview TrafficLightClient