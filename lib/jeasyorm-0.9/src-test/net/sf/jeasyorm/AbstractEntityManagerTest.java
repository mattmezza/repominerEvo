package net.sf.jeasyorm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.runners.Parameterized.Parameters;

public class AbstractEntityManagerTest {

    private String name;
    private String url;
    private String username;
    private String password;
    
    public AbstractEntityManagerTest(String name, String driver, String url, String username, String password) 
            throws ClassNotFoundException {
        this.name = name;
        Class.forName(driver);
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    protected EntityManager getEntityManager(Connection conn) {
        return EntityManager.getInstance(name, conn);
    }
    
    @Before
    public void setUp() throws IOException, SQLException {
        String directory = "tmp/" + name;
        if (directory != null && !directory.trim().equals("")) {
            delete(new File(directory));
        }
        Connection conn = getConnection();
        InputStream is = this.getClass().getResourceAsStream(name + ".sql");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Utils.executeScript(conn, br, true);
        br.close();
        conn.close();
    }
    
    private void delete(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) delete(f);
        }
        file.delete(); 
    }
    
    @Parameters
    public static Collection<Object[]> getParameters() {
        Collection<Object[]> params = new ArrayList<Object[]>();
        addParameters(params, "hsqldb", "org.hsqldb.jdbcDriver");
        addParameters(params, "h2", "org.h2.Driver");
        addParameters(params, "derby", "org.apache.derby.jdbc.EmbeddedDriver");
        addParameters(params, "mysql", "com.mysql.jdbc.Driver");
        addParameters(params, "postgresql", "org.postgresql.Driver");
        addParameters(params, "oracle", "oracle.jdbc.OracleDriver");
        return params;
    }
    
    private static void addParameters(Collection<Object[]> params, String name, String driver) {
        ResourceBundle bundle = ResourceBundle.getBundle("connection");
        try {
            String url = bundle.getString(name + ".url");
            String username = bundle.getString(name + ".username");
            String password = bundle.getString(name + ".password");
            params.add(new Object[] { name, driver, url, username, password });
        } catch (Exception e) {
            // ignore
        }
    }
    
}
