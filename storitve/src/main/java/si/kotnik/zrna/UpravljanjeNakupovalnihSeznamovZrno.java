package si.kotnik.zrna;

import si.kotnik.dtos.ArtikelDTO;
import si.kotnik.dtos.NakupovalniSeznamDTO;
import si.kotnik.entitete.Artikel;
import si.kotnik.entitete.NakupovalniSeznam;
import si.kotnik.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeNakupovalnihSeznamovZrno {
    private Logger log = Logger.getLogger(UpravljanjeNakupovalnihSeznamovZrno.class.getName());

    @Inject
    private NakupovalniSeznamZrno seznamZrno;

    @Inject
    private ArtikliZrno artikelZrno;

    @Inject
    private UporabnikiZrno uporabnikiZrno;


    public NakupovalniSeznam dodajNakupovalniSeznam(NakupovalniSeznamDTO nakupovalniSeznamDTO) {
        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(nakupovalniSeznamDTO.getUporabnikId());

        if (uporabnik == null) {
            log.info("Ne moremo ustvariti nov nakupovalni seznam, ker uporabnik ne obstaja.");
            return null;
        }

        NakupovalniSeznam nakupovalniSeznam = new NakupovalniSeznam();

        // DODAMO NA OBE STRANI!
        nakupovalniSeznam.setUporabnik(uporabnik);
        uporabnik.getNakupovalniSeznami().add(nakupovalniSeznam);

        nakupovalniSeznam.setNaziv(nakupovalniSeznamDTO.getNaziv());
        nakupovalniSeznam.setOpis(nakupovalniSeznamDTO.getOpis());
        nakupovalniSeznam.setUstvarjen(LocalDateTime.now());

        return seznamZrno.dodajSeznam(nakupovalniSeznam);
    }

    public Artikel dodajArtikelNakupovalnemuSeznamu(int nakupovalniSeznamId, ArtikelDTO artikelDTO) {
        NakupovalniSeznam nakupovalniSeznam = seznamZrno.pridobiSeznam(nakupovalniSeznamId);

        if (nakupovalniSeznam == null) {
            log.info("Ne moremo dodati artikla na nakupovalni seznam, ker nakupovalni seznam ne obstaja.");
            return null;
        }

        Artikel artikel = new Artikel();
        artikel.setNaziv(artikelDTO.getNaziv());
        artikel.setOpis(artikelDTO.getOpis());
        // DODAMO NA OBE STRANI!
        artikel.setNakupovalniSeznam(nakupovalniSeznam);
        nakupovalniSeznam.getArtikli().add(artikel);

        artikel = artikelZrno.dodajArtikel(artikel);
        seznamZrno.posodobiSeznam(nakupovalniSeznam.getId(), nakupovalniSeznam);
        return artikel;
    }
}
