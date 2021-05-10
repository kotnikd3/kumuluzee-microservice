package si.kotnik.zrna;

import si.kotnik.entitete.Artikel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ArtikliZrno {

    private Logger log = Logger.getLogger(ArtikliZrno.class.getName());

    @PersistenceContext(unitName = "lokacijski-opomniki-jpa")
    private EntityManager em;// = Persistence.createEntityManagerFactory("lokacijski-opomniki-jpa").createEntityManager();

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + ArtikliZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + ArtikliZrno.class.getName());
    }

    public List<Artikel> pridobiArtikle() {
        log.info("Getting all artikli.");
        List<Artikel> artikli = em.createNamedQuery("Artikel.getAll").getResultList();
        return artikli;
    }

    public Artikel pridobiArtikel(int id) {
        Artikel artikel = em.find(Artikel.class, id);
        return artikel;
    }

    @Transactional
    public Artikel posodobiArtikel(int id, Artikel artikel) {
        Artikel a = pridobiArtikel(id);
        artikel.setId(a.getId());
        em.merge(artikel);

        return artikel;
    }

    @Transactional
    public Artikel dodajArtikel(Artikel artikel) {
        if (artikel != null)
            em.persist(artikel);

        return artikel;
    }

    @Transactional
    public boolean odstraniArtikel(int id) {
        Artikel a = pridobiArtikel(id);
        if (a != null) {
            em.remove(a);
            return true;
        }
        return false;
    }
}
