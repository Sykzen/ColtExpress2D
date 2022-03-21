import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;

class View extends JFrame implements Observer {
    // JFrame
    JFrame settingsFrame;
    //Song
    private Clip clip;
    // Load Image
    private ImageIcon Rm = new ImageIcon("img/right.png");
    private ImageIcon Lm = new ImageIcon("img/left.png");
    private ImageIcon Bm = new ImageIcon("img/bottom.png");
    private ImageIcon Tm = new ImageIcon("img/top.png");
    private ImageIcon action = new ImageIcon("img/action.png");
    private ImageIcon braque = new ImageIcon("img/braquage.png");
    private ImageIcon Ls = new ImageIcon("img/shotLeft.png");
    private ImageIcon Rs = new ImageIcon("img/shotRight.png");
    private ImageIcon Ts = new ImageIcon("img/shotTop.png");
    private ImageIcon Bs = new ImageIcon("img/shotBottom.png");
    private ImageIcon settings = new ImageIcon("img/settings.JPG");
    private ImageIcon marshall = new ImageIcon("img/marshall.png");
    private ImageIcon bandit = new ImageIcon("img/bandit.png");
    private ImageIcon magots = new ImageIcon("img/magot.png");
    private ImageIcon bijoux = new ImageIcon("img/bijoux.png");
    private ImageIcon bourses = new ImageIcon("img/bourse.png");
    private ImageIcon SingON =new ImageIcon("img/sonOn.png");
    private ImageIcon SingOff =new ImageIcon("img/sonOff.png");
    // Load Button
    public JButton LeftShoot=new JButton(Ls);
    public JButton RightShoot=new JButton(Rs);
    public JButton TopShoot =new JButton(Ts);
    public JButton BottomShoot =new JButton(Bs);
    public JButton LeftMove=new JButton(Lm);
    public JButton RightMove=new JButton(Rm);
    public JButton TopMove=new JButton(Tm);
    public JButton BottomMove=new JButton(Bm);
    public JButton Action=new JButton("action",action);
    public JButton Stole=new JButton("    Braquage",braque);
    public JButton Settings=new JButton("Settings",settings);
    public JButton Son=new JButton(SingON);
    public JButton Soff=new JButton(SingOff);
    public JButton update=new JButton("Update");



    // Load Label
    public JLabel Commentaire1=new JLabel("",SwingConstants.RIGHT);
    public JLabel Commentaire2=new JLabel("");
    public JLabel turn=new JLabel("Joueur 1",SwingConstants.RIGHT);
    public JLabel order=new JLabel(" Ordre 0/3");
    public JLabel nbjoueur=new JLabel("Joueur");

    public JLabel nbOrder=new JLabel("Ordre");
    public JLabel nbWagon=new JLabel("Wagon" );

    // Set Contructor
    private ArrayList<JPanel> scores = new ArrayList<>();
    private ViewSettings vs = new ViewSettings();
    private Controller controller;
    private Model model;
    Map<String, ImageIcon> map;
    private ViewGrid viewGrid;
    private JPanel topDivision;

    public View(Model model) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("audio\\ColtExpressSong.wav").getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        if (model.sing){clip.start();}

        this.model = model;
        this.model.addObserver(this);
        this.controller = new Controller(this.model, this);

        this.map = new HashMap<String, ImageIcon>();
        this.map.put(model.train.getMarshall().getNom(), marshall);
        this.map.put("Bijoux",bijoux);
        this.map.put("Bourse",bourses);
        this.map.put("Magot",magots);
        for (Bandit b : model.train.getBandits()) {
            this.map.put(b.getNom(), bandit);
        }

