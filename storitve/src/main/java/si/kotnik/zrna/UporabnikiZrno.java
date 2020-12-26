package si.kotnik.zrna;

import si.kotnik.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UporabnikiZrno {

    private Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PersistenceContext(unitName = "lokacijski-opomniki-jpa")
    private EntityManager em = Persistence.createEntityManagerFactory("lokacijski-opomniki-jpa").createEntityManager();

    public List<Uporabnik> getUporabniki() {
        Query q = em.createNamedQuery("Uporabnik.getAll");
        return q.getResultList();
    }

}
