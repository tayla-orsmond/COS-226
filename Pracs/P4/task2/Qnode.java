//Tayla Orsmond u2147456
public class Qnode {
    private volatile boolean locked;
    public Marshal parent;
    private volatile Qnode next;
    private volatile Qnode prev;

    public Qnode() {
        locked = false;
        next = null;
        prev = null;
        parent = (Marshal) Thread.currentThread();
    }
    public Qnode(Marshal p) {
        locked = false;
        next = null;
        prev = null;
        parent = p;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public Qnode getNext() {
        return next;
    }
    
    public void setNext(Qnode next) {
        this.next = next;
    }

    public Qnode getPrev(){
        return prev;
    }

    public void setPrev(Qnode prev){
        this.prev = prev;
    }
}
