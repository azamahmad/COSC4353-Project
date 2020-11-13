import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class task{
    private int ID;
    private static int currentID = 1;
    private String name;
    private String description;
    private String subtasks;
    private Date dueDate;
    private String assignedTo;
    private Date createdOn;
    private String createdBy;
    private String status;
    private String color;

    public task(){
//        this.ID = nextID++;
//        name        = "unnamed task"
//        description = "N/A";
//        subtasks    = "";
//        dueDate     = new Date();
//        assignedTo  = new Member();
//        createdOn   = new Date();
//        createdBy   = new Member();
//        status      = ""; //what type?
//        color       = Color.blue;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        boolean validated;

        Scanner input = new Scanner(System.in);
        do {
            System.out.print("Task name: ");
            name = input.nextLine();
        } while (name.length() == 0);
        do {
            System.out.print("Task description: ");
            description = input.nextLine();
        } while (description.length() == 0);
        System.out.print("Sub tasks (separated by commas): ");
        subtasks = input.nextLine();
        do {
            System.out.print("Due date for task(MM-DD-YYYY HH:MM AM/PM): ");
            try {
                dueDate = df.parse(input.nextLine());
                validated = true;
            } catch(java.text.ParseException e) {
                System.out.println("Invalid date.");
                validated = false;
            }
        } while (!validated);
        System.out.print("Assigned to: ");
        //TODO: find and assign the member
        assignedTo = input.nextLine();
        createdOn = new Date();
        //TODO: receive the logged in user as an argument
        createdBy = "system";
        status = "active";
        System.out.print("Color: ");
        color = input.nextLine();
    }

    public task(String name,
                String description,
                String subtasks,
                Date dueDate,
                String assignedTo,
                Date createdOn,
                String createdBy,
                String status,
                String color) {
        this.ID = currentID++;
        this.name = name;
        this.description = description;
        this.subtasks = subtasks;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.status = status;
        this.color = color;
    }

    public void modify() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

        System.out.println("Enter a blank line to keep current value.");
        Scanner input = new Scanner(System.in);
        String str;
        System.out.printf("Task name(%s): ", name);
        str = input.nextLine();
        if (str.length() > 0) // do this to keep the original value if no input was given
            name = str;
        System.out.println("Task name is " + name);

        System.out.printf("Task description(%s): ", description);
        str = input.nextLine();
        if (str.length() > 0)
            description = str;
        System.out.println("Task description is " + description);
        System.out.printf("Subtasks (separated by commas)(%s): ", subtasks);
        str = input.nextLine();
        if (str.length() > 0)
            subtasks = str;
        System.out.println("Subtasks are " + subtasks);
        System.out.printf("Due Date (MM-DD-YYYY HH:MM AM/PM)(%s): ", dueDate);
            try {
                dueDate = df.parse(input.nextLine());
            } catch (java.text.ParseException e) {
                System.out.println("Invalid date.");
            }
        System.out.println("Due Date is " + dueDate);
        System.out.printf("Assigned to(%s): ", assignedTo);
        str = input.nextLine();
        if (str.length() > 0)
            assignedTo = str;
        System.out.println("Assigned to " + assignedTo);
        System.out.printf("Status(%s): ", status);
        str = input.nextLine();
        if (str.length() > 0)
            status = str;
        System.out.println("Status is " + status);
//        System.out.println("Password: ");
        System.out.printf("Color(%s): ", color);
        str = input.nextLine();
        if (str.length() > 0)
            color = str;
        System.out.println("Color is " + color);
    }

    void print() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy 'at' hh:mma");
        System.out.format("Task name: %s\n", name);
        System.out.format("Description: %s\n", description);
        System.out.format("Sub tasks: %s\n", subtasks);
        System.out.format("Due date: %s\n", df.format(dueDate));
        System.out.format("Assigned to: %s\n", assignedTo);
        System.out.format("Created on: %s\n", df.format(createdOn));
        System.out.format("Created by: %s\n", createdBy);
        System.out.format("Status: %s\n", status);
        System.out.format("Color: %s\n", color);
    }

    public String toColumns() {
        // format:           "|  id  |  color  |      Name      | Assigned To |          Due Date          | Subtasks "
        return String.format("| % 3d | %7s | %14s | %11s | %28s | %s",
                ID,
                color,
                name,
                assignedTo,
                dueDate,//admin ? "*" : " ",
                subtasks);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSubtasks() {
        return subtasks;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }
}
