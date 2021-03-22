package si.kotnik.zrna;

import si.kotnik.entitete.NakupovalniSeznam;
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
public class UporabnikiZrno {

    private Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PersistenceContext(unitName = "lokacijski-opomniki-jpa")
    private EntityManager em;// = Persistence.createEntityManagerFactory("lokacijski-opomniki-jpa").createEntityManager();

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UporabnikiZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UporabnikiZrno.class.getName());
    }

    public List<Uporabnik> pridobiUporabnike() {
        log.info("Getting all uporabniki.");
        List<Uporabnik> uporabniki = em.createNamedQuery("Uporabnik.getAll").getResultList();
        return uporabniki;
    }

    public Uporabnik pridobiUporabnika(int uporabnikId) {
        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);
        return uporabnik;
    }

    @Transactional
    public Uporabnik posodobiUporabnika(int uporabnikId, Uporabnik uporabnik) {
        Uporabnik u = pridobiUporabnika(uporabnikId);
        uporabnik.setId(u.getId());
        em.merge(uporabnik);

        return uporabnik;
    }

    @Transactional
    public Uporabnik dodajUporabnika(Uporabnik uporabnik) {
        if (uporabnik != null)
            em.persist(uporabnik);

        return uporabnik;
    }

    @Transactional
    public boolean odstraniUporabnika(int uporabnikId) {
        Uporabnik u = pridobiUporabnika(uporabnikId);
        if (u != null) {
            em.remove(u);
            return true;
        }
        return false;
    }
}
