import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class teamTest {


    @org.junit.jupiter.api.Test
    void getTeamName() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try{
            team t = new team("Big Brains Team", main.findMember(5), df.parse("2020-08-21 04:23 PM"), "Pink", "Brain big");
            assertEquals("Big Brains Team", t.getTeamName());
        }
        catch (ParseException ignore){

        }


    }

    @org.junit.jupiter.api.Test
    void getColor() {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        try{
            team t = new team("Big Brains Team", main.findMember(5), df.parse("2020-08-21 04:23 PM"), "Red", "Brain big");
            assertEquals("Red", t.getColor());
        }
        catch (ParseException ignore){

        }
    }



}