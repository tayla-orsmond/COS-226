import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) {
        //params
        long RED_TIME = 2000;
        long YELLOW_TIME = 1000;
        long GREEN_TIME = 3000;
        int NUM_CARS = 8;
        int NUM_THREADS = 8;
        int Q1_MAX = 5;
        int Q2_MAX = 5;
        int Q3_MAX = 5;
        int Q4_MAX = 5;
        int MAX_RUNS = 0;
        
        //create traffic light x 4
        TrafficLight trafficLight1 = new TrafficLight(RED_TIME, YELLOW_TIME, GREEN_TIME);
        TrafficLight trafficLight2 = new TrafficLight(RED_TIME, YELLOW_TIME, GREEN_TIME);
        TrafficLight trafficLight3 = new TrafficLight(RED_TIME, YELLOW_TIME, GREEN_TIME);
        TrafficLight trafficLight4 = new TrafficLight(RED_TIME, YELLOW_TIME, GREEN_TIME);

        //create the queues x 4
        BlockingDeque<Car> queue1 = new LinkedBlockingDeque<>();
        BlockingDeque<Car> queue2 = new LinkedBlockingDeque<>();
        BlockingDeque<Car> queue3 = new LinkedBlockingDeque<>();
        BlockingDeque<Car> queue4 = new LinkedBlockingDeque<>();

        //create the intersections x 4
        Intersection intersection1 = new Intersection("1", trafficLight1, queue1, queue2, Q1_MAX, Q2_MAX);
        Intersection intersection2 = new Intersection("2", trafficLight2, queue2, queue3, Q2_MAX, Q3_MAX);
        Intersection intersection3 = new Intersection("3", trafficLight3, queue3, queue4, Q3_MAX, Q4_MAX);
        Intersection intersection4 = new Intersection("4", trafficLight4, queue4, queue1, Q4_MAX, Q1_MAX);

        //create the cars x num_cars
        Car[] cars = new Car[NUM_CARS];
        for (int i = 0; i < NUM_CARS; i++) {
            //generate random int between 1 and 5
            int random = (int) (Math.random() * 5) + 1;
            MAX_RUNS += random;
            Car car = new Car("" + i, random);
            cars[i] = car;
            //choose a random queue to add the car to
            int randomQueue = (int) (Math.random() * 4) + 1;
            switch (randomQueue) {
                case 1:
                    queue1.add(car);
                    break;
                case 2:
                    queue2.add(car);
                    break;
                case 3:
                    queue3.add(car);
                    break;
                case 4:
                    queue4.add(car);
                    break;
            }
        }

        System.out.println("========== TRAFFIC SIM ==========");
        System.out.println("Config:");
        System.out.println("\tNumber of cars: " + NUM_CARS);
        System.out.println("\tNumber of threads: " + NUM_THREADS);
        System.out.println("\tMax runs: " + MAX_RUNS);
        System.out.println("\tQueue 1 max: " + Q1_MAX);
        System.out.println("\tQueue 2 max: " + Q2_MAX);
        System.out.println("\tQueue 3 max: " + Q3_MAX);
        System.out.println("\tQueue 4 max: " + Q4_MAX);
        System.out.println();
        System.out.println("The roads look like this:");
        //print out the roads
        System.out.println("Road 1: " + queue1.toString());
        System.out.println("Road 2: " + queue2.toString());
        System.out.println("Road 3: " + queue3.toString());
        System.out.println("Road 4: " + queue4.toString());
        System.out.println();

        //create the threads x 8 (two for each intersection)
        TrafficThread.maxRuns.set(MAX_RUNS);
        TrafficThread[] threads  = new TrafficThread[NUM_THREADS];
        threads[0] = new TrafficThread(intersection1, true);
        threads[1] = new TrafficThread(intersection1, false);
        threads[2] = new TrafficThread(intersection2, true);
        threads[3] = new TrafficThread(intersection2, false);
        threads[4] = new TrafficThread(intersection3, true);
        threads[5] = new TrafficThread(intersection3, false);
        threads[6] = new TrafficThread(intersection4, true);
        threads[7] = new TrafficThread(intersection4, false);

        //start the trafficlights
        trafficLight1.changeState();
        trafficLight2.changeState();
        trafficLight3.changeState();
        trafficLight4.changeState();

        //start the threads
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].start();
        }

        //join the threads
        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //after all threads are done, stop the trafficlight timer print out the final roads and the total number of runs
        trafficLight1.stopTimer();
        trafficLight2.stopTimer();
        trafficLight3.stopTimer();
        trafficLight4.stopTimer();
        System.out.println("========== END OF SIM ==========");
        System.out.println("Config:");
        System.out.println("\tMax runs left: " + TrafficThread.maxRuns.get());
        //print out the cars stats
        System.out.println("Car stats:");
        for (int i = 0; i < NUM_CARS; i++) {
            System.out.println(cars[i].toString2());
        }
        System.out.println("The roads look like this:");
        //print out the roads
        System.out.println("Road 1: " + queue1.toString());
        System.out.println("Road 2: " + queue2.toString());
        System.out.println("Road 3: " + queue3.toString());
        System.out.println("Road 4: " + queue4.toString());
    }
}
