import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import java.util.Random;

public class MIDPCanvas extends GameCanvas implements CommandListener,Runnable 
{
 
  private Image avion;  
  private Image avione;  
  private Image avione2;  
  private Image avione3;  
  private Image avione4;  
  private Image mon;  
  private Image fon;  
  private Image bom;  
  private Image vida1,vida2,vida3;  
  private Image mon2;  
 
  private Sprite s_avion;  
  private Sprite s_avione;  
  private Sprite s_avione2;  
  private Sprite s_avione3;  
  private Sprite s_avione4;  
  private Sprite s_mon,s_mon2;  
  private Sprite s_fon;  
  private Sprite s_bom;  
  private Sprite s_vida1,s_vida2,s_vida3;  
    
    Thread hilo;
    int tecla=0;
    Graphics g= getGraphics();
    Random random = new Random();
    int X=0, Y=0, X2=0, Y2=0, X3=0, Y3=0, X4=0, Y4=0, z=0;
    int puntaje =0;
    
    public MIDPCanvas() 
    {
        super(true);
        try {
            avion = Image.createImage("avion.png");
            avione  = Image.createImage("avione.png");
            avione2= Image.createImage("avione2.png");
            avione3= Image.createImage("avione3.png");
            avione4= Image.createImage("avione4.png");
            mon        = Image.createImage("mon.png");
            fon        = Image.createImage("fon.jpg");
            bom        = Image.createImage("bom.png");
            mon2      = Image.createImage("mon2.png");
            vida1    = Image.createImage("vida1.png");
            vida2    = Image.createImage("vida2.png");
            vida3    = Image.createImage("vida3.png");
            
            s_avion    = new Sprite(avion);
            s_avione  = new Sprite(avione);
            s_avione2= new Sprite(avione2);
            s_avione3= new Sprite(avione3);
            s_avione4= new Sprite(avione4);
            s_mon        = new Sprite(mon);
            s_mon2      = new Sprite(mon2);
            s_fon        = new Sprite(fon);
            s_bom        = new Sprite(bom);
            s_vida1= new Sprite(vida1);
            s_vida2= new Sprite(vida2);
            s_vida3= new Sprite(vida3);
            
            int valores[] = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
            s_bom.setFrameSequence(valores);
            
            int valores2[] = new int[] {0,1,2,3,4,5,6,7,8,9,10,11};
            s_mon2.setFrameSequence(valores2);
            
            s_avion.setPosition((getWidth()/2)-16, (getHeight()/2)- 16);
            
            s_avione.setPosition(20, -40);
            s_avione2.setPosition(30, 300);
            s_avione3.setPosition(-30, 40);
            s_avione4.setPosition(300, 40);
            
            
            setCommandListener(this);
            
            
            addCommand(new Command("Exit", Command.EXIT, 1));
        } catch (Exception e) {  e.printStackTrace();}
    
        hilo=new Thread(this);
        hilo.start();
    }

