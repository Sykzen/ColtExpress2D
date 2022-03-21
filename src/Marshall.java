public class Marshall extends Personnage {

    /**
     * La nervosité du marshall
     * Probabilité qu'il se déplace à chaque tour
     */
    private double nervosite;

    /**
     * Créer un nouveau marshall
     * @param train le train dans lequel il se trouve
     * @param nervosite sa nervosité
     */
    public Marshall(Train train, double nervosite) {
        super(train, "Marshall", train.getNumeroLocomotive(), 0, false);
        this.nervosite = nervosite;
    }

    /**
     * Faire se déplacer le marshall dans une direction aléatoire et accessible avec la probabilité `nervosite`
     */
    public boolean deplacer() {

        double p = Aleatoire.getProba();

        if (p < nervosite) {

            Log.info("Le marshall se déplace.");

            int dx = (p < nervosite / 2) ? -1 : 1;
            if (!avancer(dx, 0)) {
                 avancer(-dx, 0);
            }

            chasserBandits();

            return true;
        }

        return false;
    }

    /**
     * Faire fuir tous les bandits qui se trouvent dans le même wagon que le marshall
     */
    private void chasserBandits() {
        for (Bandit bandit : getTrain().getBandits()) {
            if (bandit.getNumeroWagon() == getNumeroWagon() && bandit.getEtage() == getEtage()) {
                Log.info("Le marshall chasse " + bandit.getNom() + " du wagon.");
                bandit.fuir();
            }
        }
    }
}
