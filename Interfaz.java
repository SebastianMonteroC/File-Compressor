import javax.swing.*;
import java.awt.*;
public class Interfaz extends JFrame {
    private Dibujo dibujo;
    private Botones botones;
    public  Container contenedor;
    public Interfaz(){
        super("Compresor");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        botones = new Botones(this);
        contenedor = getContentPane();
        contenedor.add(BorderLayout.NORTH, botones);
        setVisible(true);
    }
    
    /* Funcion: Muestra en pantalla los dibujos
    *Param: int x, int y
    *Return: void
    */
    public void mostrarDibujos(int bytesOriginales, int bytesComprimidos){
        dibujo = new Dibujo(this,bytesOriginales,bytesComprimidos);
        contenedor.add(BorderLayout.CENTER, dibujo);
        setVisible(true);
    }
}
