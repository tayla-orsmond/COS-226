//Tayla Orsmond u2147456
public class MCSNode {
    private volatile boolean locked;
    private volatile MCSNode next;

    public MCSNode() {
        locked = false;
        next = null;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public MCSNode getNext() {
        return next;
    }
    
    public void setNext(MCSNode next) {
        this.next = next;
    }
}
