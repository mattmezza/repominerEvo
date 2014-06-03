package net.sf.jeasyorm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.sf.jeasyorm.model.MyPerson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AnnotationTest extends AbstractEntityManagerTest {
    
    public AnnotationTest(String name, String driver, String url,
            String username, String password) throws ClassNotFoundException {
        super(name, driver, url, username, password);
    }

    @Test
    public void testAnnotations() throws SQLException {
        Connection conn = getConnection();
        EntityManager em = getEntityManager(conn);
        // insert data
        em.insert(new MyPerson("Franz", "Huber"));
        em.insert(new MyPerson("Max", "Meier"));
        // check unique finder
        MyPerson p1 = em.findUnique(MyPerson.class, "where last_name = ?", "Huber");
        assertNotNull(p1);
        assertNotNull(p1.getMyId());
        assertEquals("Franz", p1.getFirstName());
        assertEquals("Huber", p1.getName());
        // check finder
        List<MyPerson> ps = em.find(MyPerson.class, "order by last_name");
        assertEquals(2, ps.size());
        conn.close();
    }
    
}
