import java.util.NoSuchElementException;
import java.util.Queue;

//Tayla Orsmond u21467456

/**
 * @brief The Intersection class.
 * @details This class is used to create an intersection that can be used to control the flow of traffic.
 * This class uses a TrafficLight object as its lock
 * It has a reference to a "previous" queue of cars, and a "next" queue of cars.
 * A Thread is responsible for moving cars between the queues.
 * The thread will only move cars if the lock is acquired (light is green)
 * 
 * @author Tayla Orsmond
 * @date 2022-10-22
 */

public class Intersection {
    private String name;
    private TrafficLight trafficLight;
    private Queue<Car> previousQueue;
    private Queue<Car> nextQueue;
    private int maxQueueSize_next;
    private int maxQueueSize_previous;

    /**
     * @brief Constructor for the Intersection class.
     * @details This constructor creates a new Intersection object.
     * It sets the traffic light, the previous queue, the next queue, and the max queue sizes.
     * 
     * @param name The name of the intersection.
     * @param trafficLight The traffic light that will be used to control the flow of traffic.
     * @param previousQueue The queue of cars that will be coming from the previous intersection.
     * @param nextQueue The queue of cars that will be going to the next intersection.
     * @param maxQueueSize_next The maximum size of the next queue.
     * @param maxQueueSize_previous The maximum size of the previous queue.
     */
    public Intersection(String name, TrafficLight trafficLight, Queue<Car> previousQueue, Queue<Car> nextQueue, int maxQueueSize_previous,  int maxQueueSize_next) {
        this.name = name;
        this.trafficLight = trafficLight;
        this.previousQueue = previousQueue;
        this.nextQueue = nextQueue;
        this.maxQueueSize_next = maxQueueSize_next;
        this.maxQueueSize_previous = maxQueueSize_previous;
    }
    /**
     * @brief The moveNext method.
     * @details This method is used to move cars from the previous queue to the next queue.
     * It will only move cars if it can acquire the lock (the light is green), and only for as long as the light stays green
     * It will only move cars if (while) the next queue is not full.
     * It will only move cars if (while) the previous queue is not empty.
     */
    public void moveNext() {
        while (true) {
            try {
                trafficLight.acquire();
                while (nextQueue.size() < maxQueueSize_next && !previousQueue.isEmpty() && trafficLight.getState() == TrafficLightState.GREEN) {
                    Car car = previousQueue.remove();
                    if(car != null && !car.hasArrived()) {
                        nextQueue.add(car.drive());
                        TrafficThread.maxRuns.decrementAndGet();
                        synchronized(System.out){
                            System.out.println("Car " + car.getName() + " passed through intersection: \u001b[34m" + name + "\u001b[0m" + previousQueue.toString() + " -> " + nextQueue.toString());
                        }
                    }
                    
                }
                trafficLight.release();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch (NoSuchElementException e) {
                trafficLight.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @brief The movePrevious method.
     * @details This method is used to move cars from the next queue to the previous queue.
     * It will only move cars if it can acquire the lock (the light is green), and only for as long as the light stays green
     * It will only move cars if (while) the previous queue is not full.
     * It will only move cars if (while) the next queue is not empty.
     */
    public void movePrevious() {
        while (true) {
            try {
                trafficLight.acquire();
                while (previousQueue.size() < maxQueueSize_previous && !nextQueue.isEmpty() && trafficLight.getState() == TrafficLightState.GREEN) {
                    Car car = nextQueue.remove();
                    if(car != null && !car.hasArrived()) {
                        previousQueue.add(car.drive());
                        TrafficThread.maxRuns.decrementAndGet();
                        synchronized(System.out){
                            System.out.println("Car " + car.getName() + " passed through intersection: \u001b[34m" + name + "\u001b[0m" + nextQueue.toString() + " -> " + previousQueue.toString());
                        }
                    }
                }
                trafficLight.release();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }   
    
    /**
     * @brief The getTrafficLight method.
     * @details This method is used to get the traffic light of the intersection.
     * 
     * @return The traffic light of the intersection.
     */
    public TrafficLight getTrafficLight() {
        return trafficLight;
    }
}
