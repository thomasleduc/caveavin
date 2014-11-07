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
 * Created by macbookpro on 07/11/14.
 */
@Entity
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

    @OneToMany
    @Getter @Setter
    private Collection<Cellar> cellars;

    @Override
    public String asCSV() {
        return Bottle.toCSV(id, username, email);
    }
}
