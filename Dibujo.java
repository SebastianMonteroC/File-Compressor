
import javax.swing.*;
import java.awt.*;
public class Dibujo extends JPanel {
    private int x;
    private int y;
    private int ancho;
    private int largo;
    private int bytesComprimidos;
    private int bytesOriginales;
    private JFrame padre;
    public Dibujo(JFrame padre, int bytesOriginales, int bytesComprimidos){
        x = 200;
        y = 200;
        largo = 200;
        ancho = 70;
        this.bytesOriginales= bytesOriginales;
        this.bytesComprimidos= bytesComprimidos;
        this.padre = padre;
    }
    
    /* Funcion: Construye el dibujo mostrado en pantalla por la interfaz
    *Param: Graphics g
    *Return: void
    */
    public void paintComponent(Graphics g){
        super.paintComponent(g); 
        g.setColor( new Color( 255, 255, 0 ) ); // Color.YELLOW
        g.fillRect( x, y, ancho, largo);
        g.setColor( new Color( 238, 238,238) );
        g.fillRect(x,y,ancho,0);
        g.setColor( Color.BLACK );
        g.setColor( new Color(255,0,0)); // Color.RED
        g.drawRect( x , y , ancho, largo);
        g.setColor( Color.BLUE);
        g.drawString("Antes pesaba: " + bytesOriginales + " bytes", x-20 , y-10); 
        //__________________________________
        x+=190;
        g.setColor( new Color( 255, 255, 0 ) ); // Color.YELLOW
        g.fillRect( x, y, ancho, largo);
        g.setColor( new Color( 238, 238,238) );
        g.fillRect(x,y,ancho,largo-113);
        g.setColor( Color.BLACK );
        g.setColor( new Color(255,0,0)); // Color.RED
        g.drawRect( x , y , ancho, largo);
        g.setColor( Color.BLUE);
        g.drawString("Ahora pesa: " + bytesComprimidos + " bytes", x-20 , y-10); 
    }
}