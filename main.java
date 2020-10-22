import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class main {
    public static void main(String arg[]) {
        Scanner input = new Scanner(System.in);
        ArrayList<member> members = new ArrayList<>();
        ArrayList<team> teams = new ArrayList<>();
        ArrayList<task> tasks = new ArrayList<>();
        ArrayList<category> categories = new ArrayList<>();

        // initialize some dummy members
        members.add(new member("Test account", "password", "Red", false)); // user id 0
        members.add(new member("John Doe", "1337pass", "Pink", false));    // user id 1
        members.add(new member("Mary Sue", "letmein1", "Orange", false));  // user id 2
        member currentUser;

        try {
            System.out.println("[!] Demo userID: 0\n[!] Demo password: password");
            currentUser = MenuLogin(members);
        } catch (CancellationException e){
            return; // closes if the user canceled logging in. In other contexts, you could ignore this and resume
        }

        int choice;
        boolean terminate = false;
        do {
            System.out.printf(
                    "[ Main Menu ]\n" +
                    "Welcome, %s, please select a submenu\n" +
                    " 1: Members\n" +
                    " 2: Teams\n" +
                    " 3: Tasks\n" +
                    " 4: Categories\n" +
                    " 5: Logout\n", currentUser.getName());
            System.out.print("Choice: ");
            if (!input.hasNextInt()) {
                System.out.println("[!] Please enter a valid option.\n");
                input.next();
                continue;
            }
            choice = input.nextInt();
            switch (choice) {
                case 1:

                    members.add(new member());
                    break;
                case 2:
                    teams.add(new team());
                    break;
                case 3:
                    tasks.add(new task());
                    break;
                case 4:
                    categories.add(new category());
                    break;
                case 5: // logout
                    try {
                        currentUser = MenuLogin(members);
                    } catch (CancellationException e) {
                        terminate = true;
                    }
                    break;
                default:
                    System.out.println("[!] Please enter a valid option");
                    break;
            }
        } while (!terminate);
        // we can now run code that closes/saves/displays.
/*
        System.out.println("List of members: ");
        for (member member : members) {
            member.print();
            System.out.println();
        }
        System.out.println();

        System.out.println("List of teams:");
        for (team team : teams) {
            team.print();
            System.out.println();
        }
        System.out.println();

        System.out.println("List of tasks:");
        for(task task : tasks){
            task.print();
            System.out.println();
        }
        System.out.println();

        System.out.println("List of categories:");
        for(category category : categories){
            category.print();
            System.out.println();
        }
        System.out.println();
*/
    }

    // Menu screens

    private static member MenuLogin(ArrayList<member> members) throws CancellationException {
        // MenuLogin throws an error if user cancels logging in. Use this to resume session as current user OR terminate
        Scanner input = new Scanner(System.in);
        int memberID;
        String password;
        member currentUser;
        do {
            System.out.println("[ Login ]");
            System.out.println("Ctrl+D to exit");
            do {
                System.out.print("User ID: ");
                if (!input.hasNextInt()) {
                    try {
                        input.next(); // clears the current input
                    } catch (NoSuchElementException e) { // returns null on Ctrl+D or other in
//                        System.out.println("Goodbye");
                        throw new CancellationException();
                    }
                    System.out.println("[!] User ID should be an integer.\n");
                    continue; // reprompts for user id
                }
                memberID = input.nextInt();
                currentUser = findMember(members, memberID);
                break; // we found a member (or a null)
            } while (true);
            System.out.print("Password: ");
            password = input.next();
            if (currentUser == null || !currentUser.authenticate(password)) {
                System.out.println("[!] Invalid username or password.\n");
                continue; // user not found, or password check failed
            }
            break; // we found a valid user and logged in successfully
        } while (true);
        return currentUser;
    }

    private static void MenuMember(ArrayList<member> members) {

    }

    private static void MenuTeam(ArrayList<team> teams) {

    }

    private static void MenuTask(ArrayList<task> tasks) {

    }

    private static void MenuCategorie(ArrayList<category> categories) {

    }

    // functions to search for an object in the arrays

    private static member findMember(ArrayList<member> members, int memberID) {
        for (member obj : members) {
            if (obj.getId() == memberID)
                return obj;
        }
        return null;
    }

    private static team findTeam(ArrayList<team> teams, int teamID) {
        for (team obj : teams) {
            if (obj.getId() == teamID)
                return obj;
        }
        return null;
    }

    private static task findTeam(ArrayList<task> tasks, String taskName) {
        for (task obj : tasks) {
            if (obj.getName().equals(taskName))
                return obj;
        }
        return null;
    }

    private static category findCategory(ArrayList<category> categories, String categoryName) {
        for (category obj : categories) {
            if (obj.getCategoryName().equals(categoryName))
                return obj;
        }
        return null;
    }


}
