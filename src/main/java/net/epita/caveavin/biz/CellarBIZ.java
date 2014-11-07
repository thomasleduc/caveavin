package net.epita.caveavin.biz;

import lombok.Getter;
import net.epita.caveavin.dao.CellarDAO;
import net.epita.caveavin.dbo.Cellar;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Business class of Cellar.
 * @author teboul_g
 */
@RequestScoped
@Named
public class CellarBIZ extends AbstractBIZ<CellarDAO, Cellar, Long> {

    @Inject
    @Getter
    private CellarDAO DAO;

}
