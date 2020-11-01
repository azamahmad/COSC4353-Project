import java.util.Scanner;

public class team {
    private boolean validated;
    private String teamName;
    private String color;
    private static int currentID = 1;
    private int id;
    private String additional;

    
    team(){
        Scanner input = new Scanner(System.in);

        do{
            System.out.print("Input team name:");
            teamName = input.nextLine();
        } while(teamName.length() == 0);
        System.out.println("Team name: " + teamName);

        id = currentID++;
        System.out.println("ID is " + id);

        do{
            System.out.println("Input team color:");
            color = input.nextLine();
        }while(color.length() == 0);

        System.out.println("Color is " + color);

        System.out.println("Additional team information (can leave empty):");
        additional = input.nextLine();
    }


    public void modify() {
        System.out.println("Enter a blank line to keep current value.");
        Scanner input = new Scanner(System.in);
        String str;
        System.out.printf("Team name(%s): ", teamName);
        str = input.nextLine();
        if (teamName.length() > 0) // do this to keep the original value if no input was given
            teamName = str;
        System.out.println("Team name is " + teamName);
        System.out.printf("Color(%s): ", color);
        str = input.nextLine();
        if (str.length() > 0)
            color = str;
        System.out.println("Color is " + color);
        System.out.printf("Additional information( %s ): ", additional);
        str = input.nextLine();
        if (str.length() > 0)
            additional = str;
    }

    public team(String name, String color) {
        this.teamName = name;
        this.id = currentID++;
        this.color = color;
        this.additional = "";
    }

    void print(){
        System.out.println("Username is " + teamName);
        System.out.println("ID is " + id);
        System.out.println("Color is " + color);
        System.out.println("Additional team information: " + additional);
    }

    public String toColumns() {
        // format:           "|  id  |  color  |      Name      | Additional information "
        return String.format("| % 4d | %7s | %14s | %s",
                id,
                color,
                teamName,
                additional);
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
