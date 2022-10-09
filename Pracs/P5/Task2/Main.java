public class Main{
    public static void main(String[] args){
        Gallery gallery = new Gallery();
        int MAX = 5;
        Security threads [] = new Security[MAX];
        for(int i = 0; i < MAX; i++){
            threads[i] = new Security(i, gallery);
            threads[i].start();
        }
        for(int i = 0; i < MAX; i++){
            try{
                threads[i].join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("\n\nAll threads have finished. Final gallery:");
        gallery.printQueue();
    }
}