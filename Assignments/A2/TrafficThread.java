import java.util.concurrent.atomic.AtomicInteger;

//Tayla Orsmond u21467456

/**
 * @brief The TrafficThread class.
 * @details This class is used to create a thread that simulates the flow of traffic in an intersection.
 * It has an Intersection object.
 * It has a boolean direction (telling it whether it is moving from the previous queue to the next queue, or vice versa).
 * @param maxRuns The maximum number of times the thread will run (move cars between the queues) - static
 * 
 * @author Tayla Orsmond
 * @date 2022-10-22
 */
public class TrafficThread extends Thread {
    private Intersection intersection;
    private boolean direction;
    public static AtomicInteger maxRuns = new AtomicInteger(0);

    /**
     * @brief Constructor for the TrafficThread class.
     * @details This constructor creates a new TrafficThread object.
     * It sets the intersection and the direction.
     * 
     * @param intersection The intersection that the thread will be used for.
     * @param direction The direction that the thread will be moving cars in.
     * @param maxRuns The maximum number of times the thread will run (move cars between the queues).
     */
    public TrafficThread(Intersection intersection, boolean direction) {
        this.intersection = intersection;
        this.direction = direction;
    }

    /**
     * @brief The run method.
     * @details This method is used to run the thread.
     * It will move cars between the queues for a maximum of maxRuns times.
     */
    @Override
    public void run() {
        while(maxRuns.get() > 0) {
            if (direction) {
                intersection.moveNext();
            } else {
                intersection.movePrevious();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
