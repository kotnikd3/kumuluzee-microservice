package si.kotnik.servlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.kotnik.jdbc.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/firstservlet")
public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Calling doGet()");
        PrintWriter writer = resp.getWriter();

        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("From microservice: " + s + "\n"));

        BaseDao uporabnikDao = UporabnikDaoImpl.getInstance();
        Uporabnik uporabnik = new Uporabnik("aleks", "miklic", "aleksmiklic");

        writer.append("Seznam obstojecih uporabnikov: \n");

//        uporabnikDao.odstrani(1);
//        uporabnikDao.posodobi(uporabnik);
        uporabnikDao.vstavi(uporabnik);

        List<Entiteta> uporabniki = uporabnikDao.vrniVse();
        uporabniki.stream().forEach(u -> writer.append(u.toString() + "\n"));
    }
}
