package si.kotnik.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "nakupovalni_seznam")
@NamedQueries(value =
        {
                @NamedQuery(name = "NakupovalniSeznam.getAll", query = "SELECT n FROM NakupovalniSeznam n"),
                @NamedQuery(name = "NakupovalniSeznam.getAllForUser", query = "SELECT n FROM NakupovalniSeznam n WHERE n.uporabnik = :uporabnik")
        })
public class NakupovalniSeznam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @Column(name = "ustvarjen")
    private LocalDateTime ustvarjen;

    @ManyToOne()
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @OneToMany(mappedBy = "nakupovalniSeznam", cascade = CascadeType.ALL)
    private List<Artikel> artikli;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "nakupovalni_seznam_oznaka",
        joinColumns = @JoinColumn(name = "nakupovalni_seznam_id"),
        inverseJoinColumns = @JoinColumn(name = "oznaka_id")
    )
    private List<Oznaka> oznake;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Naziv: " + naziv);
        sb.append(" Opis: " + opis);
        return sb.toString();
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getUstvarjen() {
        return ustvarjen;
    }

    public void setUstvarjen(LocalDateTime ustvarjen) {
        this.ustvarjen = ustvarjen;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public List<Artikel> getArtikli() {
        return artikli;
    }

    public void setArtikli(List<Artikel> artikli) {
        this.artikli = artikli;
    }

    public void addArtikel(Artikel artikel) { this.artikli.add(artikel); }

    public List<Oznaka> getOznake() {
        return oznake;
    }

    public void setOznake(List<Oznaka> oznake) {
        this.oznake = oznake;
    }
}