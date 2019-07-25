import javax.swing.*;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;        
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.LayoutStyle.ComponentPlacement.*;
import javax.swing.*;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;        
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.LayoutStyle.ComponentPlacement.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.math.BigInteger;
import java.util.concurrent.*;
import java.lang.Math;


public class Simulatore implements ActionListener{
    static JFrame frame_inicio,frame_simulacion;
    JTextField dimensionField,alfaField,betaField,gammaField;
    JPanel pane;
    GraphicPanelCellAutomata graphicPane;
    int dimension;
    float alfa_=1.0f,beta_=1.0f,gamma_=1.0f;
    static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    Simulatore()
    {
      dimension=200;
    }

    public void actionPerformed(ActionEvent e){
      if("crear simulacion".equals(e.getActionCommand()))
      {

        frame_simulacion = new JFrame("Simulacion");
        frame_simulacion.setPreferredSize(new Dimension(dimension,dimension));     
        graphicPane = new GraphicPanelCellAutomata(dimension,alfa_,beta_,gamma_);
        frame_simulacion.setContentPane(graphicPane);
        frame_simulacion.pack();
        frame_simulacion.setVisible(true);
      }
      else if("dimension".equals(e.getActionCommand()))
      {
        dimension=Integer.parseInt(dimensionField.getText());
      }
      else if("iniciar simulacion".equals(e.getActionCommand()))
      {
        graphicPane.dibujar(); 
      }
      else if("fin".equals(e.getActionCommand()))
      {
        graphicPane.parar();
      }
      else if("alfa".equals(e.getActionCommand()))
      {
      	alfa_ = Float.parseFloat(alfaField.getText());
      }else if("beta".equals(e.getActionCommand()))
      {
      	beta_ = Float.parseFloat(betaField.getText());
      }else if("gamma".equals(e.getActionCommand()))
      {
      	gamma_ = Float.parseFloat(gammaField.getText());
      }
    }
   

    private void createPaneInicio()
    {
      pane = new JPanel(new BorderLayout());
      JButton simularButton = new JButton("crear simulacion"),iniciar_simul = new JButton("iniciar simulacion");
      
      JButton fin = new JButton("fin");
      JLabel etAlf=new JLabel(Character.toString((char)945)),etBet=new JLabel(Character.toString((char)946)),etGam=new JLabel(Character.toString((char)947));
      JLabel dimen=new JLabel("dimension");
     
      iniciar_simul.addActionListener(this);
      simularButton.addActionListener(this);
      fin.addActionListener(this);
      dimensionField = new JTextField("200");
      dimensionField.addActionListener(this);
      dimensionField.setActionCommand("dimension");
      alfaField=new JTextField("1.0");
      betaField=new JTextField("1.0");
      gammaField=new JTextField("1.0");
      alfaField.addActionListener(this);
      betaField.addActionListener(this);
      gammaField.addActionListener(this);
      alfaField.setActionCommand("alfa");
      betaField.setActionCommand("beta");
      gammaField.setActionCommand("gamma");

      GroupLayout layout = new GroupLayout(pane);
      layout.setAutoCreateGaps(true);
      layout.setAutoCreateContainerGaps(true);
      pane.setLayout(layout);

      layout.setHorizontalGroup(layout.createSequentialGroup()
      	.addGroup(layout.createParallelGroup(LEADING)
      	  .addComponent(dimen)
          .addComponent(etAlf)
          .addComponent(etBet)
          .addComponent(etGam)
        )
        .addGroup(layout.createParallelGroup(LEADING)
          .addComponent(dimensionField)
          .addComponent(simularButton)
          .addComponent(iniciar_simul)
          .addComponent(alfaField)
          .addComponent(betaField)
          .addComponent(gammaField)
          .addComponent(fin)
        )

      );      

      layout.setVerticalGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(BASELINE)
          .addComponent(dimen)
          .addComponent(dimensionField)
        )
        .addGroup(layout.createParallelGroup(BASELINE)
          .addComponent(simularButton)
        )
         .addGroup(layout.createParallelGroup(BASELINE)
          .addComponent(iniciar_simul)
        )
         .addGroup(layout.createParallelGroup(BASELINE)
          .addComponent(etAlf)
          .addComponent(alfaField)
        )
         .addGroup(layout.createParallelGroup(BASELINE)
          .addComponent(etBet)
          .addComponent(betaField)
        )
         .addGroup(layout.createParallelGroup(BASELINE)
          .addComponent(etGam)
          .addComponent(gammaField)
        )
         .addGroup(layout.createParallelGroup(BASELINE)
          .addComponent(fin)
        )
      );

      Border borde = BorderFactory.createLineBorder(new Color(51,134,202));
      TitledBorder borde_titulo = BorderFactory.createTitledBorder(borde,"Juego de la vida");
    
      pane.setBorder(borde_titulo);
      pane.setBackground(new Color(176,201,247));
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        Simulatore sim = new Simulatore();
        /*FRAME OPTIONS*/
        frame_inicio = new JFrame("SimulatoreInicio");
        frame_inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_inicio.setPreferredSize(new Dimension(250,250));

        
        int x = (int)(dim.getWidth()/2);
        int y = (int)(dim.getHeight()/2);
        
        frame_inicio.setLocation(x,y);
        sim.createPaneInicio();
        frame_inicio.setContentPane(sim.pane);

        frame_inicio.pack();
        frame_inicio.setVisible(true);  
    }
 
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

//PANEL PARA DIBUJAR RANDOM ***PRACTICA 6****
class GraphicPanelCellAutomata extends JPanel{
  private static final long serialVersionUID = 1L;//REVISAR
  int dimension_;
  belZab automata_;
  int generaciones;
  boolean verdad;
  Object l;
  public GraphicPanelCellAutomata(int dimension,float a,float b,float g){
    super(new BorderLayout());
    l=new Object();
    dimension_ = dimension;
    automata_ =  new belZab(dimension,a,b,g);
    verdad=true;
  }

  public void parar(){
        verdad=false;
  }

  public void dibujar()
  {
    Thread  updateThread = new Thread(){
      public void run(){
        while(verdad){
          
          repaint();
          try{
            Thread.sleep(50);
          }catch(InterruptedException e){}
          automata_.nextGen();
        }
      }
    };
    updateThread.start();
  }

  public void  paintComponent(Graphics g)
  {
    super.paintComponent(g);  
    
    for(int y=0; y<dimension_;++y)
    {
      for(int x=0; x<dimension_; ++x)
      {
        g.setColor(new Color((float)automata_.c[x][y][automata_.p_],(float)automata_.b[x][y][automata_.p_],(float)automata_.a[x][y][automata_.p_]));
        g.fillRect(x,y,1,1);
      }
    }
    ++generaciones;
  }

}
