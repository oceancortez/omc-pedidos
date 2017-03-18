package omc.pedidos.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;


@SuppressWarnings("unchecked")
public class GenericDAO<PK, T> {

	private EntityManager entityManager;

	public GenericDAO(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public T getById(final PK pk) {
		return (T) this.entityManager.find(this.getTypeClass(), pk);
	}

	public void save(final T entity) {
		this.entityManager.persist(entity);
	}

	public void update(final T entity) {
		this.entityManager.merge(entity);
	}

	public void delete(final T entity) {
		this.entityManager.remove(entity);
		this.entityManager.flush();
	}

	public List<T> findAll() {
		return this.entityManager.createQuery("FROM " + this.getTypeClass().getName()).getResultList();
	}

	private Class<?> getTypeClass() {
		final Class<?> clazz =
				(Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		return clazz;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("rawtypes")
	public static Object getSingleResultOrNull(final Query query) {
		Object object;
		final List results = query.getResultList();
		if (results.isEmpty()) {
			object = null;
		} else if (results.size() == 1) {
			object = results.get(0);
		} else {
			throw new NonUniqueResultException();
		}
		return object;
	}
}