//Tayla Orsmond u21467456

public class Tester extends Thread{
    Thread t;
    int ID;
    Project project;

    public Tester(int id, Project p){
		ID = id;
        project = p;
	}

	@Override
	public void run()
	{
		while(project.toDevelop > project.tested){
            if(project.getTests().size() > 0){
                project.test();
            }
        }
	}
	@Override
	public void start(){
        if(t == null){
            t = new Thread(this, String.valueOf(ID));
            t.start();
        }
    }
}
