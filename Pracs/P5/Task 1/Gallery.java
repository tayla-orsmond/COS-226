//Tayla Orsmond u21467456
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gallery {
    //The list-based set of GalleryNodes that make up the Gallery
    private AtomicReference<GalleryNode<String>> head;
    private Lock lock = new ReentrantLock();
    private int numNodes = 0;

    public Gallery(){
        this.head = new AtomicReference<>(null);
    }
    public void add(GalleryNode<String> newNode){
        //Add a node to the gallery
        lock.lock();
        try{
            GalleryNode<String> current = head.get();
            if(current == null){
                head.set(newNode);
                numNodes++;
            }else{
                while(current.next != null){
                    current = current.next;
                }
                current.next = newNode;
                numNodes++;
            }
            newNode.timeEntered = System.currentTimeMillis();
            System.out.println("Thread-" + Thread.currentThread().getName() + ": added " + newNode.value + ", " + newNode.timeLeft.get() + "ms");
        }finally{
            lock.unlock();
        }
    }
    public void remove(GalleryNode<String> node){
        //Remove a node from the gallery
        lock.lock();
        try{
            printQueue();
            GalleryNode<String> current = head.get();
            GalleryNode<String> previous = null;
            while(current != null){
                if(current.key == node.key){
                    if(previous == null){
                        head.set(current.next);
                    }else{
                        previous.next = current.next;
                    }
                    numNodes--;
                    break;
                }
                previous = current;
                current = current.next;
            }
            //System.out.println("(Thread-" + Thread.currentThread().getName() + ": removed " + node.value + ", " + node.timeLeft.get() + "ms)");
        }finally{
            lock.unlock();
        }
    }
    public boolean contains(int key){
        //Check if the gallery contains a node with the given key
        lock.lock();
        boolean isFound = false;
        try{
            GalleryNode<String> current = head.get();
            while(current != null){
                if(current.key == key){
                    isFound = true;
                    break;
                }
                current = current.next;
            }
        }finally{
            lock.unlock();
        }
        return isFound;
    }
    public int size(){
        //Return the number of nodes in the gallery
        return numNodes;
    }
    public void printQueue(){
        //Print the gallery
        //Print the calling thread's id
        System.out.print("\nThread-" + Thread.currentThread().getName() + ": ");
        GalleryNode<String> current = head.get();
        while(current != null){
            System.out.print("[" + current.value + ", "+ current.timeLeft.get() + " ms]");
            if(current.next != null){
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
}
