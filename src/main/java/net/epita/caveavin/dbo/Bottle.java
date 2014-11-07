package net.epita.caveavin.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by macbookpro on 07/11/14.
 */
@Entity
@XmlRootElement
@AllArgsConstructor @NoArgsConstructor
public class Bottle extends AbstractDBO<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter @Setter
    private Long id;

    @Column
    @Getter @Setter
    private String name;

    @Column(name="creation_date")
    @Getter @Setter
    private Date creationDate;

    @Column(name="drunk_date")
    @Getter @Setter
    private Date drunkDate;

    @Column
    @Getter @Setter
    private Integer size;

    @Column
    @Getter @Setter
    private Integer color;

    @Column
    @Getter @Setter
    private String stored;

    @Override
    public String asCSV() {
        return Bottle.toCSV(id, name, creationDate, drunkDate, size, color);
    }
}
