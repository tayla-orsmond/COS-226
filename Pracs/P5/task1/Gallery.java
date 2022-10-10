//Tayla Orsmond u21467456
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gallery {
    //The list-based set of GalleryNodes that make up the Gallery
    private AtomicReference<GalleryNode<String>> head;
    private Lock lock = new ReentrantLock();

    public Gallery(){
        this.head = new AtomicReference<>(null);
    }
    public void add(GalleryNode<String> newNode){
        //Add a node to the gallery in the correct position
        lock.lock();
        try{
            GalleryNode<String> curr = head.get();
            GalleryNode<String> prev = null;
            while(curr != null && curr.key < newNode.key){
                prev = curr;
                curr = curr.next;
            }
            if(prev == null){
                head.set(newNode);
            }
            else{
                prev.next = newNode;
            }
            newNode.next = curr;
            System.out.println("Thread-" + Thread.currentThread().getName() + ": added " + newNode.value + ", " + newNode.timeLeft.get() + "ms");
        }
        finally{
            lock.unlock();
        }
    }
    public void remove(GalleryNode<String> node){
        //Remove a node from the gallery
        lock.lock();
        printQueue();
        try{
            GalleryNode<String> curr = head.get();
            GalleryNode<String> prev = null;
            while(curr != null && curr.key != node.key){
                prev = curr;
                curr = curr.next;
            }
            if(prev == null){
                head.set(curr.next);
            }
            else{
                prev.next = curr.next;
            }
            System.out.println("Thread-" + Thread.currentThread().getName() + ": removed " + node.value + ", " + node.timeLeft.get() + "ms");
        }
        finally{
            lock.unlock();
        }
    }
    public void printQueue(){
        //Print the gallery
        //Print the calling thread's id
        System.out.print("\nThread-" + Thread.currentThread().getName() + ": ");
        //Print the gallery
        GalleryNode<String> curr = head.get();
        while(curr != null){
            System.out.print("[" + curr.value + ", "+ curr.timeLeft.get() + " ms]");
            if(curr.next != null){
                System.out.print(" -> ");
            }
            curr = curr.next;
        }
        System.out.println();
    }
}
