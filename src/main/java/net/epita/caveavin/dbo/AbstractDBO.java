package net.epita.caveavin.dbo;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Abstract class which defined the Entity level.
 * @author leduc_t
 * @param <K> The type of entity's id
 */
public abstract class AbstractDBO<K extends Comparable<K>>
        implements Serializable, Comparable<AbstractDBO<K>> {

    public void Log(Level lvl, String msg) {
        Logger.getLogger(getClass().getName()).log(lvl, msg);
    }

    @Override
    public int compareTo(final AbstractDBO<K> o) {
        return getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + getId().toString();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj.getClass().equals(getClass())
                && ((AbstractDBO<K>) obj).getId().equals(getId());
    }

    /**
     * The id getter.
     * @return The id of the Entity
     */
    public abstract K getId();

    /**
     * The id setter.
     * @param id The value of the id to put in the property
     */
    public abstract void setId(K id);

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    /**
     * Get the Entity as a CSV.
     * @return The String composed with the different properties
     */
    public abstract String asCSV();

    /**
     * Convert the <code>fields</code> given in a String CSV like.
     * @param fields The fields of entity.
     * @return The String.
     */
    public static String toCSV(final Object... fields) {
        StringBuilder sb = new StringBuilder();
        for (Object field : fields) {
            sb.append("\"");
            sb.append(field.toString().replaceAll("\"", "\\\""));
            sb.append("\";");
        }

        return sb.toString();
    }

    /**
     * Convert the list of <code>values</code> in an well formated string
     * spaced by comma.
     * @param values The list.
     * @return The String.
     */
    public static String toStringArray(List<? extends AbstractDBO> values) {
        final StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (AbstractDBO it: values) {
            sb.append(prefix);
            prefix = ",";
            sb.append(it.getId().toString().replaceAll("\"", "\\\""));
        }

        return sb.toString();
    }
}