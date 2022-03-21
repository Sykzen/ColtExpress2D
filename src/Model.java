import com.sun.tools.javac.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.util.*;


class Model extends Observable {

    public static int NB_WAGONS = 6;
    public final static int NB_BUTINS_MIN = 1;
    public final static int NB_BUTINS_MAX = 4;
    public final static double NERVOSITE_MARSHALL = 0.3;
    public final static int NB_BALLES = 6;
    public static int NB_ORDRES = 3;
    public int NB_BANDITS = 5;

    public boolean sing = true;
    public int nbMove=0;
    public int CurrentBanditPlaying=0;
    public Train train;
    public ArrayList<AbstractMap.SimpleEntry<Bandit, String>> actions = new ArrayList<>();
    public int curAction = 0;

    public Model() {

        resetTrain();

    }

    public boolean ajouterBandit() {
        if (getNbBandits() < 5) {
            train.ajouterBandit("Bandit " + (train.getBandits().size() + 1));
            return true;
        }
        return false;
    }

    public int getNbBandits() {
        return train.getBandits().size();
    }

    public Bandit getBandit(int b) {
        return train.getBandits().get(b);
    }

    public int getCurrentBanditPlaying(){
        return this.CurrentBanditPlaying;
}

    public boolean getSing(){
        return sing;
    }

    public void addAction(String str) {
        actions.add(new AbstractMap.SimpleEntry<Bandit, String>(train.getBandits().get(CurrentBanditPlaying), str));
    }
    public void resetTrain(){
        this.train = new Train(NB_WAGONS, NB_BUTINS_MIN, NB_BUTINS_MAX, NERVOSITE_MARSHALL, NB_BALLES);

        for (int i = 0; i < NB_BANDITS; i++) {
            ajouterBandit();
        }

    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
