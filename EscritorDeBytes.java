import java.io.*;
public class EscritorDeBytes{
    private BufferedOutputStream salida;
    private String path;
    
    public EscritorDeBytes( String nombre){
       try {
          FileOutputStream file = new FileOutputStream(nombre);
          salida = new BufferedOutputStream( file );
          this.path = nombre;
        }
       catch(IOException e){
          System.err.println("Error al crear archivo "+nombre); 
       }    
    }
    
    /* Funcion: Escribe un byte en un archivo.
    *Param: int elByte que sera escrito
    *Return: void
    */
    public void guardar(int elByte){
       try {
          salida.write(elByte);
       }
       catch(IOException e){
          System.err.println("Error al escribir en archivo "); 
       }  
    }

    /* Funcion: Cierra el archivo.
    *Param: ---
    *Return: void
    */
    public void close(){
       try {
          salida.close();
       }
       catch(IOException e){
          System.err.println("Error al cerrar archivo "); 
       } 
    }
}
