public class Butin {

    /**
     * Le nom du butin
     */
    private String nom;

    /**
     * La valeur du butin
     */
    private int valeur;

    /**
     * Créer un nouveau butin
     * @param nom son nom
     * @param valeur sa valeur
     */
    public Butin(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    /**
     * Récupérer le nom du butin
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupérer la valeur du butin
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Créer une bourse
     */
    public static Butin bourse() {
        return new Butin("Bourse", Aleatoire.getInt(500));
    }

    /**
     * Créer un bijoux
     */
    public static Butin bijoux() {
        return new Butin("Bijoux", 500);
    }

    /**
     * Créer un butin aléatoire (bijoux ou bourse)
     */
    public static Butin aleatoire() {
        if (Aleatoire.getBoolean()) {
            return bourse();
        }
        return bijoux();
    }

    /**
     * Créer un magot
     */
    public static Butin magot() {
        return new Butin("Magot", 1000);
    }
}
