package si.kotnik.servlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.kotnik.entitete.Artikel;
import si.kotnik.entitete.NakupovalniSeznam;
import si.kotnik.entitete.Oznaka;
import si.kotnik.entitete.Uporabnik;
import si.kotnik.zrna.ArtikliZrno;
import si.kotnik.zrna.NakupovalniSeznamZrno;
import si.kotnik.zrna.OznakaZrno;
import si.kotnik.zrna.UporabnikiZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private NakupovalniSeznamZrno seznamZrno;

    @Inject
    private ArtikliZrno artikelZrno;

    @Inject
    private OznakaZrno oznakaZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Calling doGet()");
        PrintWriter writer = resp.getWriter();

        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("From microservice: " + s + "\n"));

        // CREATE
        writer.println("\nCREATE: Dodajam uporabnika Denis Kotnik:");
        Uporabnik denis = new Uporabnik();
        denis.setIme("Denis");
        denis.setPriimek("Kotnik");
        denis.setEmail("denis.kotnik@gmail.com");
        denis.setUporabniskoIme("kotnikd3");
        Uporabnik denisDodan = uporabnikiZrno.dodajUporabnika(denis);
        writer.println("Dodan uporabnik: " + denisDodan.toString());

        // READ - ALL
        writer.println("\nREAD: Seznam obstojecih uporabnikov:");
        List<Uporabnik> uporabniki = uporabnikiZrno.pridobiUporabnike();
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));

        // UPDATE
        writer.println("\nUPDATE: Popravljam obstoječega uporabnika:");
        Uporabnik denisPopravljen = new Uporabnik();
        denisPopravljen.setIme("Denis Popravljen");
        denisPopravljen.setPriimek("Kotnik popravljen");
        denisPopravljen.setEmail("denis.popravljen.kotnik@gmail.com");
        denisPopravljen.setUporabniskoIme("kotnikd3popravljen");
        Uporabnik denisPosodobljen = uporabnikiZrno.posodobiUporabnika(denisDodan.getId(), denisPopravljen);
        writer.println("Obstojeci uporabnik popravljen: " + denisPosodobljen.toString());

        // DELETE
        writer.println("\nDELETE: Brisem uporabnika z ID 3:");
        if(uporabnikiZrno.odstraniUporabnika(3))
            writer.println("Uporabnik izbrisan");
        else
            writer.println("Uporabnik NI izbrisan");

        // GET - ALL NAKUPOVALNI SEZNAM
        writer.println("\nREAD: Seznam obstojecih seznamov:");
        List<NakupovalniSeznam> seznami = seznamZrno.pridobiSezname();
        seznami.stream().forEach(s -> writer.append(s.toString() + "\n"));

        // GET - ALL ARTIKLI
        writer.println("\nREAD: Seznam obstojecih artiklov:");
        List<Artikel> artikli = artikelZrno.pridobiArtikle();
        artikli.stream().forEach(s -> writer.append(s.toString() + "\n"));

        // GET - ALL OZNAKE
        writer.println("\nREAD: Seznam obstojecih oznak:");
        List<Oznaka> oznake = oznakaZrno.pridobiOznake();
        oznake.stream().forEach(s -> writer.append(s.toString() + "\n"));
    }
}