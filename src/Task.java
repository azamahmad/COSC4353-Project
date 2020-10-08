import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class Task {
//    private int ID;
//    private static int nextID;
    private String name;
    private String description;
    private ArrayList<Task> subtasks;
    private Date dueDate;
    private Member assignedTo;
    private Date createdOn;
    private Member createdBy;
    private String status; //what type?
    private Color color;

    public Task(){ // this might be preferred
//        this.ID = nextID++;
        name        = "John Doe";
        description = "N/A";
        subtasks    = new ArrayList<Task>();
        dueDate     = new Date();
        assignedTo  = new Member();
        createdOn   = new Date();
        createdBy   = new Member();
        status      = ""; //what type?
        color       = Color.blue;
    }

    public Task(String name,
                String description,
                ArrayList<Task> subtasks,
                Date dueDate,
                Member assignedTo,
                Date createdOn,
                Member createdBy,
                String status,
                Color color) {
//        this.ID = nextID++;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Task> getSubtasks() {
        return subtasks;
    }

    public void addSubtasks(Task subtask) {
        this.subtasks.add(subtask);
    }

    public void removeSubtasks(Task subtask) {
        this.subtasks.add(subtask);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Member getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Member assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Member getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Member createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
