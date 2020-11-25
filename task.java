import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

enum TaskStatus {done, active}

public class task{
    private int ID;
    private static int currentID = 1;
    private String name;
    private String description;
    private String subtasks;
    private Date dueDate;
    private member assignedTo; // user
    private Date createdOn;
    private member createdBy; // user
    private TaskStatus status;
    private String color;
    //repeats tasks
    private boolean repeats;
    private Duration interval;
    private int successes = 0;
    private int failures = 0;

    public task(Scanner input, member currentUser){
        main.skipEmptyLine(input);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
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
        Date now = new Date();
        do {
            System.out.print("Due date for task(MM-DD-YYYY HH:MM AM/PM): ");
            try {
                dueDate = df.parse(input.nextLine());
                if (dueDate.after(now))
                    break;
                else
                    System.out.println("[!] Due date should not be in the past.");
            } catch(java.text.ParseException e) {
                System.out.println("Invalid date.");
            }
        } while (true);
        do {
            System.out.print("Does this task repeat?(Y/N) ");
            String str = "";
            while (str.length() == 0)
                str = input.next();
            char c = str.charAt(0);
            if (c == 'y' || c == 'Y') {
                repeats = true;
                do {
                    System.out.print("Tasks repeats every \"weeks days\": ");
                    int weeks, days;
                    try {
                        weeks = Integer.parseInt(input.next());
                        if (weeks < 0) {
                            System.out.println("[!] Weeks should be greater than 0");
                            continue;
                        }
                        days = Integer.parseInt(input.next());
                        if (days < 0) {
                            System.out.println("[!] Days should be greater than 0");
                            continue;
                        }
                    } catch (NumberFormatException ignore) {
                        continue;
                    }
                    interval = Duration.ofDays(weeks * 7 + days);
                    System.out.printf("Task repeats every %s", getIntervalString());
                    main.skipEmptyLine(input);
                    break;
                } while (true);
                break;
            } else if (c == 'n' || c == 'N') {
                repeats = false;
                break;
            }
        } while (true);
        do {
            System.out.println("[ Members ]");
            main.ShowMemberTable();
            System.out.print("Assigned to: ");
            String str = input.next();
            if (str.length() > 0) {
                try {
                    member assign = main.findMember(Integer.parseInt(str));
                    if (assign != null) {
                        assignedTo = assign;
                        break;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
                System.out.println("Invalid User ID");
            } else {
                break;
            }
        } while (true);
        System.out.printf("Assigned to %d (%s)\n", assignedTo.getId(), assignedTo.getName());
        ID = currentID++;
        createdOn = new Date();
        createdBy = currentUser;
        status = TaskStatus.active;
        System.out.print("Color: ");
        main.skipEmptyLine(input);
        color = input.nextLine();
    }

    public task(String name, member createdBy, member assignedTo, Date createdOn, Date dueDate, String color,
                String description, TaskStatus status, boolean repeats, Duration interval, String subtasks) {
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
        this.repeats = repeats;
        this.interval = interval;
    }

    public void modify(Scanner input) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        member assign;

        System.out.println("Enter a blank line to keep current value.");
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

        do {
            System.out.printf("Task repeats(%s)(Y, N, or blank to keep): ", repeats ? "Y, "+getIntervalString():'N');
            str = input.nextLine();
            if (str.length() == 0)
                break; // keep the current value
            char c = str.charAt(0);
            if (c == 'y' || c == 'Y') {
                do {
                    repeats = true;
                    System.out.print("Tasks repeats every \"weeks days\": ");
                    int weeks, days;
                    try {
                        weeks = Integer.parseInt(input.next());
                        if (weeks < 0) {
                            System.out.println("[!] Weeks should be greater than 0");
                            continue;
                        }
                        days = Integer.parseInt(input.next());
                        if (days < 0) {
                            System.out.println("[!] Days should be greater than 0");
                            continue;
                        }
                    } catch (NumberFormatException ignore) {
                        continue;
                    }
                    interval = Duration.ofDays(weeks * 7 + days);
                    System.out.printf("Task repeats every %s", getIntervalString());
                    main.skipEmptyLine(input); // b/c we used input.next() :pensive:
                    break;
                } while (true);
                break;
            } else if (c == 'n' || c == 'N') {
                repeats = false;
                break;
            }
        } while (true);

        do {
            System.out.printf("Assigned to(%s): ", assignedTo.getName());
            str = input.nextLine();
            if (str.length() > 0) {
                try {
                    assign = main.findMember(Integer.parseInt(str));
                    if (assign != null) {
                        assignedTo = assign;
                        break;
                    }
                } finally {
                    System.out.println("Invalid User ID");
                }
            } else {
                break;
            }
        } while (true);
        System.out.printf("Assigned to %d (%s)\n", assignedTo.getId(), assignedTo.getName());
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
        System.out.format("Assigned to: %s\n", assignedTo.getName());
        System.out.format("Created on: %s\n", df.format(createdOn));
        System.out.format("Created by: %s\n", createdBy);
        System.out.format("Status: %s\n", status);
        System.out.format("Color: %s\n", color);
    }

    public String toColumns() {
        // format:           "|  Id  | Status |      Name      |   Assigned To  |  color  |           Due Date           | Subtasks "
        // format:           "|  Id  | Status |      Name      |   Assigned To  |  color  |           Due Date           |    Interval     | Subtasks "
        updateDueDate(); // TODO: find better place to update the due date
        return String.format("| % 4d | %6s | %14s | %14s | %7s | %28s | %15s | %s",
                ID,
                status,
                name,
                assignedTo.getName(),
                color,
                dueDate,
                repeats ? getIntervalString() : "",
                subtasks);
    }

    public int getId() {
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

    public member getAssignedTo() {
        return assignedTo;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public member getCreatedBy() {
        return createdBy;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void toggleStatus() {
        if (status.equals(TaskStatus.active)) {
            status = TaskStatus.done;
        } else {
            status = TaskStatus.active;
        }
    }

    public String getColor() {
        return color;
    }

    public String getIntervalString() {
        StringBuilder str = new StringBuilder();
        int weeks = (int)Math.floor(interval.toDays()/7);
        int days  = (int)interval.toDays() % 7;
        if (weeks > 0) {
            str.append(weeks).append(" week");
            if (weeks > 1)
                str.append("s");
            if (days > 0)
                str.append(" ");
        }
        if (days > 0) {
            str.append(days).append(" day");
            if (days > 1)
                str.append("s");
        }
        if (weeks <= 0 && days <= 0)
            str.append("panik");
        return str.toString();
    }

    public boolean updateDueDate() {
        if (repeats) {
            LocalDateTime localDueDate = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); // convert date to local date
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(localDueDate)) { // we passed the due date
                do {
                    localDueDate = localDueDate.plusDays(interval.toDays()); // bump the due date by the interval
                    if (status.equals(TaskStatus.done)) {
                        successes += 1;
                        toggleStatus();
                    } else {
                        failures += 1;
                        // no need to toggle status, since its still active
                    }
                } while (now.isAfter(localDueDate)); // in case multiple deadlines passed without the app being launched
                dueDate = Date.from(localDueDate.atZone(ZoneId.systemDefault()).toInstant()); // ew convert back to date
                return true;
            }
        }
        return false;
    }

    public int getSuccesses(){
        return successes;
    }

    public int getFailures(){
        return failures;
    }
}
