import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
public class Midlet extends MIDlet {

    public void startApp() 
    {
        Canvas objeto = new MIDPCanvas();
        Display.getDisplay(this).setCurrent(objeto);
    }
    
    public void pauseApp() {
    }
   
    public void destroyApp(boolean unconditional) {
    }
}
