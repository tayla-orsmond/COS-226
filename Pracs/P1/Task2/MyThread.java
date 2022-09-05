public class MyThread extends Thread{
    private Thread t;
    private int id;
    private Peterson lock;
    private Scrumboard scrum;

    MyThread(int threadID, Scrumboard scrumBoard, Peterson peter){
        id = threadID;
        scrum = scrumBoard;
        lock = peter;
        //System.out.println("Creating thread: " + id);
    }

    public void run(){
       for(int i=0; i < 5 && scrum.getSize() > 0; i++){
            lock.lock();
            try{
                //System.out.println("Thread-" + id + " Has the lock.");
                Character c = scrum.getNextTodo();
                System.out.println("--> Thread-" + id + " Task: " + c);
                scrum.setCompleted(c);
            }finally{
                lock.unlock();
            }
       }
    }

    public void start(){
        //System.out.println("Starting: " + id);
        if(t == null){
            t = new Thread(this, String.valueOf(id));
            t.start();
        }
    }
}
