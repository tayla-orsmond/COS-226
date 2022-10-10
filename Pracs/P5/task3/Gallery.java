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
        while(true){
            GalleryNode<String> curr = head.get();
            GalleryNode<String> next = curr.next;
            curr.lock();
            next.lock();
            try{
                if(validate(curr, next)){
                    newNode.next = next;
                    curr.next = newNode;
                    System.out.println("Thread-" + Thread.currentThread().getName() + ": added " + newNode.value + ", " + newNode.timeLeft.get() + "ms");
                }
                return;
            }
            finally{
                curr.unlock();
                next.unlock();
            }
        }
    }
    public void remove(GalleryNode<String> node){
        //Remove a node from the gallery using fine-grained synchronization
        printQueue();
        while(true){
            GalleryNode<String> curr = head.get();
            GalleryNode<String> next = curr.next;
            while(next.key != node.key){
                curr = next;
                next = curr.next;
            }
            curr.lock();
            next.lock();
            try{
                if(validate(curr, next)){
                    if(next.key == node.key){
                        curr.next = next.next;
                        System.out.println("(Thread-" + Thread.currentThread().getName() + ": removed " + node.value + ", " + node.timeLeft.get() + "ms)");
                    }
                    return;
                }
            }
            finally{
                curr.unlock();
                next.unlock();
            }
        }
    }
    public boolean validate(GalleryNode<String> curr, GalleryNode<String> next){
        //Check if the current and next nodes are still linked to each other and linked from the head of the list
        GalleryNode node = head.get();
        while(node != null && node.key != curr.key){
            if(node == curr){
                return curr.next == next;
            }
            node = node.next;
        }
        return false;
    }
    public void printQueue(){
        //Print the gallery
        //Print the calling thread's id
        GalleryNode<String> curr = head.get();
        System.out.print("\nThread-" + Thread.currentThread().getName() + ": ");
        while(curr.next != null){
            System.out.print("[" + curr.value + ", "+ curr.timeLeft.get() + " ms]");
            if(curr.next != null){
                System.out.print(" -> ");
            }
            else{
                System.out.print("\n");
            }
            if(curr.next != null){
                curr = next;
            }               
        }
        if(curr != null){
            System.out.print("[" + curr.value + ", "+ curr.timeLeft.get() + " ms]\n");
        }
    }
}
