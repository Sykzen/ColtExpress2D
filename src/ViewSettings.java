import java.awt.*;

public class ViewSettings {
    public double width;
    public double height;
    public String name;
    public boolean show;
    public boolean showSettings;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    ViewSettings() {



        //Nom de la fenêtre
        this.name = "2D Colt Express";
        // Largeur
        this.width = screenSize.getWidth() * 3 / 4;
        // Hauteur
        this.height = screenSize.getHeight() * 3 / 4;
        // Affichage
        this.show = true;
        this.showSettings=false;



    }

}
