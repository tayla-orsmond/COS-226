//Tayla Orsmond u21467456
public class Task1 {
    public static void main(String args[]){
        Scrumboard scrummy = new Scrumboard();
        Thread t1 = new MyThread("Thread-1", scrummy);
        Thread t2 = new MyThread("Thread-2", scrummy);
        System.out.println("--- Printing Lists ---");
        scrummy.printCompleted();
        scrummy.printTodo();
        t1.start();
        t2.start();
    }
}
