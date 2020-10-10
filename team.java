import java.util.Scanner;

public class team {
    String teamName;
    String color;
    int id;
    boolean validated;

    team(){
        Scanner input = new Scanner(System.in);

        do{
            System.out.print("Input team name:");
            teamName = input.nextLine();
        } while(teamName.length() == 0);
        System.out.println("Team name: " + teamName);

        do {
            System.out.println("Create a team ID:");
            try {
                id = Integer.parseInt(input.nextLine());
                validated = true;
            } catch (java.lang.NumberFormatException e) {
                System.out.println("ID must be an integer");
                validated = false;
            }
        } while (id == 0);
        System.out.println("ID is " + id);

        do{
            System.out.println("Input team color:");
            color = input.nextLine();
        }while(color.length() == 0);

        System.out.println("Color is " + color);
    }

    void print(){
        System.out.println("Team name is " + teamName);
        System.out.println("Color is " + color);
    }

}
