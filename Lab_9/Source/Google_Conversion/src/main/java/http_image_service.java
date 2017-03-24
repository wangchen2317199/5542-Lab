import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Chen Wang on 03/24/2017.
 */

 public class http_image_service {
        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String response = "";
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
        if (parameters.get("animals").toString().equals("animals")) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("http://imgh.us/D/Joy");
            jsonArray.put("http://imgh.us/D/Anger");
            jsonArray.put("http://imgh.us/D/Trust");
            jsonArray.put("http://imgh.us/D/Disgust");
            jsonArray.put("http://imgh.us/D/Anticipation");
            jsonArray.put("http://imgh.us/D/Surpise");
            jsonArray.put("http://imgh.us/D/Sadness");
            jsonArray.put("http://imgh.us/D/Fear");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("displayText", "The emoji is probably representing: " + Emoji);
            response = js.toString();
        }
        resp.setHeader("Content-type", "application/json");
        resp.getWriter().write(response);
    }
}
      