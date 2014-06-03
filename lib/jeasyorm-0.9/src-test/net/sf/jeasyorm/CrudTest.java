package net.sf.jeasyorm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;

import net.sf.jeasyorm.model.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CrudTest extends AbstractEntityManagerTest {
    
    public CrudTest(String name, String driver, String url,
            String username, String password) throws ClassNotFoundException {
        super(name, driver, url, username, password);
    }

    @Test 
    public void testCRUD() throws SQLException {
        Connection conn = getConnection();
        EntityManager em = getEntityManager(conn);
        // insert a person
        Person p1 = new Person("Franz", "Huber");
        em.insert(p1);
        assertNotNull(p1.getId());
        // insert another person
        Person p2 = new Person("Max", "Mayer");
        em.insert(p2);
        assertNotNull(p2.getId());
        // load the first person and compare with saved person
        Person p3 = em.load(Person.class, p1.getId());
        assertNotNull(p3);
        assertEquals(p3.getId(), p1.getId());
        assertEquals(p3.getFirstName(), p1.getFirstName());
        assertEquals(p3.getLastName(), p1.getLastName());
        // change name of second person
        p2.setLastName("Meier");
        em.update(p2);
        // load the second person and check the data
        p3 = em.load(Person.class, p2.getId());
        assertNotNull(p3);
        assertEquals(p3.getId(), p2.getId());
        assertEquals(p3.getFirstName(), p2.getFirstName());
        assertEquals(p3.getLastName(), p2.getLastName());
        // load the first person - it must be unchanged
        p3 = em.load(Person.class, p1.getId());
        assertEquals(p3.getLastName(), p1.getLastName());
        // delete the second person
        em.delete(p2);
        // loading the second person should now return null
        p3 = em.load(Person.class, p2.getId());
        assertNull(p3);
        // loading the first person should still give a result
        p3 = em.load(Person.class, p1.getId());
        assertNotNull(p3);
        conn.close();
    }
    
}
