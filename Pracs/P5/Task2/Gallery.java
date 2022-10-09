//Tayla Orsmond u21467456
import java.util.concurrent.atomic.AtomicReference;

public class Gallery {
    //The list-based set of GalleryNodes that make up the Gallery
    private AtomicReference<GalleryNode<String>> head;

    public Gallery(){
        this.head = new AtomicReference<>(new GalleryNode<String>(999, "HEAD", 0));
    }
    public void add(GalleryNode<String> newNode){
        //Add a node to the gallery using fine-grained synchronization
        head.get().lock();
        GalleryNode<String> curr = head.get();
        try{
            while(curr.next != null){
                GalleryNode<String> next = curr.next;
                next.lock();
                curr.unlock();
                if(curr.next != null){
                    curr = next;
                }               
            }
            curr.next = newNode;
            System.out.println("Thread-" + Thread.currentThread().getName() + ": added " + newNode.value + ", " + newNode.timeLeft.get() + "ms");
        }
        finally{
            curr.unlock();
        }
    }
    public void remove(GalleryNode<String> node){
        //Remove a node from the gallery
        //print the nodes out as they are traversed
        printQueue();
        GalleryNode<String> pred = null, curr = null;
        head.get().lock();
        try {
            pred = head.get();
            curr = pred.next;
            curr.lock();
            try {
                while(curr != null && curr.key != node.key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    if(curr != null){
                        curr.lock();
                    }
                }
                if(curr != null && curr.key == node.key) {
                    pred.next = curr.next;
                }
            } finally {
                if(curr != null){
                    curr.unlock();
                }
            }
        } finally {
            if(pred != null){
                pred.unlock();
            }
        }
        System.out.println("(Thread-" + Thread.currentThread().getName() + ": removed " + node.value + ", " + node.timeLeft.get() + "ms)");
    }
    public void printQueue(){
        //Print the gallery
        //Print the calling thread's id
        head.get().lock();
        GalleryNode<String> curr = head.get();
        System.out.print("\nThread-" + Thread.currentThread().getName() + ": ");
        try{
            while(curr.next != null){
                GalleryNode<String> next = curr.next;
                next.lock();
                System.out.print("T-" + Thread.currentThread().getName() + ":[" + curr.value + ", "+ curr.timeLeft.get() + " ms]");
                if(curr.next != null){
                    System.out.print(" -> ");
                }
                else{
                    System.out.print("\n");
                }
                curr.unlock();
                if(curr.next != null){
                    curr = next;
                }               
            }
            if(curr != null){
                System.out.print("T-" + Thread.currentThread().getName() + ":[" + curr.value + ", "+ curr.timeLeft.get() + " ms]\n");
            }
        }
        finally{
            curr.unlock();
        }
    }
}
