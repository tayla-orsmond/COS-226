//Tayla Orsmond u21467456
import java.util.concurrent.atomic.AtomicReference;

public class Gallery {
    //The list-based set of GalleryNodes that make up the Gallery
    private AtomicReference<GalleryNode<String>> head;

    public Gallery(){
        this.head = new AtomicReference<>(new GalleryNode<String>(Integer.MIN_VALUE, "HEAD"));
        this.head.get().next = new GalleryNode<String>(Integer.MAX_VALUE, "TAIL");
    }
    public void add(GalleryNode<String> newNode){
        //Add a node to the gallery using fine-grained synchronization (locks on each node)
        head.get().lock();
        GalleryNode<String> prev = head.get();
        try {
            GalleryNode<String> curr = prev.next;
            curr.lock();
            try {
                while(curr.key < newNode.key) {
                    prev.unlock();
                    prev = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (curr.key == newNode.key) {
                    return;
                }
                newNode.next = curr;
                prev.next = newNode;
                System.out.println("Thread-" + Thread.currentThread().getName() + ": added " + newNode.value + ", " + newNode.timeLeft.get() + "ms");
                return;
            } finally {
                curr.unlock();
            }
        } finally {
            prev.unlock();
        }
    }
    public void remove(GalleryNode<String> node){
        //Remove a node from the gallery using fine-grained synchronization (locks on each node)
        head.get().lock();
        GalleryNode<String> prev = null, curr = null;
        try {
            prev = head.get();
            curr = prev.next;
            curr.lock();
            try {
                while(curr.key < node.key){
                    prev.unlock();
                    prev = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (curr.key == node.key) {
                    prev.next = curr.next;
                    System.out.println("\nThread-" + Thread.currentThread().getName() + ": removed " + node.value + ", " + node.timeLeft.get() + "ms");
                    printQueue();
                }
                return;
            } finally {
                curr.unlock();
            }
        } finally {
            prev.unlock();
        }
    }
    public void printQueue(){
        System.out.print("Thread-" + Thread.currentThread().getName() + ": ");
        GalleryNode<String> curr = head.get();
        while(curr != null){
            System.out.print("[" + curr.value + ", " + curr.timeLeft.get() + "ms]");
            if(curr.next != null){
                System.out.print(" -> ");
            }
            curr = curr.next;
        }
        System.out.println();
    }
}
