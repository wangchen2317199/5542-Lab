import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chen Wang on 02/15/2017.
 */

@javax.servlet.annotation.WebServlet(name = "GoogleConversion", urlPatterns = {"/GoogleConversion"})
public class GoogleConversion extends javax.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, X-Auth-Token, Content-Type");
        resp.setContentType("application/json");
        String topic = req.getParameter("topic");
        String msg = req.getParameter("msg");
        System.out.print(topic + " " + msg);
        resp.getWriter().write("The video is mainly about " + topic + ".\n" + msg + "\n\nURL Working");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println(data);
        String output = "";
        JSONObject params = new JSONObject(data);
        JSONObject result = params.getJSONObject("result");
        JSONObject parameters = result.getJSONObject("parameters");
        if (parameters.has("prerequisite")) {
            String prerequisite = parameters.getString("prerequisite").toString();
            String query = "https://api.mlab.com/api/1/databases/temp123/collections/bigdata?q={%22name%22:%22" + prerequisite + "%22}&apiKey=NV6PEwYt13rsIJu21LnqTqGtnC_pZv3X";
            JSONObject jsonObject = new JO().getData(query);
            JSONObject js = new JSONObject();
            js.put("speech", "Prerequisites for tutorial " + prerequisite + ": " + jsonObject.get("prerequisite") );
            js.put("displayText", "Prerequisites for tutorial " + prerequisite + ": " + jsonObject.get("prerequisite") );
            js.put("source", "naga database");
            output = js.toString();
        } else {
            String tutorial = parameters.get("Tutorial").toString();
            String query = "https://api.mlab.com/api/1/databases/temp123/collections/bigdata?q={%22name%22:%22" + tutorial + "%22}&apiKey=NV6PEwYt13rsIJu21LnqTqGtnC_pZv3X";
            JSONObject jsonObject = new JO().getData(query);
            JSONObject js = new JSONObject();
            js.put("speech", "Tutorial " + tutorial + " is on " + jsonObject.get("date") + ". Its about " + jsonObject.get("desc"));
            js.put("displayText", "Tutorial " + tutorial + "is on " + jsonObject.get("date") + ". Its about " + jsonObject.get("desc"));
            js.put("source", "naga database");
            output = js.toString();
        }
        resp.setHeader("Content-type", "application/json");
        resp.getWriter().write(output);
    }
}

class JO {
    public JSONObject getData(String query) throws IOException {
        URL obj = new URL(query);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONArray jsonArray = new JSONArray(response.toString());
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        return jsonObject;
    }
}