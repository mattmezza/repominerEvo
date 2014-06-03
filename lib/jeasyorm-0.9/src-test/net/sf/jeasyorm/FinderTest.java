package net.sf.jeasyorm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jeasyorm.model.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FinderTest extends AbstractEntityManagerTest {
    
    public FinderTest(String name, String driver, String url,
            String username, String password) throws ClassNotFoundException {
        super(name, driver, url, username, password);
    }

    @Test
    public void testFinders() throws SQLException {
        Connection conn = getConnection();
        EntityManager em = getEntityManager(conn);
        // insert data
        em.insert(new Person("Franz", "Huber"));
        em.insert(new Person("Max", "Meier"));
        em.insert(new Person("Max", "Mayer"));
        em.insert(new Person("Martin", "Muster"));
        em.insert(new Person("Max", "Huber"));
        // check unique finder
        Person p1 = em.findUnique(Person.class, "where last_name = ?", "Mayer");
        assertNotNull(p1);
        assertNotNull(p1.getId());
        assertEquals("Max", p1.getFirstName());
        assertEquals("Mayer", p1.getLastName());
        Person p2 = em.findUnique(Person.class, "where last_name like ?", "May%");
        assertNotNull(p2);
        assertEquals(p2.getId(), p1.getId());
        Person p3 = em.findUnique(Person.class, "where last_name = ?", "X");
        assertNull(p3);
        try {
            p3 = em.findUnique(Person.class, "where first_name = ?", "Max");
            throw new RuntimeException("Must throw an exception!");
        } catch (RuntimeSQLException e) {
            // OK
        }
        // check list finder
        List<Person> ps = em.find(Person.class, "where first_name = ?", "Franz");
        assertEquals(1, ps.size());
        p1 = ps.get(0);
        assertNotNull(p1);
        assertNotNull(p1.getId());
        assertEquals("Franz", p1.getFirstName());
        assertEquals("Huber", p1.getLastName());
        ps = em.find(Person.class, "where first_name = ?", "Max");
        assertEquals(3, ps.size());
        ps = em.find(Person.class, "order by first_name, last_name");
        assertEquals(5, ps.size());
        assertEquals("Franz", ps.get(0).getFirstName());
        assertEquals("Martin", ps.get(1).getFirstName());
        assertEquals("Huber", ps.get(2).getLastName());
        assertEquals("Mayer", ps.get(3).getLastName());
        assertEquals("Meier", ps.get(4).getLastName());
        // check list finder with from
        ps = em.find(Person.class, "from tperson where first_name = ? and last_name = ?", "Franz", "Huber");
        assertEquals(1, ps.size());
        // check list finder with select
        ps = em.find(Person.class, "select * from tperson order by last_name");
        assertEquals(5, ps.size());
        // check scalar finder
        List<String> lastNames = em.find(String.class, "select last_name from tperson where last_name > ? order by last_name", "M");
        assertEquals(3, lastNames.size());
        assertEquals("Mayer", lastNames.get(0));
        assertEquals("Meier", lastNames.get(1));
        assertEquals("Muster", lastNames.get(2));
        conn.close();
    }
    
    @Test
    public void testPaging() throws SQLException {
        Connection conn = getConnection();
        EntityManager em = getEntityManager(conn);
        // insert data
        em.insert(new Person("Franz", "Huber"));
        em.insert(new Person("Max", "Huber"));
        em.insert(new Person("Mathias", "Matz"));
        em.insert(new Person("Max", "Mayer"));
        em.insert(new Person("Josef", "Meier"));
        em.insert(new Person("Matthias", "Meier"));
        em.insert(new Person("Max", "Meier"));
        em.insert(new Person("Raimund", "Meier"));
        em.insert(new Person("Roman", "Meier"));
        em.insert(new Person("Susanne", "Meier"));
        em.insert(new Person("Martin", "Muster"));
        em.insert(new Person("Robert", "Rot"));
        // check first page
        Page<Person> ps = em.find(Person.class, 0, 3, "from tperson order by last_name, first_name");
        assertEquals(3, ps.size());
        assertEquals(12, ps.totalSize());
        assertEquals(0, ps.pageNumber());
        assertEquals(3, ps.pageSize());
        assertEquals("Franz", ps.get(0).getFirstName());
        assertEquals("Huber", ps.get(0).getLastName());
        assertEquals("Max", ps.get(1).getFirstName());
        assertEquals("Huber", ps.get(1).getLastName());
        assertEquals("Mathias", ps.get(2).getFirstName());
        assertEquals("Matz", ps.get(2).getLastName());
        // check second page
        ps = em.find(Person.class, 1, 3, "from tperson order by last_name, first_name");
        assertEquals(3, ps.size());
        assertEquals(12, ps.totalSize());
        assertEquals("Max", ps.get(0).getFirstName());
        assertEquals("Mayer", ps.get(0).getLastName());
        assertEquals("Josef", ps.get(1).getFirstName());
        assertEquals("Meier", ps.get(1).getLastName());
        assertEquals("Matthias", ps.get(2).getFirstName());
        assertEquals("Meier", ps.get(2).getLastName());
        // get last partial page
        ps = em.find(Person.class, 2, 5, "from tperson order by last_name, first_name");
        assertEquals(2, ps.size());
        assertEquals(12, ps.totalSize());
        assertEquals("Martin", ps.get(0).getFirstName());
        assertEquals("Muster", ps.get(0).getLastName());
        assertEquals("Robert", ps.get(1).getFirstName());
        assertEquals("Rot", ps.get(1).getLastName());
        // get page with where
        ps = em.find(Person.class, 1, 2, "from tperson where last_name = 'Meier' order by first_name");
        assertEquals(2, ps.size());
        assertEquals(6, ps.totalSize());
        assertEquals(1, ps.pageNumber());
        assertEquals(2, ps.pageSize());
        assertEquals("Max", ps.get(0).getFirstName());
        assertEquals("Meier", ps.get(0).getLastName());
        assertEquals("Raimund", ps.get(1).getFirstName());
        assertEquals("Meier", ps.get(1).getLastName());
        conn.close();
    }
    
    @Test
    public void testIterator() throws SQLException {
        Connection conn = getConnection();
        EntityManager em = getEntityManager(conn);
        // insert data
        em.insert(new Person("Franz", "Huber"));
        em.insert(new Person("Max", "Huber"));
        em.insert(new Person("Mathias", "Matz"));
        em.insert(new Person("Max", "Mayer"));
        em.insert(new Person("Josef", "Meier"));
        em.insert(new Person("Matthias", "Meier"));
        // read data
        List<String> ps = new ArrayList<String>();
        Iterator<Person> it = em.iterator(Person.class, "order by first_name");
        while (it.hasNext()) ps.add(it.next().getFirstName());
        assertEquals(6, ps.size());
        assertEquals("Franz", ps.get(0));
        assertEquals("Josef", ps.get(1));
        assertEquals("Mathias", ps.get(2));
        assertEquals("Matthias", ps.get(3));
        assertEquals("Max", ps.get(4));
        assertEquals("Max", ps.get(5));
        conn.close();
    }
    
}
