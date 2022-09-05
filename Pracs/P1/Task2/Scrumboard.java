//Tayla Orsmond u21467456
public class Scrumboard{
    private java.util.ArrayList<Character> Todo;
    private java.util.ArrayList<Character> Completed;

    Scrumboard(){
        Todo = new java.util.ArrayList<>();
        Todo.add('A');
        Todo.add('B');
        Todo.add('C');
        Todo.add('D');
        Todo.add('E');
        Todo.add('F');
        Todo.add('G');
        Todo.add('H');
        Todo.add('I');
        Todo.add('J');
        Completed = new java.util.ArrayList<>();
        System.out.println("--Creating Scrumboard--");
    }
    public Character getNextTodo(){
        return Todo.remove(0);
    }
    public void setCompleted(Character completedTask){
        Completed.add(completedTask);
    } 
    public int getSize(){
        return Todo.size();
    }
    public void print(){
        System.out.println("=== Completed: "+ Completed.size() + " ===");
        System.out.println(Completed.toString());
        System.out.println("=== ToDo: "+ Todo.size() + " ===");
        System.out.println(Todo.toString());
    }
}