package net.sf.jeasyorm;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class EntityManager {
    
    private static final Logger log = Logger.getLogger(EntityManager.class.getName());
    
    private static Map<String, SqlInfo> infosByClassName = new HashMap<String, SqlInfo>();
    
    private static List<Class<? extends EntityManager>> entityManagerClasses = 
        new ArrayList<Class<? extends EntityManager>>();
    static {
        register(BasicEntityManager.class);
        register(HSQLDBEntityManager.class);
        register(OracleEntityManager.class);
        register(PostgreSQLEntityManager.class);
        register(DerbyEntityManager.class);
        // MySQL (5.1) supported by BasicEntityManager
        // H2 supported by BasicEntityManager
    }
    
    private String cache;
    private Connection connection;
    private NameGuesser nameGuesser;
    
    public static EntityManager getInstance(Connection connection) {
        return getInstance(null, connection);
    }
    
    public static EntityManager getInstance(String cache, Connection connection) {
        for (Class<? extends EntityManager> emClass : entityManagerClasses) {
            try {
                Constructor<? extends EntityManager> c = emClass.getConstructor(Connection.class);
                EntityManager em = c.newInstance(connection);
                em.setCache(cache);
                return em;
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }
    
    public static void clear() {
        infosByClassName.clear();
    }
    
    @SuppressWarnings("unchecked")
    public static void register(String entityManagerClassName) {
        try {
            entityManagerClasses.add(0, (Class<? extends EntityManager>) Class.forName(entityManagerClassName)); 
        } catch (Exception e) {
            throw new RuntimeException("Class '" + entityManagerClassName + "' not found!");
        }
    }
    
    public static void register(Class<? extends EntityManager> entityManagerClass) {
        entityManagerClasses.add(0, entityManagerClass);
    }
    
    protected static SqlInfo getSqlInfo(EntityManager manager, Class<?> entityClass) {
        String key = (manager.getCache() != null ? manager.getCache() + ":" : "") + entityClass.getName();
        SqlInfo info = infosByClassName.get(key);
        if (info == null && !infosByClassName.containsKey(key)) {
            info = manager.newSqlInfo(entityClass);
            infosByClassName.put(key, info);
        }
        return info;
    }
    
    /**
     * Constructors of child classes should throw an exception if they do not
     * support the database type of the connection.
     * (e.g. by checking connection.getMetaData().getDatabaseProductName())
     */
    protected EntityManager(Connection connection) {
        this.connection = connection;
    }
    
    public Connection getConnection() { return connection; }
    public String getCache() { return cache; }
    public void setCache(String cache) { this.cache = cache; }
    public NameGuesser getNameGuesser() { 
        if (nameGuesser == null) nameGuesser = new SimpleNameGuesser();
        return nameGuesser; 
    }
    public void setNameGuesser(NameGuesser nameGuesser) { this.nameGuesser = nameGuesser; }
    
    protected abstract SqlInfo newSqlInfo(Class<?> entityClass);
    
    protected Mapping newMapping(Class<?> entityClass) throws SQLException {
        return new Mapping(this, entityClass);
    }
    
    public abstract <T> T load(Class<T> entityClass, Object... pk);
    
    public abstract <T> T findUnique(Class<T> entityClass, String query, Object... params);
    
    public abstract <T> List<T> find(Class<T> entityClass, String query, Object... params);
    
    public abstract <T> Page<T> find(Class<T> entityClass, int pageNum, int pageSize, String query, Object... params);
    
    public abstract <T> Iterator<T> iterator(Class<T> entityClass, String query, Object... params);
    
    public abstract int count(String query, Object... params);
    
    public abstract <T> T insert(T entity);
    
    public abstract <T> void update(T entity);
    
    public abstract <T> void delete(T entity);

    protected void log(Level level, String message, Object... params) {
        log.log(level, message, params);
    }

    protected void log(Level level, String message, Throwable t) {
        log.log(level, message, t);
    }

    protected static class SqlInfo {
        
        protected Mapping mapping;
        protected String loadSql;
        protected String selectSql;
        protected String insertSql;
        protected String updateSql;
        protected String deleteSql;
        
        protected SqlInfo(Mapping mapping) {
            this.mapping = mapping;
        }
    }
}
