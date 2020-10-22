import java.util.Scanner;

public class team {
    private String teamName;
    private String color;
    private int id;

    team(){
        Scanner input = new Scanner(System.in);
        System.out.print("Input team name:");
        teamName = input.nextLine();
        System.out.println("Team name: " + teamName);
        System.out.println("Input team ID:");
        id = Integer.parseInt(input.nextLine());
        System.out.println("ID is " + id);
        System.out.println("Input team color:");
        color = input.nextLine();
        System.out.println("Color is " + color);
    }

    void print(){
        System.out.println("Username is " + teamName);
        System.out.println("Color is " + color);
    }

    public String getTeamName() {
        return teamName;
    }

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
}
