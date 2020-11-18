import java.io.*;
public class LectorDeBytes {
   private BufferedInputStream entrada; 
   
   public LectorDeBytes(String nombre){
       try {
          entrada = new BufferedInputStream(new FileInputStream(nombre));
       }
       catch(IOException e){
          System.err.println("Error al abrir archivo " + nombre); 
       }
   }
   
   /* Funcion: Lee el archivo y devuelve un byte
    *Param: ---
    *Return: int elByte
    */
   public int read(){
       int elByte = -1;
       try {
          elByte = entrada.read();
       }
       catch(IOException e){
          System.err.println("Error al leer de archivo "); 
       }
       return elByte;
   }
   
   /* Funcion: Cierra el archivo
    *Param: ---
    *Return: void
    */
   public void close(){
       try{
          entrada.close();
       }
       catch(IOException e){
          System.err.println("Error al cerrar archivo"); 
       }        
   }  
}
