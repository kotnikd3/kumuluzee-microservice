package si.kotnik.servlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.kotnik.entitete.Uporabnik;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Calling doGet()");
        PrintWriter writer = resp.getWriter();

        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("From microservice: " + s + "\n"));

        writer.append("Seznam obstojecih uporabnikov: \n");

        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki();

        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));
    }
}