import com.theironyard.Main;
import com.theironyard.Toilet;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by jeffryporter on 6/17/16.
 */
public class MainTest
{


    Connection startConnection() throws SQLException
    {
        Connection conn = DriverManager.getConnection(("jdbc:h2:mem:test"));
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void TestInsertSelectAndDelete() throws SQLException
    {

        Connection conn = startConnection();
        Toilet a = new Toilet(null, "The Iron Yard",32.1234,-79.2346,3,3,4,"asd2345f");
        Toilet b = new Toilet (null, "Starbucks",33.4123,-79.534,1,1,1,"aswef");
        Main.insertToilet(conn, a);
        Main.insertToilet(conn, b);
        ArrayList<Toilet> toilets = new ArrayList<>();
        toilets = Main.selectToilets(conn);

        assertTrue(toilets != null);
        assertTrue(toilets.get(0).getFacility().equals("The Iron Yard"));

        Main.deleteToilet(conn,toilets.get(0).getId());
        toilets = new ArrayList<>();
        toilets = Main.selectToilets(conn);
        conn.close();

        assertTrue(toilets.size() == 1);
    }

    @Test
    public void testUpdate() throws SQLException
    {
        Connection conn = startConnection();
        Toilet a = new Toilet(null,"The Iron Yard",32.1234,-79.2346,1,3,4, "a2345sdf");
        Toilet b = new Toilet (1,"Starbucks",33.4123,-79.534,2,7,5,"as2135df");
        Main.insertToilet(conn, a);
        Main.updateToilet(conn,b);
        ArrayList<Toilet> users = Main.selectToilets(conn);
        conn.close();
        assertTrue(users.get(0).getFacility().equals("The Iron Yard"));
        assertTrue(users.get(0).getCapacity() == 3);
        assertTrue(users.get(0).getLat() == 32.1234);
    }

    @Test
    public void testindividualSelect() throws SQLException
    {
        Connection conn = startConnection();
        Toilet a = new Toilet(null,"The Iron Yard",32.1234,-79.2346,1,3,4, "asdf");
        Main.insertToilet(conn, a);
        Toilet b = Main.selectToilet(conn, 1);

        assertTrue(a.getFacility().equals(b.getFacility()));

    }
}
