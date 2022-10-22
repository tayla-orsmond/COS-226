import java.util.concurrent.atomic.AtomicInteger;

//Tayla Orsmond u21467456

/**
 * @brief The Car class.
 * @details This class is used to create a car object.
 * It has a String name.
 * A car has a route (the number of intersections it must pass through).
 * A car has a count (the number of intersections it has passed through).
 * 
 * @author Tayla Orsmond
 * @date 2022-10-22
 */
public class Car {
    private String name;
    private int route;
    private AtomicInteger count;

    /**
     * @brief Constructor for the Car class.
     * @details This constructor creates a new Car object.
     * It sets the name, route and count.
     * 
     * @param name The name of the car.
     * @param route The number of intersections the car must pass through.
     */
    public Car(String name, int route) {
        this.name = name;
        this.route = route;
        this.count = new AtomicInteger(0);
    }

    /**
     * @brief This method is used to get the name of the car.
     * @details This method is used to get the name of the car.
     * 
     * @return The name of the car.
     */
    public String getName() {
        return name;
    }

    /**
     * @brief This method is used to get the route of the car.
     * @details This method is used to get the route of the car.
     * 
     * @return The route of the car.
     */
    public int getRoute() {
        return route;
    }

    /**
     * @brief This method is used to "drive" the car.
     * @details This method is used to increment the count of the car.
     * (i.e., simulate it moving through the intersection)
     * 
     * @return The car.
     */
    public Car drive() {
        count.incrementAndGet();
        return this;
    }

    /**
     * @brief This method is used to get the completed boolean of the car.
     * @details This method is used to get the completed boolean of the car.
     * 
     * @return If the car has completed its route (count == route)
     */
    public boolean hasArrived() {
        return count.get() == route;
    }

    /**
     * @brief This method is used to get the string representation of the car.
     * @details This method is used to get the string representation of the car.
     * 
     * @return The string representation of the car.
     */
    @Override
    public String toString() {
        return "\u001b[35mCar " + name + "\u001b[0m";
    }

    /**
     * @brief This method is used to get the string representation of the car.
     * @details This method is used to get the string representation of the car.
     * 
     * @return The string representation of the car.
     */
    public String toString2() {
        return "\u001b[35mCar " + name + "\u001b[0m[" + "route: " + route + " count: " + count + " completed: " + hasArrived() + "]";
    }
}
