//Tayla Orsmond u21467456
public class Main {
    public static void main(String args[]){
        Scrumboard scrummy = new Scrumboard();
        Peterson peter = new Peterson();
        scrummy.print();
        Thread t0 = new MyThread(0, scrummy, peter);
        Thread t1 = new MyThread(1, scrummy, peter);

        t0.start();
        t1.start();
    }
}
