import java.util.concurrent.atomic.AtomicLong;

//Tayla Orsmond u21467456
public class GalleryNode<T> {
    //The nodes in the gallery
    public final int key;
    public final T value;
    public GalleryNode<T> next;
    private static final int min = 100, max = 1000;
    public long timeEntered;
    public long timeInGallery;
    public AtomicLong timeLeft;

    public GalleryNode(int key, T value){
        this.key = key;
        this.value = value;
        this.next = null;
        this.timeInGallery = (long) (Math.random() * (max - min) + min);
        this.timeLeft = new AtomicLong(timeInGallery);
    }

    public void spendTime(){
        //spend time in the gallery
        while(System.currentTimeMillis() - timeEntered < timeInGallery){
            timeLeft.set(timeInGallery - (System.currentTimeMillis() - timeEntered));
        }
        timeLeft.set(0);
    }
}
