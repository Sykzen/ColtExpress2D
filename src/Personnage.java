public abstract class Personnage {

    private Train train;
    private String nom;
    private int numeroWagon, etage;
    private boolean peutMonter;

    public Personnage(Train train, String nom, int numeroWagon, int etage, boolean peutMonter) {
        this.train = train;
        this.nom = nom;
        this.numeroWagon = numeroWagon;
        this.etage = etage;
        this.peutMonter = peutMonter;
    }

    public String getNom() {
        return nom;
    }

    public int getNumeroWagon() {
        return numeroWagon;
    }

    public int getEtage() {
        return etage;
    }

    public Train getTrain() {
        return train;
    }

    public Wagon getWagon() {
        return train.getWagon(numeroWagon);
    }

    protected boolean avancer(int dx, int dy) {

        if (!peutMonter && dy != 0) {
            return false;
        }

        int nx = numeroWagon + dx, ny = etage + dy;

        if (train.positionEstValide(nx, ny)) {
            numeroWagon = nx;
            etage = ny;
            return true;
        }

        return false;
    }
}
