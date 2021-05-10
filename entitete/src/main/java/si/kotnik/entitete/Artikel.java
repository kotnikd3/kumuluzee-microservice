package si.kotnik.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table(name = "artikel")
@NamedQueries(value =
        {
                @NamedQuery(name = "Artikel.getAll", query = "SELECT a FROM Artikel a")
        })
public class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @JsonbTransient
    @ManyToOne()
    @JoinColumn(name = "nakupovalni_seznam_id")
    private NakupovalniSeznam nakupovalniSeznam;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Naslov: " + naziv);
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

    public NakupovalniSeznam getNakupovalniSeznam() {
        return nakupovalniSeznam;
    }

    public void setNakupovalniSeznam(NakupovalniSeznam nakupovalniSeznam) {
        this.nakupovalniSeznam = nakupovalniSeznam;
    }
}