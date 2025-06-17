import junit.framework.TestCase;

public class UserTest extends TestCase {
    public void setUp() throws Exception {

    }
    public void test() {
        User user = new User("guga", "shreki");
        assertEquals("guga", user.getUserName());
        assertEquals("shreki", user.getPassword());
    }
}
