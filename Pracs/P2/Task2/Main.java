public class Main {
    // Name: Tayla Orsmond
    // Student Number: u21467456
    public static void main(String[] args) {
        int MAX = 5;
	    Transport[] buses = new Transport[MAX];
        Venue destination = new Venue(MAX);

        for(int i = 0; i < MAX; i++)
            buses[i] = new Transport(destination, i);

        for(Transport bus : buses)
            bus.start();

    }
}
