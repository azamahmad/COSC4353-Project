import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String arg[]){
        Scanner input = new Scanner(System.in);
        ArrayList<member> members = new ArrayList<member>();
        //ArrayList<team> teams = new ArrayList<team>();
        //ArrayList<task> tasks = new ArrayList<task>();
        //ArrayList<category> catagories = new ArrayList<category>();

        int x = 2;
        System.out.print("1:members, 2:teams, 3:tasks, 4:categories: ");
        x = Integer.parseInt(input.nextLine());
        switch(x) {
            case 1:
            members.add(new member());
            break;
            case 2:
            teams.addElement(new team());
            break;
            case 3:
            //tasks.addElement(new task());
            break;
            case 4:
                //categories.addElement(new category());
                break;
    }
        for(int i = 0; i < members.size(); ++i){
            members.get(i).print();
        }
        /*
        for(int i = 0; i < teams.size(); ++i){
            members.get(i).print();
        }
        for(int i = 0; i < tasks.size(); ++i){
            members.get(i).print();
        }
        for(int i = 0; i < categories.size(); ++i){
            members.get(i).print();
        }
        */
}
}
