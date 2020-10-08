import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String arg[]){
        Scanner input = new Scanner(System.in);
        ArrayList<member> members = new ArrayList<member>();
        ArrayList<team> teams = new ArrayList<team>();
        //ArrayList<task> tasks = new ArrayList<task>();
        //ArrayList<category> categories = new ArrayList<category>();

        int x;
        System.out.print("1:members, 2:teams, 3:tasks, 4:categories: ");
        x = Integer.parseInt(input.nextLine());
        switch(x) {
            case 1:
            members.add(new member());
            break;
            case 2:
            teams.add(new team());
            break;
            case 3:
            //tasks.add(new task());
            break;
            case 4:
                //categories.add(new category());
                break;
        }
        System.out.println("List of members: ");
        for (member member : members) {
            member.print();
            System.out.println();
        }

//        System.out.println("List of teams");
//        for (team team : teams) {
//            team.print();
//            System.out.println();
//        }

//        System.out.println("List of tasks");
//        for(task task : tasks){
//            task.print();
//            System.out.println();
//        }

//        System.out.println("List of categories");
//        for(category category : categories){
//            category.print();
//            System.out.println();
//        }

}
}
