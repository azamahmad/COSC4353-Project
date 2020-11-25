
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class taskTest {

    @Test
    void getId() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals(4, t.getId());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getName() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals("Remember the Alamo", t.getName());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getDescription() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals("Test Descriptor", t.getDescription());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getSubtasks() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals("Yes.", t.getSubtasks());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getDueDate() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            System.out.println(t.getDueDate());
            assertEquals(df.parse("2020-09-21 04:23 PM"), t.getDueDate());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getAssignedTo() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        member check = new member("Jimmy Johnson", "1", "Red", "S", true);
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), check, df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals(check, t.getAssignedTo());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getCreatedOn() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals(df.parse("2020-08-21 04:23 PM"), t.getCreatedOn());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getCreatedBy() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        member check = new member("John Johnson", "1", "Red", "S", true);
        try {
            task t = new task("Remember the Alamo", check, new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals(check, t.getCreatedBy());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getStatus() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals(TaskStatus.active, t.getStatus());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void toggleStatus() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            t.toggleStatus();
            assertEquals(TaskStatus.done, t.getStatus());
        }
        catch (ParseException ignore){

        }
    }

    @Test
    void getColor() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try {
            task t = new task("Remember the Alamo", new member("John Johnson", "1", "Red", "S", true), new member("Jimmy Johnson", "1", "Red", "S", true), df.parse("2020-08-21 04:23 PM"), df.parse("2020-09-21 04:23 PM"), "Breen",
                    "Test Descriptor", TaskStatus.active, false, null, "Yes.");
            assertEquals("Breen", t.getColor());
        }
        catch (ParseException ignore){

        }
    }
}