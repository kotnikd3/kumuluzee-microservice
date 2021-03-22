package si.kotnik.zrna;

import si.kotnik.dtos.ArtikelDTO;
import si.kotnik.dtos.NakupovalniSeznamDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UpravljanjeNakupovalnihSeznamovZrno {
    @Inject
    private NakupovalniSeznamZrno seznamZrno;

    @Inject
    private ArtikliZrno artikelZrno;


    public void dodajNakupovalniSeznam(NakupovalniSeznamDTO nakupovalniSeznamDTO) {

    }

    public void dodajArtikelNakupovalnemuSeznamu(int nakupovalniSeznamId, ArtikelDTO artikelDTO) {

    }
}
