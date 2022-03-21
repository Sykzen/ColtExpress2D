import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class Controller implements ActionListener {

    private Model model;
    private View view;


    public Controller (Model model, View view){
        this.model = model;
        this.view = view;
        try { Log.setPrintMethod(view, View.class.getMethod("setCommentaire", String.class)); } catch(Exception e) {}
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean whichmove = model.actions.size() < model.NB_ORDRES * model.getNbBandits();

        if (e.getSource() == view.Settings){

            view.ShowGuiSettings();


        }

        else if (e.getSource()==view.Son){
            view.setSing();
        }

        else if (e.getSource()==view.Soff) {
            view.setSing();
        }

        else if (e.getSource() == view.Action) {

            if (!whichmove) {

                DisplayAction();

                if (model.curAction == model.actions.size()) {
                    model.actions.clear();
                    model.curAction = 0;
                    view.setCommentaire("");
                    view.setTour(0);
                }
            }
        }
        else if(view.settingsFrame.isShowing()) {

            if ((e.getActionCommand().substring(0, 2)).equals("+1") || (e.getActionCommand().substring(0, 2)).equals("-1")) {
                switch (e.getActionCommand()) {
                    case "+1 Ordre":
                        view.setNB_ORDRES(1);
                        break;
                    case "-1 Ordre":
                        view.setNB_ORDRES(-1);
                        break;
                    case "+1 Joueur":
                        view.setNb_BANDITS(1);
                        break;
                    case "-1 Joueur":
                        view.setNb_BANDITS(-1);
                        break;
                    case "+1 Wagon":
                        view.setNB_WAGONS(1);
                        break;
                    case "-1 Wagon":
                        view.setNB_WAGONS(-1);
                        break;
                }
            }

            else if(e.getActionCommand()=="Update"){
                view.setTour(model.nbMove);
                model.resetTrain();
                view.updateTopBandits();
                view.repaint();
            }
        }

        else if (whichmove) {
            model.addAction(e.getActionCommand());
            LoadGame();
        }
    }




    public void LoadGame() {
        // Set Changement Player turn
        model.nbMove++;
        model.CurrentBanditPlaying = (model.nbMove / model.NB_ORDRES) % model.getNbBandits();
        view.setTour(model.nbMove);
    }

    // fonction pour jouer les actions sauvegardé par les joueurs
    public void DisplayAction()  {

        if (!model.train.getMarshall().deplacer()) {

            int numBandit = model.curAction % model.getNbBandits();
            int numAction = model.curAction / model.getNbBandits();
            int curAction = numBandit * model.NB_ORDRES + numAction;

            Bandit bandit = model.actions.get(curAction).getKey();

            switch(model.actions.get(curAction).getValue()) {

                case "TS":
                    bandit.tirer(Direction.HAUT);
                    break;
                case "RS":
                    bandit.tirer(Direction.AVANT);
                    break;
                case "LS":
                    bandit.tirer(Direction.ARRIERE);
                    break;
                case "BS":
                    bandit.tirer(Direction.BAS);
                    break;
                case "TM":
                    bandit.avancer(Direction.HAUT);
                    break;
                case "RM":
                    bandit.avancer(Direction.AVANT);
                    break;
                case "LM":
                    bandit.avancer(Direction.ARRIERE);
                    break;
                case "BM":
                    bandit.avancer(Direction.BAS);
                    break;
                case "stole":
                    bandit.braquer();
                    break;
            }

            model.curAction++;
        }

        model.update();
    }
}
