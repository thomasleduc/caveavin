package net.epita.caveavin.dao;

import net.epita.caveavin.dbo.AbstractDBO;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The abstract class which defined the DAO level.
 * @author leduc_t
 * @param <T> The entity
 * @param <K> The type of entity's id
 */
public abstract class AbstractDAO
        <T extends AbstractDBO<K>, K extends Comparable<K>> {

    public void Log(Level lvl, String msg) {
        Logger.getLogger(getClass().getName()).log(lvl, msg);
    }

    /**
     * Create an entity manager and return it.
     * @return The entityManager.
     */
    @PersistenceContext(unitName="cav-pu")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * @return the class of Entity managed by this DAO
     */
    public abstract Class<T> getEntityClass();

    /**
     * @param id of the searched entity
     * @return the entity according the id
     */
    public T findById(final K id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    /**
     * @return a collection of all entity managed by the DAO.
     */
    public Collection<T> findAll() {

        // construct the criteria query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
        Root<T> rootEntry = cq.from(getEntityClass());
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);

        // get all entities
        return allQuery.getResultList();
    }

    /**
     * Push the entity.
     * @param entity The entity to push.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void persist(final T entity) throws Exception {
        em.persist(entity);
    }

    public Long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<T> rootEntry = cq.from(getEntityClass());
        CriteriaQuery<Long> countQ = cq.select(cb.count(rootEntry));

        return em.createQuery(countQ).getSingleResult();
    }

    /**
     * @param key The sql key of the unique constrain
     * @return The entity associate entity
     */
    public T findUnique(String key, String value) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(getEntityClass());

        Root<T> c = q.from(getEntityClass());
        q.select(c);

        ParameterExpression<String> name = cb.parameter(String.class, key);
        q.where(cb.equal(c.get(key), name));

        try {
            return em.createQuery(q).setParameter(key, value).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}