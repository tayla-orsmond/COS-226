import java.util.Random;

public class QThread extends Thread {
    private BoundedQueue<String> queue;
    private int numOps;
    private int maxSleep;
    private Random rand = new Random();
    private boolean role;

    public QThread(BoundedQueue<String> queue, int id, boolean role, int numOps, int maxSleep){
        this.queue = queue;
        this.numOps = numOps;
        this.maxSleep = maxSleep;
        this.role = role;
        //set name
        this.setName(Integer.toString(id));
    }

    public void run(){
        if(role){
            for(int i = 0; i < numOps || BoundedQueue.maxEnq > 0; i++){
                try{
                    //enqueue a blue colour string
                    queue.enq("\u001b[31m " + rand.nextInt(maxSleep) + " \u001b[0m");
                    Thread.sleep(rand.nextInt(maxSleep));
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        else{
            for(int i = 0; i < numOps || queue.size() > 0; i++){
                try{
                    queue.deq();
                    Thread.sleep(rand.nextInt(maxSleep));
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }    
}
