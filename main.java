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
                    MenuMembers(members, currentUser);
                    break;
                case 2:
                    System.out.println("Not updated yet");
                    teams.add(new team());
                    break;
                case 3:
                    System.out.println("Not updated yet");
                    tasks.add(new task());
                    break;
                case 4:
                    System.out.println("Not updated yet");
                    categories.add(new category());
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
        members.add(new member("Gunther", "weakpassword", "Pink", false));    // user id 2
        members.add(new member("Mohammed Ali", "letmein", "Orange", false));  // user id 3
        members.add(new member("Elvis Presley", "letmein", "Blue", false));  // user id 4
        members.add(new member("Nina Carlson", "letmein", "Cyan", false));  // user id 5
        members.add(new member("Matthew Parker", "letmein", "Pink", false));  // user id 6
        members.add(new member("Rocky Rhodes", "letmein", "Brown", false));  // user id 7
        members.add(new member("Mike Wazowski", "letmein", "Green", false));  // user id 8
        members.add(new member("Freeza", "letmein", "Purple", false));  // user id 9
        members.add(new member("Chucky Cheese", "letmein", "Grey", false));  // user id 10
        members.add(new member("Hyper Baby", "letmein", "Brown", false));  // user id 11

    }

    // Menu screens

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

    private static void MenuMembers(ArrayList<member> members, member currentUser) {
        Scanner input = new Scanner(System.in);
        int choice;
        member target;
        boolean terminate = false;
        int page = 1;

        do {
            target = null;
            System.out.print("[ Members ]\n");
            // display the list
            MembersTable(members, page);
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
                        target.modify();
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

    private static void MembersTable(ArrayList<member> members, int page) {
        System.out.println("|  id |  color  |      Name      | Admin | Additional information ");
        int i=0;
        for (member o : members) {
            if (i >= (page-1)*PAGELENGTH && i < page*PAGELENGTH)
                System.out.println(o.toColumns());
            i++;
        }
        boolean hasLastPage = members.size() > 0 && page > 1;
        boolean hasNextPage = members.size() > PAGELENGTH && (page == 1 || members.size() > page*PAGELENGTH);
        if (members.size() > PAGELENGTH) {
            System.out.print("| ");
            if (hasLastPage)
                System.out.print("< 5: Last Page ");
            else if (hasNextPage)
                System.out.print("6: Next Page >");
            System.out.println();
        }
    }

    private static void MenuTeam(ArrayList<team> teams, member currentUser) {

    }

    private static void MenuTask(ArrayList<task> tasks, member currentUser) {

    }

    private static void MenuCategorie(ArrayList<category> categories, member currentUser) {

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
