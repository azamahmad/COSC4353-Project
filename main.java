import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class main {
    private final static int PAGELENGTH = 5; // displays N items per page

    public static void main(String arg[]) {
        Scanner input = new Scanner(System.in);
        ArrayList<member> members = new ArrayList<>();
        ArrayList<team> teams = new ArrayList<>();
        ArrayList<task> tasks = new ArrayList<>();
        ArrayList<category> categories = new ArrayList<>();

        // initialize some dummy members
        initDefaultData(members, teams, tasks, categories);

        member currentUser;


        try {
            System.out.println("[!] Demo userID: 1\n[!] Demo password: password");
            currentUser = MenuLogin(members);
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
//                    members.add(new member());
                    MenuMember(members, currentUser);
                    break;
                case 2:
                    //System.out.println("Not updated yet");
                    MenuTeam(teams, currentUser);
                    break;
                case 3:
                    //System.out.println("Not updated yet");
                    MenuTask(tasks, currentUser);
                    break;
                case 4:
                    MenuCategory(categories, currentUser);
                    break;
                case 5: // logout
                    try {
                        System.out.println("[!] Demo userID: 1\n[!] Demo password: password");
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

    private static void initDefaultData(ArrayList<member> members,
                                        ArrayList<team> teams,
                                        ArrayList<task> tasks,
                                        ArrayList<category> categories) {
        members.add(new member("Admin account", "password", "Red", true)); // user id 1
        members.add(new member("Crung McCrimb", "weakpassword", "Pink", false));    // user id 2
        members.add(new member("Mohammed Ali", "letmein", "Orange", false));  // user id 3
        members.add(new member("Elvis Presley", "letmein", "Blue", false));  // user id 4
        members.add(new member("Nina Carlson", "letmein", "Cyan", false));  // user id 5
        members.add(new member("Matthew Parker", "letmein", "Pink", false));  // user id 6
        members.add(new member("Rocky Rhodes", "letmein", "Brown", false));  // user id 7
        members.add(new member("Mike Wazowski", "letmein", "Green", false));  // user id 8
        members.add(new member("Freeza", "letmein", "Purple", false));  // user id 9
        members.add(new member("Chucky Cheese", "letmein", "Grey", false));  // user id 10
        members.add(new member("Hyper Baby", "letmein", "Brown", false));  // user id 11

        teams.add(new team("Big Brains Team", "password", "Pink", true)); // user id 1


    }

    // Menu screens

    // This handles logging in. If the user sends an newline for the user ID, it throws CancellationException.
    // See main for examples of how to use this.
    // If you need the user to verify their password to do an action, see call the member.java authenticate function
    private static member MenuLogin(ArrayList<member> members)
            throws CancellationException {
        // MenuLogin throws an error if user cancels logging in. Use this to resume session as current user OR terminate
        Scanner input = new Scanner(System.in);
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
                currentUser = findMember(members, memberID);
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

    private static void MenuMember(ArrayList<member> members, member currentUser) {
        Scanner input = new Scanner(System.in);
        int choice;
        member target;
        boolean terminate = false;
        int page = 1;

        do {
            target = null;
            System.out.print("[ Members ]\n");
            // display the list
            ShowMemberTable(members, page);
            boolean hasLastPage = members.size() > 0 && page > 1;
            boolean hasNextPage = members.size() > PAGELENGTH && (page == 1 || members.size() > page*PAGELENGTH);

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create\n" +
                        " 2: Modify\n" +
                        " 3: Delete\n" +
                        " 4: Back\n");
            else {
                System.out.println("Only admins can modify users");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            if (!input.hasNextInt()) {
                System.out.println("[!] Please enter a valid option.\n");
                input.nextLine();
                continue;
            }
            choice = Integer.parseInt(input.nextLine());
            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3) {
                    while (target == null) {
                        System.out.print("Target userID: ");
                        while (!input.hasNextInt()) {
                            System.out.println("[!] Invalid ID\nTarget userID: ");
                            input.nextLine();
                        }
                        target = findMember(members, Integer.parseInt(input.next())); // we have an integer, find the them in the table

                        if (target == null)
                            System.out.println("[!] Invalid ID\n");
                    }
                }
                switch (choice) {
                    case 1: // create
                        members.add(new member());
                        break;
                    case 2: // modify
                        target.modify(); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                        if (target == currentUser) {
                            System.out.println("[!] You cannot delete your own account");
                        } else {
                            System.out.printf("Do you really want to delete user \"%s\" id \"%s\"? (Y/N) ",
                                    target.getName(),
                                    target.getId());
                            String str = input.nextLine();
                            while (str.length() == 0) {
                                str = input.nextLine();
                            }
                            char c = str.charAt(0);
                            if (c == 'y' || c == 'Y') {
                                members.remove(target);
                                System.out.println("Confirmed. User was deleted.");
                            } else {
                                System.out.println("Aborted. User was not deleted.");
                            }
                        }
                        break;
                    case 4: // logout
                        terminate = true;
                        break;
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
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
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);
    }

    private static void ShowMemberTable(ArrayList<member> members, int page) {
        System.out.println("|  id |  color  |      Name      | Admin | Additional information ");
        int i=0;
        for (member o : members) { // prints only the members on the current "page"
            if (i >= (page-1)*PAGELENGTH && i < page*PAGELENGTH)
                System.out.println(o.toColumns());
            i++;
        }
        boolean hasLastPage = members.size() > 0 && page > 1;
        boolean hasNextPage = members.size() > PAGELENGTH && (page == 1 || members.size() > page*PAGELENGTH);
        if (members.size() > PAGELENGTH) { // shows previous and next page hints
            System.out.print("| ");
            if (hasLastPage)
                System.out.print("< 5: Last Page ");
            else if (hasNextPage)
                System.out.print("6: Next Page >");
            System.out.println();
        }
    }

    private static void MenuTeam(ArrayList<team> teams, member currentUser) {
        Scanner input = new Scanner(System.in);
        int choice;
        team target;
        boolean terminate = false;
        int page = 1;

        do {
            target = null;
            System.out.print("[ Teams ]\n");
            // display the list
            ShowTeamsTable(teams, page);
            boolean hasLastPage = teams.size() > 0 && page > 1;
            boolean hasNextPage = teams.size() > PAGELENGTH && (page == 1 || teams.size() > page*PAGELENGTH);

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create\n" +
                        " 2: Modify\n" +
                        " 3: Delete\n" +
                        " 4: Back\n");
            else {
                System.out.println("Only admins can modify teams");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            if (!input.hasNextInt()) {
                System.out.println("[!] Please enter a valid option.\n");
                input.nextLine();
                continue;
            }
            choice = Integer.parseInt(input.nextLine());
            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3) {
                    while (target == null) {
                        System.out.print("Target userID: ");
                        while (!input.hasNextInt()) {
                            System.out.println("[!] Invalid ID\nTarget userID: ");
                            input.nextLine();
                        }
                        target = findTeam(teams, Integer.parseInt(input.next())); // we have an integer, find the them in the table

                        if (target == null)
                            System.out.println("[!] Invalid ID\n");
                    }
                }
                switch (choice) {
                    case 1: // create
                        //System.out.println("Feature unavailable.\n");
                        teams.add(new team());
                        break;
                    case 2: // modify
                        target.modify(); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                            System.out.printf("Do you really want to delete team \"%s\" id \"%s\"? (Y/N) ",
                                    target.getTeamName(),
                                    target.getId());
                            String str = input.nextLine();
                            while (str.length() == 0) {
                                str = input.nextLine();
                            }
                            char c = str.charAt(0);
                            if (c == 'y' || c == 'Y') {
                                teams.remove(target);
                                System.out.println("Confirmed. Team was deleted.");
                            } else {
                                System.out.println("Aborted. Team was not deleted.");
                            }

                        break;
                    case 4: // logout
                        terminate = true;
                        break;
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
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
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);

    }

    private static void ShowTeamsTable(ArrayList<team> teams, int page) {
        System.out.println("|  id |  color  |      Team Name       | Admin | Additional information ");
        int i=0;
        for (team o : teams) { // prints only the members on the current "page"
            if (i >= (page-1)*PAGELENGTH && i < page*PAGELENGTH)
                System.out.println(o.toColumns());
            i++;
        }
        boolean hasLastPage = teams.size() > 0 && page > 1;
        boolean hasNextPage = teams.size() > PAGELENGTH && (page == 1 || teams.size() > page*PAGELENGTH);
        if (teams.size() > PAGELENGTH) { // shows previous and next page hints
            System.out.print("| ");
            if (hasLastPage)
                System.out.print("< 5: Last Page ");
            else if (hasNextPage)
                System.out.print("6: Next Page >");
            System.out.println();
        }
    }

    private static void MenuTask(ArrayList<task> tasks, member currentUser) {
        Scanner input = new Scanner(System.in);
        int choice;
        task target;
        boolean terminate = false;
        int page = 1;

        do {
            target = null;
            System.out.print("[ Tasks ]\n");
            // display the list
            ShowTasksTable(tasks, page);
            boolean hasLastPage = tasks.size() > 0 && page > 1;
            boolean hasNextPage = tasks.size() > PAGELENGTH && (page == 1 || tasks.size() > page*PAGELENGTH);

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create\n" +
                        " 2: Modify\n" +
                        " 3: Delete\n" +
                        " 4: Back\n");
            else {
                System.out.println("Only admins can modify tasks");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            if (!input.hasNextInt()) {
                System.out.println("[!] Please enter a valid option.\n");
                input.nextLine();
                continue;
            }
            choice = Integer.parseInt(input.nextLine());
            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3) {
                    while (target == null) {

                        System.out.print("Target task name: ");
                        //while (!input.hasNextInt()) {
                        //    System.out.println("[!] Invalid name\nTarget task name: ");
                        //    input.nextLine();
                        //}
                        target = findTask(tasks, input.next()); // we have an name, find the them in the table

                        if (target == null)
                            System.out.println("[!] Invalid name\n");
                    }
                }
                switch (choice) {
                    case 1: // create
                        tasks.add(new task());
                        break;
                    case 2: // modify
                        target.modify(); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                        //if (target == currentUser) {
                        //    System.out.println("[!] You cannot delete your own account");
                        //} else {
                        System.out.printf("Do you really want to delete task \"%s\" id \"%s\"? (Y/N) ",
                                target.getName(),
                                target.getID());
                        String str = input.nextLine();
                        while (str.length() == 0) {
                            str = input.nextLine();
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
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
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
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);
    }

    private static void ShowTasksTable(ArrayList<task> tasks, int page) {
        System.out.println("|  id |  color  |      Name      | Assigned To |           Due Date           | Subtasks ");
        int i=0;
        for (task o : tasks) { // prints only the tasks on the current "page"
            if (i >= (page-1)*PAGELENGTH && i < page*PAGELENGTH)
                System.out.println(o.toColumns());
            i++;
        }
        boolean hasLastPage = tasks.size() > 0 && page > 1;
        boolean hasNextPage = tasks.size() > PAGELENGTH && (page == 1 || tasks.size() > page*PAGELENGTH);
        if (tasks.size() > PAGELENGTH) { // shows previous and next page hints
            System.out.print("| ");
            if (hasLastPage)
                System.out.print("< 5: Last Page ");
            else if (hasNextPage)
                System.out.print("6: Next Page >");
            System.out.println();
        }
    }

    private static void MenuCategory(ArrayList<category> categories, member currentUser) {
        Scanner input = new Scanner(System.in);
        int choice;
        category target;
        boolean terminate = false;
        int page = 1;

        do {
            target = null;
            System.out.print("[ Category ]\n");
            // display the list
            ShowCategoryTable(categories, page);
            boolean hasLastPage = categories.size() > 0 && page > 1;
            boolean hasNextPage = categories.size() > PAGELENGTH && (page == 1 || categories.size() > page*PAGELENGTH);

            if (currentUser.isAdmin()) // only admins can do the following:
                System.out.print(" 1: Create\n" +
                        " 2: Modify\n" +
                        " 3: Delete\n" +
                        " 4: Back\n");
            else {
                System.out.println("Only admins can modify users");
                System.out.print(" 1: Back\n");
            }

            System.out.print("Choice: ");
            if (!input.hasNextInt()) {
                System.out.println("[!] Please enter a valid option.\n");
                input.nextLine();
                continue;
            }
            choice = Integer.parseInt(input.nextLine());
            if (currentUser.isAdmin()) {
                if (choice == 2 || choice == 3) {
                    while (target == null) {
                        System.out.print("Target Category Name: ");
                        while (!input.hasNextLine()) {
                            System.out.println("[!] Invalid Name\nTarget Category Name: ");
                            input.nextLine();
                        }
                        target = findCategory(categories, input.nextLine()); // we have an integer, find the them in the table

                        if (target == null)
                            System.out.println("[!] Invalid Name\n");
                    }
                }
                switch (choice) {
                    case 1: // create
                        categories.add(new category());
                        break;
                    case 2: // modify
                        target.modify(); //each class should have a modify function, similar to how the constructor works
                        break;
                    case 3: // delete
                        System.out.printf("Do you really want to delete category \"%s\"? (Y/N) ",
                                target.getCategoryName());
                        String str = input.nextLine();
                        while (str.length() == 0) {
                            str = input.nextLine();
                        }
                        char c = str.charAt(0);
                        if (c == 'y' || c == 'Y') {
                            categories.remove(target);
                            System.out.println("Confirmed. User was deleted.");
                        } else {
                            System.out.println("Aborted. User was not deleted.");
                        }
                        break;
                    case 4: // logout
                        terminate = true;
                        break;
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
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
                    case 5: // page backward (only works if there is a previous page)
                        if (hasLastPage) {
                            page -= 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    case 6: //page forward (only works if there is a next page)
                        if (hasNextPage) {
                            page += 1;
                            break;
                        }
                        System.out.println("[!] Please enter a valid option");
                        break;
                    default:
                        System.out.println("[!] Please enter a valid option");
                        break;
                }
            }
        } while (!terminate);
    }

    private static void ShowCategoryTable(ArrayList<category> categories, int page) {
        System.out.println("|      Name      |  Color  | Description");
        int i=0;
        for (category o : categories) { // prints only the members on the current "page"
            if (i >= (page-1)*PAGELENGTH && i < page*PAGELENGTH)
                System.out.println(o.toColumns());
            i++;
        }
        boolean hasLastPage = categories.size() > 0 && page > 1;
        boolean hasNextPage = categories.size() > PAGELENGTH && (page == 1 || categories.size() > page*PAGELENGTH);
        if (categories.size() > PAGELENGTH) { // shows previous and next page hints
            System.out.print("| ");
            if (hasLastPage)
                System.out.print("< 5: Last Page ");
            else if (hasNextPage)
                System.out.print("6: Next Page >");
            System.out.println();
        }
    }

    // functions to search for an object in the arrays
    // If they don't find a match, they return null.
    // After searching, ALWAYS do something like this:
    // member = findMember(members, 1)
    // if (member == null)
    //    //exception code

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

    private static task findTask(ArrayList<task> tasks, String taskName) {
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