        CreateMainView();
        update(null, null);
    }
    public void CreateMainView(){
        this.setTitle(vs.name);
        this.setSize((int)(vs.width), (int)(vs.height));
        // DONT CHANGE SETLAYOUT !IMPORTANT
        this.setLayout(new GridLayout(3, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(vs.show);
        CreateSettingsFrame();
        viewDivision();

    }
    public void CreateSettingsFrame(){
         this.settingsFrame=new JFrame("Settings");
        this.settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.settingsFrame.setLayout(new GridLayout(2, 1));
        this.settingsFrame.setSize((int)vs.width/2, (int)vs.height/2);
        this.settingsFrame.setVisible(vs.showSettings);
        //this.settingsFrame.setClosable(true);
        this.settingsFrame.setResizable(true);
        JPanel Top=new JPanel(new GridLayout(3,1));
        JPanel Bot=new JPanel();
        Bot.add(update);
        update.addActionListener(controller);

        ArrayList<JLabel> cst=new ArrayList<>(Arrays.asList(nbjoueur,nbWagon,nbOrder));
        for(JLabel i: cst) {

             JButton plus=new JButton("+1 "+i.getText());
             JButton moins=new JButton("-1 "+i.getText());
                plus.addActionListener(controller);
                moins.addActionListener(controller);

            JPanel temp=new JPanel(new GridLayout(1, 3));
            temp.add(i);
            temp.add(plus);
            temp.add(moins);
            Top.add(temp);
        }
        nbWagon.setText("Nombre de Wagon :  "+model.NB_WAGONS);
        nbjoueur.setText("Nombre de Joueurs  "+model.NB_BANDITS);
        nbOrder.setText("Nombre d'Ordre  "+model.NB_ORDRES);

        this.settingsFrame.add(Top);

        this.settingsFrame.add(Bot);





    }



    public void viewDivision() {

        // Top Division (text/settings)
        this.add(topDivision());

        // Middle Division (train/locomotive)
        this.add(middleDivision());

        // Bottom Division (the move/shoot button)
        this.add(bottomDivision());
    }


    private JPanel bottomDivision() {

        JPanel bottom = new JPanel();

        bottom.setLayout(new GridLayout(1,3));

            JPanel JBottomROne=new JPanel(new GridLayout(3,3));
                JBottomROne.add(new JPanel());
                JBottomROne.add(TopShoot);
                TopShoot.setBackground(Color.white);
                TopShoot.addActionListener(controller);
                TopShoot.setActionCommand("TS");
                JBottomROne.add(turn);
                JBottomROne.add(LeftShoot);
                LeftShoot.setBackground(Color.white);
                LeftShoot.addActionListener(controller);
                LeftShoot.setActionCommand("LS");
                JBottomROne.add(new JPanel());
                JBottomROne.add(RightShoot);
                RightShoot.setBackground(Color.white);
                RightShoot.addActionListener(controller);
                RightShoot.setActionCommand("RS");
                JBottomROne.add(new JPanel());
                JBottomROne.add(BottomShoot);
                BottomShoot.setBackground(Color.white);
                BottomShoot.addActionListener(controller);
                BottomShoot.setActionCommand("BS");
                JBottomROne.add(Commentaire1);

            JPanel JBottomRTwo=new JPanel(new GridLayout(3,3));
                JBottomRTwo.add(order);
                JBottomRTwo.add(TopMove);
                TopMove.setBackground(Color.white);
                TopMove.addActionListener(controller);
                TopMove.setActionCommand("TM");
                JBottomRTwo.add(new JPanel());
                JBottomRTwo.add(LeftMove);
                LeftMove.setBackground(Color.white);
                LeftMove.addActionListener(controller);
                LeftMove.setActionCommand("LM");
                JBottomRTwo.add(new JPanel());
                JBottomRTwo.add(RightMove);
                RightMove.setBackground(Color.white);
                RightMove.addActionListener(controller);
                RightMove.setActionCommand("RM");
                JBottomRTwo.add(Commentaire2);
                JBottomRTwo.add(BottomMove);
                BottomMove.setBackground(Color.white);
                BottomMove.addActionListener(controller);
                BottomMove.setActionCommand("BM");
                JBottomRTwo.add(new JPanel());

            JPanel JBottomRThree=new JPanel(new GridLayout(2,3));
                JBottomRThree.add(Action);
                Action.setBackground(Color.white);
                Action.addActionListener(controller);
                Action.setActionCommand("action");
                JBottomRThree.add(Stole);
                Stole.setBackground(Color.white);
                Stole.addActionListener(controller);
                Stole.setActionCommand("stole");

        bottom.add(JBottomROne);
        bottom.add(JBottomRTwo);
        bottom.add(JBottomRThree);

        return bottom;
    }

    private JPanel middleDivision() {

        JPanel middle = new JPanel();

        this.viewGrid = new ViewGrid(this, model);
        middle.add(this.viewGrid);

        return middle;
    }

    public void updateTopBandits() {

        for (int n = 0; n < 5; n++) {
            scores.get(n).setVisible(n < model.getNbBandits());
        }
    }

    private JPanel topDivision() {

        topDivision = new JPanel(new GridLayout(1, 6));

        for (int n = 0; n < 5; n++) {

            JPanel col = new JPanel();

            if (n < model.getNbBandits()) {
                scores.add(col);
                showBanditStuff(col, model.train.getBandits().get(n));
            }

            topDivision.add(col, n);
        }

        JPanel lastCol = new JPanel(new GridLayout(2,1));

        lastCol.add(Settings);
        Settings.setBackground(Color.white);
        Settings.addActionListener(controller);

        lastCol.add(model.getSing() ? Son : Soff);
        Son.setBackground(Color.white);
        Son.addActionListener(controller);
        Soff.setBackground(Color.white);
        Soff.addActionListener(controller);

        lastCol.setBackground(new Color(150,150,150));

        topDivision.add(lastCol);

        return topDivision;
    }

    private void showBanditStuff(JPanel jp, Bandit b) {

        jp.setLayout(new GridLayout(5,2));
        jp.add(new JLabel(bandit));
        jp.add(new JLabel(b.getNom()));

        jp.add(new JLabel(bourses));
        jp.add(new JLabel());

        jp.add(new JLabel(bijoux));
        jp.add(new JLabel());

        jp.add(new JLabel(magots));
        jp.add(new JLabel());

        jp.add(new JLabel("Total: ", SwingConstants.RIGHT));
        jp.add(new JLabel());

        jp.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    // -------------------Set Changement-------------------------
    public void setTour(int n) {

        int nbjoueur = model.getNbBandits();

        this.turn.setText("Joueur " + (1 + (n / model.NB_ORDRES) % nbjoueur));
        this.order.setText(" Ordre " + n % model.NB_ORDRES + "/" + model.NB_ORDRES);

        if (model.actions.size() == model.NB_ORDRES * nbjoueur) {
            this.turn.setText("Appuyez sur ");
            this.order.setText("Action !");
        }
    }

    public void setSing(){

        if(model.sing==true) {
            clip.stop();

            this.Son.setIcon(SingOff);
            this.Soff.setIcon(SingOff); // si model.sing=false sur le modele
            model.sing=false;
        }
            else {
            this.Son.setIcon(SingON);
            this.Soff.setIcon(SingON); // si model.sing=false sur le modele
            model.sing = !model.sing;
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void setCommentaire(String message) {
        //message = new String(message.getBytes(), Charset.forName("ISO-8859-1"));
        if (message.isEmpty()) {
            this.Commentaire1.setText("");
            this.Commentaire2.setText("");
        }
        else {
            this.Commentaire1.setText(message.substring(0, message.length() / 2));
            this.Commentaire2.setText(message.substring(message.length() / 2));
        }
    }

    public void updateScores() {

        for (int b = 0; b < model.getNbBandits(); b++) {

            int numBijoux = 0, numMagots = 0, numBourses = 0, total = 0;

            for (Butin butin : model.getBandit(b).getButins()) {
                if ("Bijoux".equals(butin.getNom())) numBijoux++;
                else if ("Bourse".equals(butin.getNom())) numBourses++;
                else if ("Magot".equals(butin.getNom())) numMagots++;
                total += butin.getValeur();
            }

            setBijoux(b, numBijoux);
            setBourses(b, numBourses);
            setMagots(b, numMagots);
            setTotal(b, total);
        }
    }

    public void setBourses(int b, int nombre){
        ((JLabel)scores.get(b).getComponent(3)).setText("Bourses: " + nombre);
    }

    public void setBijoux(int b, int nombre){
        ((JLabel)scores.get(b).getComponent(5)).setText("Bijoux: " + nombre);
    }

    public void setMagots(int b, int nombre){
        ((JLabel)scores.get(b).getComponent(7)).setText("Magots: " + nombre);
    }

    public void setTotal(int b, int nombre){
        ((JLabel)scores.get(b).getComponent(9)).setText(nombre + "$");
    }
    public void setNb_BANDITS(int relatif){
        if(((model.NB_BANDITS>=1 && model.NB_BANDITS <=4) && relatif==1)||((model.NB_BANDITS>1 && model.NB_BANDITS <=5) && relatif==-1)) {
            model.NB_BANDITS = model.NB_BANDITS + relatif;
        }
        nbjoueur.setText("Nombre de Joueurs  "+model.NB_BANDITS);

    }
    public void setNB_WAGONS(int relatif){
        if(((model.NB_WAGONS>=1 && model.NB_WAGONS <=5) && relatif==1)||((model.NB_WAGONS>1 && model.NB_WAGONS <=6) && relatif==-1)) {
        model.NB_WAGONS=model.NB_WAGONS+relatif;}
        nbWagon.setText("Nombre de Wagon :  "+model.NB_WAGONS);

    }
    public void setNB_ORDRES(int relatif){
        if((model.NB_ORDRES>=1 && relatif==1) || (model.NB_ORDRES>1 && relatif==-1)) {
            model.NB_ORDRES = model.NB_ORDRES + relatif;
        }
        nbOrder.setText("Nombre d'Ordre  "+model.NB_ORDRES);

    }

    public int getCurrentDimensionWidth(){
        return (int)this.getContentPane().getSize().getWidth();
    }

    public int getCurrentDimensionHeight(){
        return (int)this.getContentPane().getSize().getHeight();
    }

    public ImageIcon getImageOfObject(String object){
        return this.map.get(object);
    }


    @Override
    public void update(Observable o, Object arg) {
        updateScores();
        this.viewGrid.update();
        repaint();
    }


    public void ShowGuiSettings(){

        vs.showSettings=!settingsFrame.isShowing();
        settingsFrame.setVisible(vs.showSettings);
    }

    public void update() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
       // SwingUtilities.updateComponentTreeUI(this);
        //this.dispose();
       // settingsFrame.dispose();

       // CreateMainView();
    }
}






