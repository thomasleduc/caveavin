package net.epita.caveavin.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


import java.util.Collection;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by leduc_t
 */
@Entity
@Table(name="causer")
@XmlRootElement
@AllArgsConstructor @NoArgsConstructor
public class User extends AbstractDBO<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter @Setter
    private Long id;

    @Column
    @Getter @Setter
    private String username;

    @Column
    @Getter @Setter
    private String password;

    @Column
    @Getter @Setter
    private String email;

    @Column(name="creation_date")
    @Getter @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy="owner")
    @Getter @Setter
    private Collection<Cellar> cellars;

    @Override
    public String asCSV() {
        return Bottle.toCSV(id, username, email);
    }
}
