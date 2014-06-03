package net.sf.jeasyorm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import net.sf.jeasyorm.model.Note;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DatatypeTest extends AbstractEntityManagerTest {
    
    public DatatypeTest(String name, String driver, String url,
            String username, String password) throws ClassNotFoundException {
        super(name, driver, url, username, password);
    }

    @Test
    public void testLOBs() throws SQLException {
        Connection conn = getConnection();
        EntityManager em = getEntityManager(conn);
        // insert note
        Note n = new Note("Content", "attachment".getBytes());
        em.insert(n);
        assertNotNull(n.getId());
        // get note
        Note n1 = em.load(Note.class, n.getId());
        assertNotNull(n1);
        assertEquals(n.getId(), n1.getId());
        assertEquals(n.getContent(), n1.getContent());
        assertArrayEquals(n.getAttachment(), n1.getAttachment());
        conn.close();
    }

}
