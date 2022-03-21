import java.util.ArrayList;

public class Test {

    public static void testInitTrain() {

        Train train = new Train(11, 1, 4, 0.3, 4);

        assert train.getNbWagons() == 12;
        assert train.getNumeroLocomotive() == train.getNbWagons() - 1;
        assert train.getBandits().size() == 0;
    }

    public static void testTrain() {

        Train train = new Train(6, 1, 4, 0.3, 4);

        ArrayList<Bandit> bandits = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Bandit b = train.ajouterBandit(Integer.toString(i));
            assert b != null;
            bandits.add(b);
        }

        assert train.getBanditAleatoire(0, 0) == null;

        for (int i = 0; i < 100; i++) {
            assert bandits.contains(train.getBanditAleatoire(0, 1));
        }

        for (int i = 1; i < train.getNbWagons(); i++) {
            assert train.getBanditAleatoire(i, -1) == null;
            assert train.getBanditAleatoire(i, 0) == null;
            assert train.getBanditAleatoire(i, 1) == null;
            assert train.getBanditAleatoire(i, 2) == null;
        }

        assert train.positionEstValide(0, 0);
        assert train.positionEstValide(0, 1);
        assert train.positionEstValide(train.getNumeroLocomotive(), 0);
        assert train.positionEstValide(train.getNumeroLocomotive(), 1);
        assert !train.positionEstValide(0, 2);
        assert !train.positionEstValide(-1, 0);
        assert !train.positionEstValide(train.getNumeroLocomotive() + 1, 0);
        assert !train.positionEstValide(train.getNumeroLocomotive() + 1, 1);
    }

    public static void testInitMarsahll() {

        Train train = new Train(6, 1, 4, 0.3, 4);

        Marshall marshall = train.getMarshall();
        assert "Marshall".equals(marshall.getNom());
        assert marshall.getTrain() == train;
        assert marshall.getNumeroWagon() == train.getNumeroLocomotive();
        assert marshall.getWagon() == train.getWagon(marshall.getNumeroWagon());
        assert marshall.getEtage() == 0;
    }

    public static void testMarshall() {

        double nervosite = 0.43;

        Train train = new Train(6, 1, 4, nervosite, 4);

        Marshall marshall = train.getMarshall();

        int total = 20000, deplacements = 0;

        for (int i = 0; i < total; i++) {

            int wagon = marshall.getNumeroWagon();

            if (marshall.deplacer()) {
                assert marshall.getEtage() == 0;
                assert wagon != marshall.getNumeroWagon();
                deplacements++;
            }

            else {
                assert wagon == marshall.getNumeroWagon();
            }
        }

        assert Math.abs(deplacements / (double)total - nervosite) < 0.01;
    }

    public static void testInitBandit() {

        Train train = new Train(6, 1, 4, 0.3, 4);

        Bandit bandit1 = train.ajouterBandit("abcd");
        assert train.getBandits().size() == 1;
        assert "abcd".equals(bandit1.getNom());
        assert bandit1.getTrain() == train;
        assert bandit1.getNumeroWagon() == 0;
        assert bandit1.getEtage() == 1;
        assert bandit1.getWagon() == train.getWagon(bandit1.getNumeroWagon());
        assert bandit1.getButins().size() == 0;
        assert bandit1.getTotalButins() == 0;

        Bandit bandit2 = train.ajouterBandit("efgh");
        assert train.getBandits().size() == 2;
        assert "efgh".equals(bandit2.getNom());
        assert bandit2.getTrain() == train;
        assert bandit2.getNumeroWagon() == 0;
        assert bandit2.getEtage() == 1;
        assert bandit2.getWagon() == train.getWagon(bandit2.getNumeroWagon());
        assert bandit2.getButins().size() == 0;
        assert bandit2.getTotalButins() == 0;
    }

    public static void testInitWagonButin() {

        Train train = new Train(1000, 7, 13, 0.3, 4);

        for (int i = 0; i < train.getNbWagons(); i++) {

            Wagon wagon = train.getWagon(i);

            assert wagon.getNumero() == i;
            assert !wagon.estLaLocomotive() || train.getNumeroLocomotive() == i;

            int nbButins = wagon.getButins().size();
            assert nbButins >= 7 && nbButins <= 13;

            while (nbButins > 0) {

                Butin butin = wagon.prendreButinAleatoire();
                if ("Magot".equals(butin.getNom())) {
                    assert butin.getValeur() == 1000;
                }
                else if ("Bijoux".equals(butin.getNom())) {
                    assert butin.getValeur() == 500;
                }
                else {
                    assert "Bourse".equals(butin.getNom()) && butin.getValeur() >= 0 && butin.getValeur() <= 500;
                }

                assert wagon.getButins().size() == --nbButins;
            }

            assert wagon.prendreButinAleatoire() == null;
        }
    }

    public static void testActions() {

        Train train = new Train(1, 1, 1, 0.3, 4);

        assert train.getWagon(0).getButins().size() == 1;

        Bandit b1 = train.ajouterBandit("b1");
        Bandit b2 = train.ajouterBandit("b2");

        b1.avancer(Direction.BAS);
        b1.braquer();

        assert b1.getEtage() == 0;
        assert b1.getNumeroWagon() == 0;
        assert b1.getButins().size() == 1;
        assert b1.getButins().get(0).getValeur() == b1.getTotalButins();
        assert train.getWagon(0).getButins().size() == 0;

        b2.tirer(Direction.BAS);

        assert b1.getNumeroWagon() == 0;
        assert b1.getButins().size() == 0;
        assert train.getWagon(0).getButins().size() == 1;

        b2.avancer(Direction.AVANT);
        b2.braquer();

        assert b2.getEtage() == 1;
        assert b2.getNumeroWagon() == 1;
        assert b2.getButins().size() == 0;
        assert train.getWagon(1).getButins().size() == 1;

        b1.braquer();
        b1.avancer(Direction.AVANT);

        assert b1.getEtage() == 1;
        assert b1.getNumeroWagon() == 1;
        assert b1.getButins().size() == 0;
        assert train.getWagon(1).getButins().size() == 2;

        while(!train.getMarshall().deplacer());

        b1.avancer(Direction.BAS);
        b2.avancer(Direction.BAS);
        b1.braquer();
        b1.braquer();

        while(!train.getMarshall().deplacer());

        assert b1.getEtage() == 1;
        assert b1.getNumeroWagon() == 1;
        assert b1.getButins().size() == 1;
        assert b2.getEtage() == 1;
        assert b2.getNumeroWagon() == 1;
        assert b2.getButins().size() == 0;
        assert train.getWagon(1).getButins().size() == 1;

        b2.avancer(Direction.ARRIERE);
        b2.tirer(Direction.AVANT);

        assert b1.getEtage() == 1;
        assert b1.getNumeroWagon() == 1;
        assert b1.getButins().size() == 0;
        assert b2.getEtage() == 1;
        assert b2.getNumeroWagon() == 0;
        assert b2.getButins().size() == 0;
        assert train.getWagon(1).getButins().size() == 2;
    }

    public static void main(String[] args) {
        Log.off();
        testInitTrain();
        testTrain();
        testInitMarsahll();
        testMarshall();
        testInitBandit();
        testInitWagonButin();
        testActions();
    }
}
