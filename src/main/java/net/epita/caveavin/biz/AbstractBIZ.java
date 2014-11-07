package net.epita.caveavin.biz;

import net.epita.caveavin.dao.AbstractDAO;
import net.epita.caveavin.dbo.AbstractDBO;

import java.util.Collection;

/**
 * The Abstract class which defined the Business level.
 * @author leduc_t
 * @param <D> DAO used
 * @param <T> Entity used
 * @param <K> Key use
 */
public abstract class AbstractBIZ
        <D extends AbstractDAO<T, K>,
                T extends AbstractDBO<K>,
                K extends Comparable<K>> {

    /**
     * Get the DAO according to the Business class.
     * @return The DAO.
     */
    public abstract D getDAO();

    /**
     * Find all the entities managed by the instance of the business class.
     * @return all the entities managed by the instance of the business class.
     */
    public Collection<T> findAll() {
        return getDAO().findAll();
    }

    /**
     * Find the entity managed by the instance of the business class,
     * according to the <code>id</code>.
     * @param id The id of the entity
     * @return The entity.
     */
    public T findById(final K id) {
        return getDAO().findById(id);
    }

    /**
     * Push the entity.
     * @param entity The entity to push.
     */
    public void persist(final T entity) {
        getDAO().persist(entity);
    }

    public Long count() {
        return getDAO().count();
    }

}