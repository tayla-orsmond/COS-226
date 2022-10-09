public class Main{
    public static void main(String[] args){
        Gallery gallery = new Gallery();
        int MAX = 5;
        Security threads [] = new Security[MAX];
        for(int i = 0; i < MAX; i++){
            threads[i] = new Security(i, gallery);
            threads[i].start();
        }
    }
}