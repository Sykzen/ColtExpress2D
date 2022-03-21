import javax.sound.sampled.*;
import java.io.IOException;
public class ColtExpress {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        System.setProperty("file.encoding", "UTF-8");

        //RUN APP
        Model modele = new Model();
        View vue = new View(modele);

    }
}
