import java.util.Random;

/**
 * Classe pour générer des valeurs aléatoires
 */
public class Aleatoire {

    private static Random random = new Random();

    /**
     * Générer un entier aléatoire entre min (inclus) et max (inclus)
     */
    public static int getInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Générer un entier aléatoire entre 0 (inclus) et max (inclus)
     */
    public static int getInt(int max) {
        return random.nextInt(max + 1);
    }

    /**
     * Générer un double aléatoire entre 0 (inclus) et 1 (exclu)
     */
    public static double getProba() {
        return random.nextDouble();
    }

    /**
     * Générer un booléen aléatoire
     */
    public static boolean getBoolean() {
        return random.nextBoolean();
    }
}
