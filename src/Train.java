import java.util.ArrayList;

public class Train {

    private ArrayList<Wagon> wagons;

    private ArrayList<Bandit> bandits;
    private int nbBallesBandits;

    private Marshall marshall;

    public Train(int nbWagons, int nbButinsMin, int nbButinsMax, double nervositeMarshall, int nbBallesBandits) {

        this.wagons = new ArrayList<Wagon>(nbWagons + 1);
        for (int numero = 0; numero < nbWagons; numero++) {
            wagons.add(new Wagon(this, numero, Aleatoire.getInt(nbButinsMin, nbButinsMax)));
        }

        this.wagons.add(Wagon.locomotive(this, nbWagons, Aleatoire.getInt(nbButinsMin, nbButinsMax)));

        this.bandits = new ArrayList<Bandit>();
        this.nbBallesBandits = nbBallesBandits;

        this.marshall = new Marshall(this, nervositeMarshall);
    }

    public int getNbWagons() {
        return wagons.size();
    }

    public Wagon getWagon(int numero) {
        return wagons.get(numero);
    }

    public int getNumeroLocomotive() {
        return getNbWagons() - 1;
    }

    public Marshall getMarshall() {
        return marshall;
    }

    public ArrayList<Bandit> getBandits() {
        return bandits;
    }

    public Bandit getBanditAleatoire(int numeroWagon, int etage) {

        Bandit banditAleatoire = null;

        if (positionEstValide(numeroWagon, etage)) {

            for (Bandit bandit : bandits) {

                if (bandit.getNumeroWagon() == numeroWagon && bandit.getEtage() == etage && (banditAleatoire == null || Math.random() < 0.5)) {
                    banditAleatoire = bandit;
                }
            }
        }

        return banditAleatoire;
    }

    public boolean positionEstValide(int numeroWagon, int etage) {
        return numeroWagon >= 0 && numeroWagon < getNbWagons() && (etage == 0 || etage == 1);
    }

    public Bandit ajouterBandit(String nom) {
        Bandit nouveauBandit = new Bandit(this, nom, nbBallesBandits);
        bandits.add(nouveauBandit);
        return nouveauBandit;
    }
}
