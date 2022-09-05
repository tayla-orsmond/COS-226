//Tayla Orsmond u21467456

public class Main {
    public static void main(String args[]){
        int MAX = 5;
        Developer[] devs = new Developer[MAX];
        Tester[] tests = new Tester[MAX];
        Project project = new Project(MAX);

        for(int i = 0; i < MAX; i++){
            devs[i] = new Developer(i, project);
            tests[i] = new Tester(i, project);
        }
        for(Developer dev : devs){
            dev.start();
        }
        for(Tester t : tests){
            t.start();
        }
    }
}