    public void run(){
    try 
     {
       while (true)
       {
        dibuja_avionsito();
        Thread.sleep(100);
        flushGraphics();
       }    
     }catch(Exception e) { e.printStackTrace();}
   
    }
    public void dibuja_avionsito()
   {
   tecla =getKeyStates();
   s_fon.paint(g);
   s_avion.paint(g);
   s_avione.paint(g);
   s_avione2.paint(g);
   s_avione3.paint(g);
   s_avione4.paint(g);
   
   s_mon.paint(g);
   s_vida1.paint(g);
   s_vida2.paint(g);
   s_vida3.paint(g);
   
   s_avione.move(0, 5);
   s_avione2.move(0, -5);
   s_avione3.move(0, 5);
   s_avione4.move(0, -5);
   s_mon.move(0, 5);
   
   g.setColor(0,0,0);
   g.drawString("Vidas = " , 100, 0, Graphics.TOP | Graphics.LEFT);
   g.drawString("Puntaje = " + puntaje , 100, 15, Graphics.TOP | Graphics.LEFT);
   
   if(tecla==32){s_avion.move(5, 0);}
   if(tecla==4){s_avion.move(-5, 0);}
   if(tecla==2){s_avion.move(0, -5);}
   if(tecla==64){s_avion.move(0, 5);}
    
   valida_limites();
   
   if(s_avione.collidesWith(s_avion, true)){
   puntaje=puntaje -40;
   s_avione.setVisible(false);
   for (z=0; z<23; z++)
           {    
            s_bom.setFrame(z+1);
            //s_bomba.nextFrame();
            s_bom.setPosition(s_avione.getX(), s_avione.getY());
            s_bom.paint(g);
            flushGraphics();
              try
              {
               Thread.sleep(100);
              }catch (Exception e){e.printStackTrace();}
           }
        nuevo_avion_contrincante1();
      }
    
    if(s_avion.collidesWith(s_mon,true))
       {
        puntaje = puntaje + 10;
        posiciones_aleatorias();
        s_mon.setPosition(X, Y);    
       }
      
   
    if (s_avione.getY() >= getHeight())
     {
       nuevo_avion_contrincante1();
     }
    
   if (s_avione2.getY() <=0)
     {
      nuevo_avion_contrincante2();
     }
    if (s_avione3.getX() >= getWidth())
     {
       nuevo_avion_contrincante3();
     }
    
   if (s_avione4.getX() <=0)
     {
      nuevo_avion_contrincante4();
     }
   
    if (s_mon.getY() >= getHeight())
    {
     posiciones_aleatorias();
     s_mon.setPosition(X, Y);
    }
   }
    
    public void paint(Graphics g) {
        g.drawString("Sample Text", 0, 0, Graphics.TOP | Graphics.LEFT);
    }
public void valida_limites()
   {
    if(s_avion.getX()>= getWidth()-32) //limite derecho
        {s_avion.move(-5, 0);}
    
    if(s_avion.getX()<=0) //limite izquierdo
        {s_avion.move(5, 0);}
    
    if (s_avion.getY()>=getHeight()-32) //ARRIBA
        {s_avion.move(0,-5);}
        
    if (s_avion.getY()<=0) //ABAJO
        {s_avion.move(0,5);}
    
   }
    
public void posiciones_aleatorias()
    {
     X = (int)(random.nextDouble()* (getWidth()-32) + 0); //genera coodenadas no mayor al ancho del display
     Y = (int)(random.nextDouble()* (-80) + (-50));//genera coordenasa no mayor al alto del display 
     
     X2 = (int)(random.nextDouble()* (getWidth()-32) + 0);
     Y2 = (int)(random.nextDouble()* (getHeight()+30) + getHeight());
     
     X3 = (int)(random.nextDouble()* (getWidth()-32) + 0); 
     Y3 = (int)(random.nextDouble()* (-80) + (-50));
     
     X4 = (int)(random.nextDouble()* (getWidth()-32) + 0);
     Y4 = (int)(random.nextDouble()* (getHeight()+30) + getHeight());
    }

public void nuevo_avion_contrincante1()
    {
     posiciones_aleatorias();
     s_avione.setVisible(true);
     s_avione.setPosition(X,Y);
    }
   
   public void nuevo_avion_contrincante2()
    {
     posiciones_aleatorias();
     s_avione2.setPosition(X2,Y2);
    }
   
   public void nuevo_avion_contrincante3()
    {
     posiciones_aleatorias();
     s_avione3.setPosition(X3,Y3);
    }
     
   public void nuevo_avion_contrincante4()
    {
     posiciones_aleatorias();
     s_avione4.setPosition(X4,Y4);
    }
    protected void keyPressed(int keyCode) {
    }

    /**
     * Called when a key is released.
     */
    protected void keyReleased(int keyCode) {
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected void keyRepeated(int keyCode) {
    }

    /**
     * Called when the pointer is dragged.
     */
    protected void pointerDragged(int x, int y) {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected void pointerPressed(int x, int y) {
    }

    /**
     * Called when the pointer is released.
     */
    protected void pointerReleased(int x, int y) {
    }

    /**
     * Called when action should be handled
     */
    public void commandAction(Command command, Displayable displayable) {
    }
}
    
