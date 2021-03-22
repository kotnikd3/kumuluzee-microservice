package si.kotnik.zrna;

import si.kotnik.entitete.Artikel;
import si.kotnik.entitete.NakupovalniSeznam;
import si.kotnik.entitete.Oznaka;
import si.kotnik.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class OznakaZrno {

    private Logger log = Logger.getLogger(OznakaZrno.class.getName());

    @PersistenceContext(unitName = "lokacijski-opomniki-jpa")
    private EntityManager em;// = Persistence.createEntityManagerFactory("lokacijski-opomniki-jpa").createEntityManager();

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + OznakaZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + OznakaZrno.class.getName());
    }

    public List<Oznaka> pridobiOznake() {
        log.info("Getting all oznake.");
        List<Oznaka> oznake = em.createNamedQuery("Oznaka.getAll").getResultList();
        return oznake;
    }

    public Oznaka pridobiOznako(int id) {
        Oznaka oznaka = em.find(Oznaka.class, id);
        return oznaka;
    }

    @Transactional
    public Oznaka posodobiOznako(int id, Oznaka oznaka) {
        Oznaka o = pridobiOznako(id);
        oznaka.setId(o.getId());
        em.merge(oznaka);

        return oznaka;
    }

    @Transactional
    public Oznaka dodajOznako(Oznaka oznaka) {
        if (oznaka != null)
            em.persist(oznaka);

        return oznaka;
    }

    @Transactional
    public boolean odstraniOznako(int id) {
        Oznaka o = pridobiOznako(id);
        if (o != null) {
            em.remove(o);
            return true;
        }
        return false;
    }
}
