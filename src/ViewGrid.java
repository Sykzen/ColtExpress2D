import java.awt.Dimension;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewGrid extends JPanel {
    private BufferedImage image;
    View vue;
    Model model;
    String whichBandit;
    char[][] gg;
    ArrayList<ArrayList<HashMap<String,ImageIcon>>> terrain;
    ViewGrid(View vue, Model model) {
        super(new GridLayout(2, model.train.getNbWagons()));

        try {
            image = ImageIO.read(new FileInputStream("img\\paysage.jpg"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        this.vue=vue;
        this.model = model;

        initTerrain();
        update();
    }

    public void initTerrain() {

        this.terrain = new ArrayList<>();
        for (int i = 0; i < model.train.getNbWagons(); i++) {
            ArrayList<HashMap<String,ImageIcon>> wagon = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                wagon.add(new HashMap<String,ImageIcon>());
            }
            this.terrain.add(wagon);
        }
    }

    public void clearTerrain() {
        for (int i = 0; i < model.train.getNbWagons(); i++) {
            for (int j = 0; j < 2; j++) {
                getSquare(i, j).clear();
            }
        }
    }

    public HashMap<String,ImageIcon> getSquare(int wagon, int etage) {
        return terrain.get(wagon).get(etage);
    }
    public void update() {

        clearTerrain();
        int count = 0;
        for (int wagon = 0; wagon < model.train.getNbWagons(); wagon++) {
            for (Butin butin : model.train.getWagon(wagon).getButins()) {
                count++;
                getSquare(wagon, 1).put(butin.getNom()+count,vue.getImageOfObject(butin.getNom()));


            }
        }
        for (Bandit bandit : model.train.getBandits()) {

            ImageIcon image = vue.getImageOfObject(bandit.getNom());
            getSquare(bandit.getNumeroWagon(), 1 - bandit.getEtage()).put(bandit.getNom(),image);



        }


        Marshall marshall = model.train.getMarshall();
        getSquare(marshall.getNumeroWagon(), 1).put(marshall.getNom(),vue.getImageOfObject(marshall.getNom()));
    }

    @Override
    public void paintComponent(Graphics g) {
        update();
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        double DIM_WIDTH = vue.getCurrentDimensionWidth() / 1; // 1 Jpanel in column
        double DIM_HEIGHT = vue.getCurrentDimensionHeight() / 3; // 3 Jpanel in line

        int SQ_SIZE_HEIGHT = (int)(DIM_HEIGHT / 2);
        int SQ_SIZE_WIDTH = (int)(DIM_WIDTH / model.train.getNbWagons());

        g2.drawImage(image,0,0,(int)DIM_WIDTH,(int)DIM_HEIGHT,null);
        for (int i = 0; i < model.train.getNbWagons(); i++) {

            for (int j = 0; j < 2; j++) {

                g2.setPaint(Color.BLACK);
                g2.drawRect(i * SQ_SIZE_WIDTH, j * SQ_SIZE_HEIGHT, SQ_SIZE_WIDTH, SQ_SIZE_HEIGHT);

                HashMap<String,ImageIcon> square = getSquare(i, j);
                int k=0;
                for (Map.Entry<String, ImageIcon> entry : square.entrySet()) {


                    ImageIcon image = entry.getValue();
                    String theobject=entry.getKey();


                    int max = SQ_SIZE_WIDTH / image.getIconWidth();
                    if (max == 0) max = 1;

                    int x = i * SQ_SIZE_WIDTH + (k % max) * image.getIconWidth() + 5;
                    int y = j * SQ_SIZE_HEIGHT + k / max * image.getIconHeight() + 10;
                    if(image==vue.getImageOfObject("Marshall") || image==vue.getImageOfObject("Bandit 1")) {
                        g2.drawString(theobject, x, y);
                    }

                    image.paintIcon(this, g2, x, y);
                    k++;
                }
            }
        }
    }

    public Dimension getPreferredSize() { // !important
        return new Dimension(((int) vue.getCurrentDimensionWidth()), (int) vue.getCurrentDimensionHeight());
    }
}