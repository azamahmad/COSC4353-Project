import java.util.ArrayList;
import java.util.Scanner;

public class team {
    private String teamName;
    private String color;
    private static int currentID = 1;
    private int id;
    private String additional;
    private ArrayList<member> teamMembers;
    
    team(Scanner input){
        main.skipEmptyLine(input);
        do{
            System.out.print("Input team name:");
            teamName = input.nextLine();
        } while(teamName.length() == 0);
        System.out.println("Team name: " + teamName);

        id = currentID++;
        System.out.println("ID is " + id);

        do{
            System.out.print("Input team color:");
            color = input.nextLine();
        }while(color.length() == 0);

        System.out.println("Color is " + color);

        System.out.print("Additional team information (optional): ");
        additional = input.nextLine();

        teamMembers = new ArrayList<>();
        menuAddMember(input);
    }


    public void modify(Scanner input) {
        System.out.println("Enter a blank line to keep current value.");
        String str;
        System.out.printf("Team name(%s): ", teamName);
        str = input.nextLine();
        if (str.length() > 0) // do this to keep the original value if no input was given
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

        menuAddMember(input);
        menuRemoveMember(input);
    }

    public team(String name, String color) {
        this.teamName = name;
        this.id = currentID++;
        this.color = color;
        this.additional = "";
        this.teamMembers = new ArrayList<>();
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

    public boolean hasMember(member user) {
        return teamMembers.contains(user);
    }

    public boolean addMember(member user) { // returns true if member was successfully added
        if (this.hasMember(user)) {
            return false;
        } else {
            teamMembers.add(user);
            return true;
        }
    }

    public boolean removeMember(member user) { // returns true if member was successfully removed
        if (!this.hasMember(user)) {
            return false;
        } else {
            teamMembers.remove(user);
            return true;
        }
    }

    public void showTeam() {
        System.out.printf("[ Team: %s ]\n", teamName);
        if (additional.length() > 0)
            System.out.printf("More info: %s\n", additional);
        System.out.printf("Color: %s\n", color);
        if (teamMembers.size() > 0) {
            System.out.println("|  id  |  Name");
            for (member user : teamMembers)
                System.out.printf("| % 4d  |  %s\n", user.getId(), user.getName());
        } else {
            System.out.println("No team members");
        }
        System.out.println();
    }

    public void menuAddMember(Scanner input) {
        System.out.println("[ Members ]");
        main.ShowMemberTable();
        System.out.println("[!] Add members to your team");
        member target = null;
        while (true) {
            System.out.print("Select user ID(None to cancel): ");
            String str = input.nextLine();
            if (str.length() == 0)
                break;
            try {
                target = main.findMember(Integer.parseInt(str)); // we have an integer, find the them in the table
            } catch (NumberFormatException ignore){
            }
            if (target == null) {
                System.out.println("[!] Invalid ID");
                continue;
            }
            if (this.addMember(target)) {
                System.out.printf("Added %d (%s) to the team\n", target.getId(), target.getName());
            } else {
                System.out.printf("[!] %d (%s) is already in the team\n", target.getId(), target.getName());
            }
        }
    }

    public void menuRemoveMember(Scanner input) {
        System.out.println("[ Members ]");
        showTeam();
        System.out.println("[!] Remove members to your team");
        member target = null;
        while (true) {
            System.out.print("Select user ID(None to cancel): ");
            String str = input.nextLine();
            if (str.length() == 0)
                break;
            try {
                target = main.findMember(Integer.parseInt(str)); // we have an integer, find the them in the table
            } catch (NumberFormatException ignore){
            }
            if (target == null) {
                System.out.println("[!] Invalid ID");
                continue;
            }
            if (this.removeMember(target)) {
                System.out.printf("Removed %d (%s) from the team\n", target.getId(), target.getName());
            } else {
                System.out.printf("[!] %d (%s) is not in the team\n", target.getId(), target.getName());
            }
        }

    }
}
