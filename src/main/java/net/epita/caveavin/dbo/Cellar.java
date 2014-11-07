package net.epita.caveavin.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Collection;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by leduc_t
 */
@Entity
@XmlRootElement
@AllArgsConstructor @NoArgsConstructor
public class Cellar extends AbstractDBO<Long>{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column
    @Getter @Setter
    private String name;

    @Column
    @Getter @Setter
    private String owner;

    @OneToMany(mappedBy="stored")
    @Getter @Setter
    private Collection<Bottle> bottles;

    @Override
    public String asCSV() {
        return Bottle.toCSV(id, name);
    }
}
