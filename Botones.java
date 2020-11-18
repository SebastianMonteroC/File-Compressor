import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // ActionListener ActionEvent

public class Botones extends JPanel implements ActionListener {
    public static final String nombre[] = {"COMPRIMIR","DESCOMPRIMIR","SALIR"};
    private JButton [] boton;
    private JFileChooser chooser;
    private Dibujo controlado;
    private String nombreArchivo;
    private Archivo archivo;
    private Interfaz interfaz;
    private Dibujo dibujo;
    public Botones(Interfaz interfaz){
        this.interfaz = interfaz;
        boton = new JButton[  nombre.length ];   
        chooser = new JFileChooser();
        for(int i=0; i< boton.length; ++i){
           boton[i] = new JButton( nombre[i] ); 
           boton[i].addActionListener(this);
           add( boton[i] );
        }
    }
    
    /* Funcion: Le asigna acciones a los botones
    *Param: ActionEvent evento
    *Return: void
    */
    public void actionPerformed( ActionEvent evento ){
       switch(evento.getActionCommand()){
          case "COMPRIMIR":
          int returnVal = chooser.showOpenDialog(this);
          if(returnVal == JFileChooser.APPROVE_OPTION) {
             nombreArchivo=chooser.getSelectedFile().getName();
             JOptionPane.showMessageDialog(null,"Archivo a comprimir: " + nombreArchivo);
             archivo = new Archivo(nombreArchivo,true);
             int pesoOriginal = archivo.getBytesOriginales();
             int pesoComprimido = archivo.getBytesComprimidos();
             interfaz.mostrarDibujos(pesoOriginal ,pesoComprimido );
          }
          break;
          case "DESCOMPRIMIR":
          returnVal = chooser.showOpenDialog(this);
          if(returnVal == JFileChooser.APPROVE_OPTION) {
             nombreArchivo=chooser.getSelectedFile().getName();
             JOptionPane.showMessageDialog(null,"Archivo a descomprimir: " + nombreArchivo);
             archivo = new Archivo(nombreArchivo,false);
             JOptionPane.showMessageDialog(null,"Archivo descomprimido!");
            }   
          break;       
          case "SALIR":
             System.exit(0);
          break;
        }
    }
}
