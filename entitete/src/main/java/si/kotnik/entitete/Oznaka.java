package si.kotnik.entitete;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "oznaka")
@NamedQueries(value =
        {
                @NamedQuery(name = "Oznaka.getAll", query = "SELECT o FROM Oznaka o")
        })
public class Oznaka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String naslov;
    private String opis;

//    @Transient
    @ManyToMany(mappedBy = "oznake")
    private List<NakupovalniSeznam> nakupovalniSeznami;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public List<NakupovalniSeznam> getNakupovalniSeznami() {
        return nakupovalniSeznami;
    }

    public void setNakupovalniSeznami(List<NakupovalniSeznam> nakupovalniSeznami) {
        this.nakupovalniSeznami = nakupovalniSeznami;
    }
}
