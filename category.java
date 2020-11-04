import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class category {
    private String categoryName, color, description;
    private int id;
    private static int currentID = 1; // keeps id's unique
    private member createdBy;
    private Date createdOn;
    private ArrayList<task> categoryTasks;

    category(Scanner input, member currentUser) {
        main.skipEmptyLine(input);
        createdBy = currentUser;
        createdOn = new Date();
        System.out.print("Enter new category name: ");
        categoryName = input.nextLine();
        id = currentID++;
        System.out.println("ID is " + id);
        //categories.add(categoryName);
        System.out.println("Created category " + categoryName);

        System.out.print("Enter color for new category: ");
        color = input.nextLine();
        System.out.println("Category color: " + color);

        System.out.println("Enter description for category: ");
        description = input.nextLine();
        System.out.println(categoryName + " description: " + description);

        categoryTasks = new ArrayList<>();
        menuAddTask(input);
    }

    category(String categoryName, member createdBy, Date createdOn, String color, String description) {
        this.id = currentID++;
        this.categoryName = categoryName;
        this.color = color;
        this.description = description;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.categoryTasks = new ArrayList<>();
    }

    public void modify() {
        System.out.println("Enter a blank line to keep current value.");
        Scanner input = new Scanner(System.in);
        String str;
        System.out.printf("Category name(%s): ", categoryName);
        str = input.nextLine();
        if (str.length() > 0) // do this to keep the original value if no input was given
            categoryName = str;
        System.out.println("Category name is " + categoryName);
        System.out.printf("Category color(%s): ", color);
        str = input.nextLine();
        if (str.length() > 0)
            color = str;
        System.out.println("Color is " + color);
        System.out.printf("Additional information( %s ): ", description);
        str = input.nextLine();
        if (str.length() > 0)
            description = str;
        System.out.println("Description is " + description);

        menuAddTask(input);
        menuRemoveTask(input);
    }
    void print(){
        System.out.println("Category name is: " + categoryName);
        System.out.println("Category color: " + color);
        System.out.println("Description: " + description);
    }

    public String toColumns() {
        // format:           "|  Id  |      Name      |  Color  |   Created By   |          Created On          | Description"
        return String.format("| % 4d | %14s | %7s | %14s | %28s | %s",
                id,
                categoryName,
                color,
                createdBy.getName(),
                createdOn,
                description);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public member getCreatedBy() {
        return createdBy;
    }

    public int getId() {
        return id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public boolean hasTask(task task) {
        return categoryTasks.contains(task);
    }

    public boolean addTask(task task) { // returns true if member was successfully added
        if (this.hasTask(task)) {
            return false;
        } else {
            categoryTasks.add(task);
            return true;
        }
    }

    public boolean removeTask(task task) { // returns true if member was successfully removed
        if (!this.hasTask(task)) {
            return false;
        } else {
            categoryTasks.remove(task);
            return true;
        }
    }

    public void showCategory() {
        System.out.printf("[ Category: %s ]\n", categoryName);
        if (description.length() > 0)
            System.out.printf("More info: %s\n", description);
        System.out.printf("Color: %s\n", color);
        if (categoryTasks.size() > 0) {
            System.out.println("|  Id  | Status |      Name      |   Assigned To  |  color  |           Due Date           | Subtasks ");
            for (task task : categoryTasks)
                System.out.println(task.toColumns());
        } else {
            System.out.println("No category tasks");
        }
        System.out.println();
    }

    public void menuAddTask(Scanner input) {
        System.out.println("[ Tasks ]");
        main.ShowTasksTable();
        System.out.println("[!] Add Tasks to the category");
        task target = null;
        while (true) {
            System.out.print("Select task ID(None to cancel): ");
            String str = input.nextLine();
            if (str.length() == 0)
                break;
            try {
                target = main.findTask(Integer.parseInt(str)); // we have an integer, find the them in the table
            } catch (NumberFormatException ignore){
            }
            if (target == null) {
                System.out.println("[!] Invalid ID");
                continue;
            }
            if (this.addTask(target)) {
                System.out.printf("Added %d (%s) to the category\n", target.getId(), target.getName());
            } else {
                System.out.printf("[!] %d (%s) is already in the category\n", target.getId(), target.getName());
            }
        }
    }

    public void menuRemoveTask(Scanner input) {
        System.out.println("[ Tasks ]");
        showCategory();
        System.out.println("[!] Remove Tasks to the category");
        task target = null;
        while (true) {
            System.out.print("Select user ID(None to cancel): ");
            String str = input.nextLine();
            if (str.length() == 0)
                break;
            try {
                target = main.findTask(Integer.parseInt(str)); // we have an integer, find the them in the table
            } catch (NumberFormatException ignore){
            }
            if (target == null) {
                System.out.println("[!] Invalid ID");
                continue;
            }
            if (this.removeTask(target)) {
                System.out.printf("Removed %d (%s) from the category\n", target.getId(), target.getName());
            } else {
                System.out.printf("[!] %d (%s) is not in the category\n", target.getId(), target.getName());
            }
        }

    }
}
