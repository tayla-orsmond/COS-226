//Tayla Orsmond u21467456

public class Developer extends Thread{
    Thread t;
    int ID;
    Project project;

    public Developer(int id, Project p){
		ID = id;
        project = p;
	}

	@Override
	public void run()
	{
		while(!project.getDevs().isEmpty()){
            project.develop();
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
