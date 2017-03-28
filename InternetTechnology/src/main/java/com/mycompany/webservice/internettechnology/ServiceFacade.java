package com.mycompany.webservice.internettechnology;

import com.mycompany.internettechnologydb.dao.UsersDAO;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;

/**
 * Function for autorization user, if he not existing in system
 *
 * @author roma
 */
@Path("/rest")
public class ServiceFacade {

    private static final String soult = "romanroman";

    @POST
    @Path("/getAutorization")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAutorization(@QueryParam("user") String userJsonString) {

        Map<String, Object> resultResponse = new HashMap<>();

        JSONObject jSONObject = null;

        String jsonUserData = "{\"userIsExist\": \"false\"}";
        try {
            jSONObject = new JSONObject(userJsonString);

            if ((jSONObject.getString("login") != null) && (jSONObject.getString("password") != null)) {
                String login = jSONObject.getString("login");
                String password = jSONObject.getString("password");

                UsersDAO userDAO = null;
                try {
                    userDAO = new UsersDAO(Persistence.createEntityManagerFactory("Testing"));
                } catch (Exception e) {
                    return Response.status(Status.SERVICE_UNAVAILABLE).build();
                }
                resultResponse = userDAO.userAutorization(login, password);

                JSONObject json = new JSONObject();
                json.put("hid", resultResponse.get("hid"));
                json.put("login", resultResponse.get("login"));
                json.put("pass", resultResponse.get("pass"));
                json.put("name", resultResponse.get("name"));
                json.put("userIsExist", resultResponse.get("userIsExist"));
                json.put("sesID", resultResponse.get("sesID"));
                json.put("sesName", resultResponse.get("sesName"));

                jsonUserData = json.toString();
            }
        } catch (Exception e) {
        }
        return Response.status(Status.OK).entity(jsonUserData).build();
    }

    /**
     *
     * @param userJsonString json string user (and he attribute)
     * @return flag - userIsRegistry (true if user is exist in system)
     */
    @POST
    @Path("/getRegistry")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRegistry(@QueryParam("user") String userJsonString) {
        Map<String, Object> resultResponse = new HashMap<>();

        try {
            JSONObject jSONObject = new JSONObject(userJsonString);

            if ((jSONObject.getString("login") != null)
                    && (jSONObject.getString("name") != null)
                    && (jSONObject.getString("password") != null)) {

                String name = jSONObject.getString("name");
                String login = jSONObject.getString("login");
                String password = jSONObject.getString("password");

                UsersDAO userDAO = null;
                try {
                    userDAO = new UsersDAO(Persistence.createEntityManagerFactory("Testing"));
                } catch (Exception e) {
                    return Response.status(Status.SERVICE_UNAVAILABLE).build();
                }
                boolean registryUser = userDAO.registryUser(name, login, password);

                resultResponse.put("userIsRegistry", registryUser);
            }
        } catch (Exception e) {
        }

        return Response.status(Status.OK).entity(resultResponse).build();
    }

}
