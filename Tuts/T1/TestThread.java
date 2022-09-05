class ThreadDemo extends Thread{
    private Thread t;
    private String name;

    ThreadDemo(String threadName){
        name = threadName;
        System.out.println("Creating: " + name);
    }

    public void run(){
        System.out.println("Running: " + name);
        try{
            for(int i = 4; i > 0; i--){
                System.out.println("Thread: " + name + ": " + i);
                //Let Thread sleep for a while
                Thread.sleep(500);
            }
        } catch (InterruptedException e){
            System.out.println("Thread: " + name + " interrupted");
        }
        System.out.println("Thread: " + name + " exiting.");
    }

    public void start(){
        System.out.println("Starting: " + name);
        if(t == null){
            t = new Thread(this, name);
            t.start();
        }
    }
}

class RunnableDemo implements Runnable{
    private Thread t;
    private String name;
    private Integer max = 500;
    private Integer min = 50;
    RunnableDemo(String threadName){
        name = threadName;
        System.out.println("Creating: "+ name);
    }

    public void run(){
        System.out.println("Running: " + name);
        try{
            for(int i = 4; i > 0; i--){
                System.out.println("Thread: " + name + ": " + i);
                //Let Thread sleep for a while
                Thread.sleep((long)(Math.random() * (max - min + 1) + min));
            }
        } catch (InterruptedException e){
            System.out.println("Thread: " + name + " interrupted");
        }
        System.out.println("Thread: " + name + " exiting.");
    }

    public void start(){
        System.out.println("Starting: " + name);
        if(t == null){
            t = new Thread(this, name);
            t.start();
        }
    }
}

public class TestThread{
    public static void main(String[] args){
        ThreadDemo t1 = new ThreadDemo("Thread-A");
        t1.start();
        ThreadDemo t2 = new ThreadDemo("Thread-B");
        t2.start();
        ThreadDemo t3 = new ThreadDemo("Thread-C");
        t3.start();
        ThreadDemo t4 = new ThreadDemo("Thread-D");
        t4.start();
        ThreadDemo t5 = new ThreadDemo("Thread-E");
        t5.start();
    }
}