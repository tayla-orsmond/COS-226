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
    public void printCompleted(){
        System.out.println("Completed Tasks: " + Completed.size());
        for(Character c: Completed){
            System.out.print(c + ", ");
        }
        System.out.println();
    }
    public void printTodo(){
        System.out.println("ToDo Tasks: " + Todo.size());
        for(Character c: Todo){
            System.out.print(c + ", ");
        }
        System.out.println();
    }
    public int getSize() {
        return Todo.size();
    }
}