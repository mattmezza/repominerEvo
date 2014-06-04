package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Method;
import it.unisa.sesa.repominer.db.entities.Type;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class MethodDAO {

	public List<Method> getMethodsOfType(Type pType) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<Method> methods = em.find(Method.class, "where belonging_type=?", pType.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return methods;
	}

}
