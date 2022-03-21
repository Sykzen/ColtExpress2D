import java.util.ArrayList;

public class Bandit extends Personnage {

    /**
     * Liste des butins tenus par le bandit
     */
    private ArrayList<Butin> butins;

    /**
     * Nombre de balles restantes
     */
    private int nbBalles;

    /**
     * Créer un nouveau bandit
     * @param train le train dans lequel il se trouve
     * @param nom son nom
     * @param nbBalles son nombre de balles de départ
     */
    public Bandit(Train train, String nom, int nbBalles) {
        super(train, nom, 0, 1, true);
        this.butins = new ArrayList<Butin>();
        this.nbBalles = nbBalles;
    }

    /**
     * Récupérer la liste des butins tenus par le bandit
     */
    public ArrayList<Butin> getButins() {
        return butins;
    }

    /**
     * Récupérer la somme des valeurs des butins tenus par le bandit
     */
    public int getTotalButins() {
        int total = 0;
        for (Butin butin : butins) {
            total += butin.getValeur();
        }
        return total;
    }

    /**
     * Faire se déplacer le bandit dans une direction donnée
     * Ne fait rien si la position où déplacer le bandit n'est pas accessible
     * @param direction la direction dans laquelle se déplacer
     */
    public void avancer(Direction direction) {

        switch (direction) {
            case AVANT:
                avancer(1, 0, "avance vers la locomotive", "ne peut pas aller plus loin");
                break;
            case ARRIERE:
                avancer(-1, 0, "avance vers l'arrière du train", "est déjà dans le dernier wagon");
                break;
            case HAUT:
                avancer(0, 1, "monte sur le toit", "ne peut pas monter plus haut");
                break;
            case BAS:
                avancer(0, -1, "descend du toit", "ne peut pas tomber plus bas");
                break;
        }

        if (getEtage() == 0 && getNumeroWagon() == getTrain().getMarshall().getNumeroWagon()) {
            fuir();
        }
    }

    /**
     * Fonction interne pour faire se déplacer le bandit
     * @param dx la différence en nombre de wagons avec la position où se déplacer
     * @param dy la différence en nombre d'étages avec la position où se déplacer
     * @param reussite le message à afficher si le bandit a réussi à se déplacer
     * @param echec le message à afficher si le bandit ne peut pas se déplacer vers cette position
     */
    private void avancer(int dx, int dy, String reussite, String echec) {
        Log.info(getNom() + " " + (avancer(dx, dy) ? reussite : echec) + ".");
    }

    /**
     * Récupérer un butin aléatoire dans le wagon où se trouve le bandit
     * Ne fait rien s'il n'y a pas de butin dans ce wagon ou si le bandit est sur le toit
     */
    public void braquer() {

        if (getEtage() == 0) {

            Butin butin = getWagon().prendreButinAleatoire();

            if (butin != null) {
                Log.info(getNom() + " récupère un butin.");
                butins.add(butin);
            }

            else {
                Log.info(getNom() + " cherche un butin mais il n'y en a pas dans ce wagon.");
            }
        }

        else {
            Log.info(getNom() + " cherche un butin mais il n'y en a pas sur le toit.");
        }
    }

    /**
     * Tirer une balle dans une direction donnée
     * S'il y a un bandit dans cette direction et qu'il a au moins un butin, il en lâche un aléatoirement
     * Fait diminuer le nombre de balles de 1
     * Ne fait rien si le bandit n'a plus de balles
     * @param direction la direction dans laquelle tirer
     */
    public void tirer(Direction direction) {

        switch (direction) {
            case AVANT:
                tirer(1, 0, "l'avant");
                break;
            case ARRIERE:
                tirer(-1, 0, "l'arrière");
                break;
            case HAUT:
                tirer(0, 1, "le haut");
                break;
            case BAS:
                tirer(0, -1, "le bas");
                break;
        }
    }

    /**
     * Fonction interne pour tirer une balle
     * @param dx la différence en nombre de wagons avec la position où tirer
     * @param dy la différence en nombre d'étages avec la position où tirer
     * @param direction le nom de la direction dans laquelle tirer
     */
    private void tirer(int dx, int dy, String direction) {

        if (nbBalles > 0) {

            Bandit cible = getTrain().getBanditAleatoire(getNumeroWagon() + dx, getEtage() + dy);

            String message = getNom() + " tire vers " + direction + ". ";

            if (cible != null) {
                Log.info(message + cible.getNom() + " est touché.");
                cible.lacherButin();
            }

            else {
                Log.info(message + "Personne n'a été touché.");
            }

            nbBalles--;
        }

        else {
            Log.info(getNom() + " tire vers " + direction + " mais il n'a plus de balles.");
        }
    }

    /**
     * Lâcher un butin si le bandit en a au moins un et le faire fuir sur le toit de son wagon s'il n'y est pas déjà
     */
    public void fuir() {
        Log.info(getNom() + " fuit sur le toit.");
        lacherButin();
        avancer(0, 1);
    }

    /**
     * Lâcher un butin aléatoire dans le wagon où se trouve le bandit
     */
    private void lacherButin() {
        if (butins.size() > 0) {
            Log.info(getNom() + " lâche un butin.");
            getWagon().ajouterButin(butins.remove(Aleatoire.getInt(butins.size() - 1)));
        }
    }
}
