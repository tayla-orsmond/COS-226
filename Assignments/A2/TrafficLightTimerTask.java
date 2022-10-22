import java.util.TimerTask;
//Tayla Orsmond u21467456

/**
 * @brief The TrafficLightTimerTask class.
 * @details This class is used to create a timer task that can be used to change the state of a traffic light.
 * @author Tayla Orsmond
 * @date 2022-10-22
 */

public class TrafficLightTimerTask extends TimerTask {
    private TrafficLight trafficLight;

    /**
     * @brief Constructor for the TrafficLightTimerTask class.
     * @details This constructor creates a new TrafficLightTimerTask object.
     * It sets the traffic light and the state.
     *
     * @param trafficLight The traffic light that the timer task will be used for.
     */
    public TrafficLightTimerTask(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
        
    }

    /**
     * @brief This method is used to run the timer task.
     * @details This method is used to run the timer task.
     * It will change the state of the traffic light to the state that was set in the constructor.
     */
    @Override
    public void run() {
        trafficLight.changeState();
    }
}
