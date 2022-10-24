import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//Tayla Orsmond u21467456

/**
 * @brief The TrafficLight class.
 * @details This class is used to create a traffic light lock that can be used to control the flow of traffic in an intersection.
 * This class is an example of a monitor.
 * It has a ReentrantLock as its lock.
 * It has a Condition object to represent the green light.
 * It has states, which are represented by the enum TrafficLightState.
 *  The states are: RED, YELLOW, GREEN.
 * A thread can only acquire the lock if the state is GREEN.
 *  The state can be changed by calling the changeState() method, a state has a time limit for how long the light can be in that state.
 *  The state can be checked by calling the getState() method.
 *
 * @author Tayla Orsmond
 * @date 2022-10-22
 */
enum TrafficLightState {
    RED, YELLOW, GREEN
}

public class TrafficLight {
    private ReentrantLock lock;
    private volatile TrafficLightState state;
    private Timer timer;
    private long redTime, yellowTime, greenTime;

    /**
     * @brief Constructor for the TrafficLight class.
     * @details This constructor creates a new TrafficLight object.
     * It sets the state to RED, and creates a new ReentrantLock object.
     * It also creates a new Timer object.
     * It also sets the time for each state.
     *
     * @param redTime The time the light will be red for.
     * @param yellowTime The time the light will be yellow for.
     * @param greenTime The time the light will be green for.
     */
    public TrafficLight(long redTime, long yellowTime, long greenTime) {
        this.state = TrafficLightState.RED;
        this.lock = new ReentrantLock();
        this.timer = new Timer();
        this.redTime = redTime;
        this.yellowTime = yellowTime;
        this.greenTime = greenTime;
    }

    /**
     * @brief This method is used to acquire the lock.
     * @details This method is used to acquire the lock.
     * It will only acquire the lock if the state is GREEN.
     * If the state is not GREEN, it will wait until the state is GREEN.
     *
     * @throws InterruptedException
     */
    public void acquire() throws InterruptedException {
        while (state != TrafficLightState.GREEN) {}
        lock.lock();
    }

    /**
     * @brief This method is used to release the lock.
     * @details This method is used to release the lock.
     */
    public void release() {
        lock.unlock();
    }

    /**
     * @brief This method is used to change the state of the traffic light.
     * @details This method is used to change the state of the traffic light.
     * It will change the state to the next state in the enum.
     * It will also start a timer for the state.
     */
    public void changeState() {
        switch (state) {
            case RED:
                state = TrafficLightState.YELLOW;
                timer.schedule(new TrafficLightTimerTask(this), redTime);
                break;
            case YELLOW:
                state = TrafficLightState.GREEN;
                timer.schedule(new TrafficLightTimerTask(this), yellowTime);
                break;
            case GREEN:
                state = TrafficLightState.RED;
                timer.schedule(new TrafficLightTimerTask(this), greenTime);
                break;
        }
    }

    /**
     * @brief This method is used to get the state of the traffic light.
     * @details This method is used to get the state of the traffic light.
     *
     * @return The state of the traffic light.
     */
    public TrafficLightState getState() {
        return state;
    } 
    
    /**
     * @brief This method is used to stop the timer.
     * @details This method is used to stop the timer.
     */
    public void stopTimer() {
        timer.cancel();
    }

    /**
     * @brief This method is used to see if the timer is running.
     */
    public boolean isTimerRunning() {
        return timer.purge() > 0;
    }
}
