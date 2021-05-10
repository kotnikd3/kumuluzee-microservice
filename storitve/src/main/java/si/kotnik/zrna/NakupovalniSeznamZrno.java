package si.kotnik.zrna;

import si.kotnik.entitete.Artikel;
import si.kotnik.entitete.NakupovalniSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class NakupovalniSeznamZrno {

    private Logger log = Logger.getLogger(NakupovalniSeznamZrno.class.getName());

    @PersistenceContext(unitName = "lokacijski-opomniki-jpa")
    private EntityManager em;

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + NakupovalniSeznamZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + NakupovalniSeznamZrno.class.getName());
    }

    public List<NakupovalniSeznam> pridobiSezname() {
        log.info("Getting all nakupovalni seznami.");
        List<NakupovalniSeznam> seznami = em.createNamedQuery("NakupovalniSeznam.getAll").getResultList();
        return seznami;
    }

    public NakupovalniSeznam pridobiSeznam(int id) {
        NakupovalniSeznam seznam = em.find(NakupovalniSeznam.class, id);
        return seznam;
    }

    @Transactional
    public NakupovalniSeznam posodobiSeznam(int id, NakupovalniSeznam seznam) {
        NakupovalniSeznam s = pridobiSeznam(id);
        seznam.setId(s.getId());
        em.merge(seznam);

        return seznam;
    }

    @Transactional
    public NakupovalniSeznam dodajSeznam(NakupovalniSeznam seznam) {
        if (seznam != null)
            em.persist(seznam);

        return seznam;
    }

    @Transactional
    public boolean odstraniSeznam(int id) {
        NakupovalniSeznam s = pridobiSeznam(id);
        if (s != null) {
            em.remove(s);
            return true;
        }
        return false;
    }
}
