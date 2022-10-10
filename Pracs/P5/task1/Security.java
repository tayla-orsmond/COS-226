import java.util.LinkedList;

//Tayla Orsmond u21467456
public class Security extends Thread{
    //The threads adding and removing nodes from the gallery
    private int ID;
    private Gallery gallery;
    private LinkedList<GalleryNode<String>> myNodes;

    public Security(int ID, Gallery gallery){
        this.ID = ID;
        this.gallery = gallery;
        this.myNodes = new LinkedList<>();
        this.setName(String.valueOf(ID));
    }

    public void run(){
        for(int i = 0; i < 10; i++){
            GalleryNode<String> newNode = new GalleryNode<>((ID + i), "P-" + ID + ":" + i);
            myNodes.add(newNode);
            gallery.add(newNode);
            newNode.timeEntered = System.currentTimeMillis();
            newNode.spendTime();
            try{
                Thread.sleep(200);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            //if any of my node's times are up, remove the node from the gallery
            for(GalleryNode<String> node : myNodes){
                if(node.timeLeft.get() <= 0){
                    gallery.remove(node);
                    myNodes.remove(node);
                }
            }
        }
        while(!myNodes.isEmpty()){
            for(GalleryNode<String> node : myNodes){
                if(node.timeLeft.get() <= 0){
                    gallery.remove(node);
                    myNodes.remove(node);
                }
            }
        }
    }
}
