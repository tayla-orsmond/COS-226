public class MyThread extends Thread{
    private Thread t;
    private String name;
    private Scrumboard scrum;

    MyThread(String threadName, Scrumboard scrumBoard){
        name = threadName;
        scrum = scrumBoard;
        System.out.println("Creating thread: " + name);
    }

    public void run(){
        for(int i = 5; i > 0; i--){
            Character c = scrum.getNextTodo();
            System.out.println(name + " Task: " + c);
            scrum.setCompleted(c);
        }
    }

    public void start(){
        System.out.println("Starting: " + name);
        if(t == null){
            t = new Thread(this, name);
            t.start();
        }
    }
}
