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
        //Add a node to the gallery using optimistic synchronization
        while(true){
            GalleryNode<String> prev = head.get();
            GalleryNode<String> curr = prev.next;
            while(curr.key < newNode.key){
                prev = curr;
                curr = curr.next;
            }
            prev.lock();
            curr.lock();
            try{
                if(validate(prev, curr)){
                    if(curr.key == newNode.key){
                        return;
                    }
                    else{
                        newNode.next = curr;
                        prev.next = newNode;
                        System.out.println("Thread-" + Thread.currentThread().getName() + ": added " + newNode.value + ", " + newNode.timeLeft.get() + "ms");
                        return;
                    }
                }
            }
            finally{
                prev.unlock();
                curr.unlock();
            }
        }
    }
    public void remove(GalleryNode<String> node){
        //Remove a node from the gallery using fine-grained synchronization (locks on each node)
        while(true){
            GalleryNode<String> prev = head.get();
            GalleryNode<String> curr = prev.next;
            while(curr.key < node.key){
                prev = curr;
                curr = curr.next;
            }
            prev.lock();
            curr.lock();
            try{
                if(validate(prev, curr)){
                    if(curr.key == node.key){
                        prev.next = curr.next;
                        System.out.println("\nThread-" + Thread.currentThread().getName() + ": removed " + curr.value + ", " + curr.timeLeft.get() + "ms");
                        printQueue();
                        return;
                    }
                    else{
                        return;
                    }
                }
            }
            finally{
                prev.unlock();
                curr.unlock();
            }
        }
    }
    public boolean validate(GalleryNode<String> prev, GalleryNode<String> curr){
        //Check if the nodes are still in the correct order
        GalleryNode<String> node = head.get();
        while(node.key <= prev.key){
            if(node == prev){
                return node.next == curr;
            }
            node = node.next;
        }
        return false;
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
