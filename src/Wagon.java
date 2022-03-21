import java.util.ArrayList;

public class Wagon {

    private Train train;
    private int numero;

    private ArrayList<Butin> butins;

    public Wagon(Train train, int numero, int nbButins) {

        this.train = train;
        this.numero = numero;

        this.butins = new ArrayList<Butin>(nbButins);
        for (int b = 0; b < nbButins; b++) {
            butins.add(Butin.aleatoire());
        }
    }

    public int getNumero() {
        return numero;
    }

    public boolean estLaLocomotive() {
        return train.getNumeroLocomotive() == numero;
    }

    public ArrayList<Butin> getButins() {
        return butins;
    }

    public void ajouterButin(Butin butin) {
        butins.add(butin);
    }

    public Butin prendreButinAleatoire() {

        if (butins.size() == 0) {
            return null;
        }

        return butins.remove(Aleatoire.getInt(butins.size() - 1));
    }

    public static Wagon locomotive(Train train, int numero, int nbButins) {
        Wagon l = new Wagon(train, numero, nbButins - 1);
        l.ajouterButin(Butin.magot());
        return l;
    }
}
