import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class main {
    private static ArrayList<member> members = new ArrayList<>();
    private static ArrayList<team> teams = new ArrayList<>();
    private static ArrayList<task> tasks = new ArrayList<>();
    private static ArrayList<category> categories = new ArrayList<>();

    public static void main(String arg[]) {
        Scanner input = new Scanner(System.in);

        // initialize some dummy members
        initDefaultData(members, teams, tasks, categories);

        member currentUser;


        try {
            System.out.println("[!] Demo userID: 1\n[!] Demo password: password");
            if (false) // debug mode
                currentUser = members.get(0);
            else
                currentUser = MenuLogin(input);
        } catch (CancellationException e){
            return; // closes if the user canceled logging in. In other contexts, you could ignore this and resume
        }

        int choice;
        boolean terminate = false;
        do {
            System.out.printf(
                    "[ Main Menu ]\n" +
                    "Welcome, %s, please select a submenu:\n" +
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
                    MenuMember(currentUser, input);
                    break;
                case 2:
                    MenuTeam(currentUser, input);
                    break;
                case 3:
                    MenuTask(currentUser, input);
                    break;
                case 4:
                    MenuCategory(currentUser, input);
                    break;
                case 5: // logout
                    try {
                        System.out.println("[!] Demo userID: 1\n[!] Demo password: password");
                        skipEmptyLine(input);
                        currentUser = MenuLogin(input);
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
        input.close();
    }

    private static void initDefaultData(ArrayList<member> members,
                                        ArrayList<team> teams,
                                        ArrayList<task> tasks,
                                        ArrayList<category> categories) {
        members.add(new member("Admin account", "password", "Red", true)); // user id 1
        members.add(new member("Nina Carlson", "weakpassword", "Pink", false));    // user id 2
        members.add(new member("Rocky Rhodes", "letmein", "Brown", false));  // user id 3
        members.add(new member("Mike Wazowski", "letmein", "Green", false));  // user id 4
        members.add(new member("Chucky Cheese", "letmein", "Grey", false));  // user id 5
        members.add(new member("Hyper Baby", "letmein", "Brown", false));  // user id 6

        teams.add(new team("Big Brains Team","Pink")); // team id 1
        teams.add(new team("Team Rocket","Red")); // team id 2
        teams.add(new team("Justice League","Blue")); // team id 3
        teams.add(new team("X-Men","Pink")); // team id 4

//        tasks.add()


    }

    // Menu screens

    // This handles logging in. If the user sends an newline for the user ID, it throws CancellationException.
    // See main for examples of how to use this.
    // If you need the user to verify their password to do an action, see call the member.java authenticate function
    private static member MenuLogin(Scanner input)
            throws CancellationException {
        // MenuLogin throws an error if user cancels logging in. Use this to resume session as current user OR terminate
        String str;
        int memberID;
        String password;
        member currentUser;
        do {
            System.out.println("[ Login ]");
            System.out.println("Enter a blank user ID to exit");
            do {
                System.out.print("User ID: ");
                try {
                    str = input.nextLine();
                    if (str.length() == 0) // cancels when empty line is given for username
                        throw new NoSuchElementException();
                    else
                        memberID = Integer.parseInt(str);
                } catch (NoSuchElementException e) { // Acts as a "cancel" feature
//                        System.out.println("Goodbye");
                    throw new CancellationException(); // throws an error upstream to be handled there
                } catch (NumberFormatException e) { // re prompts for user id
                    System.out.println("[!] User ID should be an integer.");
                    continue;
                }
                currentUser = findMember(memberID);
                break; // we found a member (or a null)
            } while (true);
            System.out.print("Password: ");
            password = input.nextLine();
            if (currentUser == null || !currentUser.authenticate(password)) {
                // user not found, or password check failed
                System.out.println("[!] Invalid username or password.\n");
                continue;
            }
            // we found a valid user and logged in successfully
            break;
        } while (true);
        return currentUser;
    }

    // MenuMember (and other menu____'s) display the options and handle directing you to modify specific objects data.
    // ShowMemberTable (and other Show____table's) just handle displaying list of data one "page" at a time.
    // We can remove it later, but it might be better than seeing all the data at once.

    private static void MenuMember(member currentUser, Scanner input) {
        int choice;
        member target;
        boolean terminate = false;
        menu:
        do {
            target = null;
            System.out.print("[ Members ]\n");
            // display the list
            ShowMemberTable();

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create New Member\n" +
                        " 2: Modify Member\n" +
                        " 3: Delete Member\n" +
                        " 4: Back\n");
            else {
                System.out.println("Only admins can modify users");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            try {
                choice = Integer.parseInt(input.next());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid option.\n");
                continue;
            }

            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3) {
                    if (members.size() == 0) {
                        System.out.println("[!] No members exist");
                        continue;
                    }
                    main.skipEmptyLine(input);
                    while (target == null) {
                        System.out.print("Target user ID(None to cancel): ");
                        String str = input.nextLine();
                        if (str.length() == 0)
                            continue menu;
                        try {
                            target = findMember(Integer.parseInt(str)); // we have an integer, find the them in the table
                        } catch (NumberFormatException ignore){
                        }
                        if (target == null) {
                            System.out.println("[!] Invalid ID");
                        } else if (target == currentUser) {
                            System.out.println("[!] You cannot delete your own account");
                            target = null;
                        }
                    }
                }
                switch (choice) {
                    case 1: // create
                        members.add(new member(input));
                        break;
                    case 2: // modify
                        target.modify(input); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                        System.out.printf("Do you really want to delete user \"%s\" id \"%s\"? (Y/N) ",
                                target.getName(),
                                target.getId());
                        String str = "";
                        while (str.length() == 0) {
                            str = input.next();
                        }
                        char c = str.charAt(0);
                        if (c == 'y' || c == 'Y') {
                            members.remove(target);
                            System.out.println("Confirmed. User was deleted.");
                        } else {
                            System.out.println("Aborted. User was not deleted.");
                        }
                        break;
                    case 4: // logout
                        terminate = true;
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            } else { //regular user
                switch (choice) {
                    case 1: // back
                        terminate = true;
                        break;
//                    case 2: //should allow self modification of their OWN, non-admin, account
//                        break
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);
    }

    public static void ShowMemberTable() {
        System.out.println("|  id  |  color  |      Name      | Admin | Additional information ");
        for (member o : members) {
            System.out.println(o.toColumns());
        }
    }

    private static void MenuTeam(member currentUser, Scanner input) {
        int choice;
        team target;
        boolean terminate = false;
        menu:
        do {
            target = null;
            System.out.print("[ Teams ]\n");
            // display the list
            ShowTeamsTable();

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create New Team\n" +
                        " 2: Modify Team\n" +
                        " 3: Delete Team\n" +
                        " 4: View Team Members\n" +
                        " 5: Back\n");
            else {
                System.out.println("Only admins can modify teams");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            try {
                choice = Integer.parseInt(input.next());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid option.\n");
                continue;
            }

            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3 || choice == 4) {
                    if (teams.size() == 0) {
                        System.out.println("[!] No teams exist");
                        continue;
                    }
                    main.skipEmptyLine(input);
                    while (target == null) {
                        System.out.print("Target Team ID(None to cancel): ");
                        String str = input.nextLine();
                        if (str.length() == 0)
                            continue menu;
                        try {
                            target = findTeam(Integer.parseInt(str)); // we have an integer, find the them in the table
                        } catch (NumberFormatException ignore) {
                        }
                        if (target == null)
                            System.out.println("[!] Invalid ID");
                    }
                }
                switch (choice) {
                    case 1: // create
                        teams.add(new team(input));
                        break;
                    case 2: // modify
                        target.modify(input); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                            System.out.printf("Do you really want to delete team \"%s\" id \"%s\"? (Y/N) ",
                                    target.getTeamName(),
                                    target.getId());
                            String str = "";
                            while (str.length() == 0) {
                                str = input.next();
                            }
                            char c = str.charAt(0);
                            if (c == 'y' || c == 'Y') {
                                teams.remove(target);
                                System.out.println("Confirmed. Team was deleted.");
                            } else {
                                System.out.println("Aborted. Team was not deleted.");
                            }

                        break;
                    case 4:
                        target.showTeam();
                        break;
                    case 5: // back
                        terminate = true;
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            } else { //regular user
                switch (choice) {
                    case 1: // back
                        terminate = true;
                        break;
//                    case 2: //should allow self modification of their OWN, non-admin, account
//                        break
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);

    }

    public static void ShowTeamsTable() {
        System.out.println("|  id  |  color  |      Name      | Additional information ");
        for (team o : teams) {
            System.out.println(o.toColumns());
        }
    }

    private static void MenuTask(member currentUser, Scanner input) {
        int choice;
        task target;
        boolean terminate = false;
        menu:
        do {
            target = null;
            System.out.print("[ Tasks ]\n");
            // display the list
            ShowTasksTable(currentUser);

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create New Task\n" +
                        " 2: Modify Task\n" +
                        " 3: Delete Task\n" +
                        " 4: Back\n");
            else {
                System.out.println("Only admins can modify tasks");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            try {
                choice = Integer.parseInt(input.next());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid option.\n");
                continue;
            }

            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3) {
                    if (tasks.size() == 0) {
                        System.out.println("[!] No tasks exist");
                        continue;
                    }
                    main.skipEmptyLine(input);
                    while (target == null) {
                        System.out.print("Target task ID(None to cancel): ");
                        String str = input.nextLine();
                        if (str.length() == 0)
                            continue menu;
                        try {
                            target = findTask(Integer.parseInt(str)); // we have an name, find the them in the table
                        } catch (NumberFormatException ignore) {
                        }
                        if (target == null)
                            System.out.println("[!] Invalid ID");
                    }
                }
                switch (choice) {
                    case 1: // create
                        tasks.add(new task(input, currentUser));
                        break;
                    case 2: // modify
                        target.modify(input); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                        System.out.printf("Do you really want to delete task \"%s\" id \"%s\"? (Y/N) ",
                                target.getName(),
                                target.getId());
                        String str = "";
                        while (str.length() == 0) {
                            str = input.next();
                        }
                        char c = str.charAt(0);
                        if (c == 'y' || c == 'Y') {
                            tasks.remove(target);
                            System.out.println("Confirmed. task was deleted.");
                        } else {
                            System.out.println("Aborted. task was not deleted.");
                        }
                        //}
                        break;
                    case 4: // logout
                        terminate = true;
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            } else { //regular user
                switch (choice) {
                    case 1: // back
                        terminate = true;
                        break;
//                    case 2: //should allow self modification of their OWN, non-admin, account
//                        break
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);
    }

    public static void ShowTasksTable() { // shows ALL the tasks regardless of user
        System.out.println("|  id  |  color  |      Name      |   Assigned To  |           Due Date           | Subtasks ");
        for (task o : tasks) { // prints only the tasks that belong to the user OR all tasks if they are an admin
            System.out.println(o.toColumns());
        }
    }

    public static void ShowTasksTable(member currentUser) {
        System.out.println("|  id  |  color  |      Name      |   Assigned To  |           Due Date           | Subtasks ");
        for (task o : tasks) { // prints only the tasks that belong to the user OR all tasks if they are an admin
            if (currentUser.isAdmin() || o.getAssignedTo().equals(currentUser))
                System.out.println(o.toColumns());
        }
    }

    private static void MenuCategory(member currentUser, Scanner input) {
        int choice;
        category target;
        boolean terminate = false;
        menu:
        do {
            target = null;
            System.out.print("[ Category ]\n");
            // display the list
            ShowCategoryTable();

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create New Category\n" +
                        " 2: Modify Category\n" +
                        " 3: Delete Category\n" +
                        " 4: View Category Tasks\n" +
                        " 5: Back\n");
            else {
                System.out.println("Only admins can modify users");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            try {
                choice = Integer.parseInt(input.next());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid option.\n");
                continue;
            }

            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3 || choice == 4) {
                    if (categories.size() == 0) {
                        System.out.println("[!] No categories exist");
                        continue;
                    }
                    main.skipEmptyLine(input);
                    while (target == null) {
                        System.out.print("Target Category Name(None to cancel): ");
                        String str = input.nextLine();
                        if (str.length() == 0)
                            continue menu;
                        try {
                            target = findCategory(Integer.parseInt(str)); // we have an integer, find the them in the table
                        } catch (NumberFormatException ignore) {
                        }
                        if (target == null)
                            System.out.println("[!] Invalid ID");
                    }
                }
                switch (choice) {
                    case 1: // create
                        categories.add(new category(input, currentUser));
                        break;
                    case 2: // modify
                        target.modify(); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                        System.out.printf("Do you really want to delete category \"%s\"? (Y/N) ",
                                target.getCategoryName());
                        String str = "";
                        while (str.length() == 0) {
                            str = input.next();
                        }
                        char c = str.charAt(0);
                        if (c == 'y' || c == 'Y') {
                            categories.remove(target);
                            System.out.println("Confirmed. User was deleted.");
                        } else {
                            System.out.println("Aborted. User was not deleted.");
                        }
                        break;
                    case 4:
                        target.showTasks();
                        break;
                    case 5: // logout
                        terminate = true;
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            } else { //regular user
                switch (choice) {
                    case 1: // back
                        terminate = true;
                        break;
//                    case 2: //should allow self modification of their OWN, non-admin, account
//                        break
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);
    }

    public static void ShowCategoryTable() {
        System.out.println("|  Id  |      Name      |  Color  |   Created By   |          Created On          | Description");
        for (category o : categories) {
            System.out.println(o.toColumns());
        }
    }

    // functions to search for an object in the arrays
    // If they don't find a match, they return null.
    // After searching, ALWAYS do something like this:
    // member = findMember(members, 1)
    // if (member == null)
    //    //exception code

    public static member findMember(int memberID) {
        for (member obj : members) {
            if (obj.getId() == memberID)
                return obj;
        }
        return null;
    }

    public static team findTeam(int teamID) {
        for (team obj : teams) {
            if (obj.getId() == teamID)
                return obj;
        }
        return null;
    }

    public static task findTask(int taskID) {
        for (task obj : tasks) {
            if (obj.getId() == taskID)
                return obj;
        }
        return null;
    }

    public static category findCategory(int categoryID) {
        for (category obj : categories) {
            if (obj.getId() == categoryID)
                return obj;
        }
        return null;
    }

    public static void skipEmptyLine(Scanner input) { // hotfix for using scanner.next() followed by scanner.nextLine()
        try {
            input.skip("\n");
        }catch (Exception ignore) {

        }
    }
}
