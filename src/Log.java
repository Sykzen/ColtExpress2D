import java.lang.reflect.Method;


/**
 * Classe pour afficher des messages
 */
public class Log {

    private static Object object = null;
    private static Method print = null;

    /**
     * Si l'affichage est activé
     */
    private static boolean active = false;

    /**
     * Activer l'affichage
     */
    public static void on() {
        active = true;
    }

    /**
     * Désactiver l'affichage
     */
    public static void off() {
        active = false;
    }

    public static void setPrintMethod(Object object, Method print) {
        Log.object = object;
        Log.print = print;
    }

    /**
     * Afficher un message
     * Ne fait rien si l'affichage n'est pas activé
     * @param texte le message à afficher
     */
    public static void info(String texte) {

        if (active) {
            System.out.println(texte);
        }

        if (object != null && print != null) {

            try {
                print.invoke(object, texte);
            }

            catch (Exception e) {

                if (active) {
                    System.out.println(texte);
                }
            }
        }
    }
}
