import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;
import org.apache.commons.math3.util.Pair;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chen Wang on 02/08/17.
 */
public class ImageAnnotation {
    public static void main(String[] args) throws IOException {
        final ClarifaiClient client = new ClarifaiBuilder("KKQIegBW9uOl_3vaMSzqq4QCfPNyNBvB7XNBz1vE", "xsY48eiDhhsFo5M7HE3F71ZYkB_tEQmemlWekTgG")
                .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
                .buildSync(); // or use .build() to get a Future<ClarifaiClient>
        client.getToken();

        String path = "input/Input.mkv";
        KeyFrameDetection K = new KeyFrameDetection();
        K.Frames(path);
        K.MainFrames();
        ArrayList<Pair<String, Float>> M = new ArrayList<Pair<String, Float>>();

        File file = new File("output/mainframes");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++){
            ClarifaiResponse response = client.getDefaultModels().generalModel().predict()
                    .withInputs(
                            ClarifaiInput.forImage(ClarifaiImage.of(files[i]))
                    )
                    .executeSync();
            List<ClarifaiOutput<Concept>> predictions = (List<ClarifaiOutput<Concept>>) response.get();
            MBFImage image = ImageUtilities.readMBF(files[i]);
            List<Concept> data = predictions.get(0).data();
            boolean b = true;
            for (int j = 0; j < data.size(); j++) {
                for (int k = 0; k < M.size(); k++) {
                    if (M.get(k).getKey() == data.get(j).name()) {
                        float cu = M.get(k).getValue() + data.get(j).value();
                        M.set(k, new Pair<String, Float>(data.get(j).name(), cu));
                        b = false;
                        break;
                    }
                }
                if (b) {
                    M.add(new Pair<String, Float>(data.get(i).name(), data.get(i).value()));
                    b = true;
                }
            }
        }

        float f = -1;
        Pair<String, Float> P = new Pair<String, Float>("", f);
        for (int p = 0; p < M.size(); p++) {
            if (p == 0) {
                P = M.get(p);
            }
            else if (M.get(p).getValue() > P.getValue()) {
                P = M.get(p);
            }
            else {
                continue;
            }
        }
        FileWriter writer = new FileWriter(new File("output/summary.txt"));
        writer.write("The video was mainly talking about " + P.getFirst() + ".");
        writer.flush();
        writer.close();
        System.out.println(P.getFirst() + "  " + P.getSecond());
    }
}
