package si.kotnik.api.v1.sources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.kotnik.dtos.ArtikelDTO;
import si.kotnik.dtos.NakupovalniSeznamDTO;
import si.kotnik.entitete.NakupovalniSeznam;
import si.kotnik.entitete.Uporabnik;
import si.kotnik.zrna.NakupovalniSeznamZrno;
import si.kotnik.zrna.UpravljanjeNakupovalnihSeznamovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("seznami")
public class NakupovalniSeznamiSource {

    @Inject
    private UpravljanjeNakupovalnihSeznamovZrno upravljanjeNakupovalnihSeznamovZrno;

    @Inject
    private NakupovalniSeznamZrno nakupovalniSeznamZrno;

    @Operation(description = "Vrne seznam nakupovalnih seznamov", summary = "Nakupovalni seznami")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Seznam nakupovalnih seznamov",
                    content = @Content(schema = @Schema(implementation = NakupovalniSeznam.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Stevilo vrnjenih nakupovalnih seznamov.")}
            )})
    @GET
    public Response pridobiNakupovalneSezname() {
        List<NakupovalniSeznam> nakupovalniSeznamList = nakupovalniSeznamZrno.pridobiSezname();

        return Response
                .ok(nakupovalniSeznamList)
                .header("X-Total-Count", nakupovalniSeznamList.size())
                .build();
    }

    @Operation(description = "Vrne podrobnosti nakupovalniega seznama", summary = "Podrobnosti nakupovalnega seznama")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Podrobnosti nakupovalnega seznama",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            )})
    @GET
    @Path("{id}")
    public Response pridobiNakupovalniSeznam(@PathParam("id") int id) {
        NakupovalniSeznam nakupovalniSeznam = nakupovalniSeznamZrno.pridobiSeznam(id);

        if (nakupovalniSeznam != null) {
            return Response.ok(nakupovalniSeznam).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Operation(description = "Dodaj nov nakupovalni seznam", summary = "Dodaj nakupovalni seznam")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Nakupovalni seznam uspesno dodan"),
            @APIResponse(responseCode = "405", description = "Validacijska napaka"),
            @APIResponse(responseCode = "500", description = "Napaka na strezniku"),
    })
    @POST
    public Response dodajNakupovalniSeznam(@RequestBody(
            description = "DTO objekt za dodajanje nakupovalnih seznamov",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = NakupovalniSeznamDTO.class)))
                                           NakupovalniSeznamDTO nakupovalniSeznamDTO) {

        NakupovalniSeznam nakupovalniSeznam = upravljanjeNakupovalnihSeznamovZrno.dodajNakupovalniSeznam(nakupovalniSeznamDTO);

        if (nakupovalniSeznam == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Operation(description = "Odstrani nakupovalni seznam", summary = "Odstrani nakupovalni seznam")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Nakupovalni seznam uspesno odstranjen"),
            @APIResponse(responseCode = "404", description = "Nakupovalni seznam ne obstaja")
    })
    @DELETE
    @Path("{id}")
    public Response odstraniNakupovalniSeznam(@PathParam("id") int id) {
        if (nakupovalniSeznamZrno.odstraniSeznam(id)) {
            return Response.status(Response.Status.OK).entity(id).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(id).build();
    }


    @Operation(description = "Dodaj artikel na nakupovalni seznam", summary = "Dodaj artikel")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Dodajanje artikla dodano uspesno"),
            @APIResponse(responseCode = "405", description = "Validacijska napaka"),
            @APIResponse(responseCode = "500", description = "Napaka na strezniku")
    })
    @POST
    @Path("{id}/artikel")
    public Response dodajArtikelNakupovalniSeznam(@PathParam("id") int id, ArtikelDTO artikelDTO) {
        upravljanjeNakupovalnihSeznamovZrno.dodajArtikelNakupovalnemuSeznamu(id, artikelDTO);

        return Response.ok().build();
    }
    
}
