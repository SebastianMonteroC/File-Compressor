import java.io.*;

public class Archivo{
    private String codigoBinario;
    private String archivoBinario = "";
    private int bytesOriginales;
    private int bytesComprimidos;
    private String path;
    private LectorDeBytes lector;
    private EscritorDeBytes escritor;
    private ListaDobleOrdenada listaBytes;
    
    public Archivo(String path, boolean comprimir){
        this.path = path;
        lector = new LectorDeBytes(path);
        if(comprimir){
            escritor = new EscritorDeBytes(path + ".oof");
            listaBytes = new ListaDobleOrdenada();
            comprimirArchivo();
        }
        else{
            escritor = new EscritorDeBytes(quitarExtension(path));
            descomprimirArchivo();
        }
    }
    
    /* Funcion: Crea una lista con bytes a partir del archivo
    *Param: ---
    *Return: void
    */
    public void construirListaDeBytes(){
        boolean leido = false;
        int byteActual;
        while(!leido){
            byteActual = lector.read();
            if(byteActual != -1){
                listaBytes.insertar(byteActual);
            }
            else{
                leido = true;
            }
        }
        lector.close();
        lector = new LectorDeBytes(path);
        listaBytes.crearArbolHuffman();
        
        this.bytesOriginales = listaBytes.getFrecuencia();
    }
    
    /* Funcion: Codifica los bytes con el algoritmo de Huffman
    *Param: ---
    *Return: void
    */
    public void codificarBytes(){
        boolean leido = false;
        int byteActual; 
        while(!leido){
            byteActual = lector.read();
            if(byteActual != -1){
                archivoBinario += listaBytes.getHuffman().buscarElemento(byteActual);
            }
            else{
                leido = true;
            }
        }
    }
    
    /* Funcion: Construye la tira final en binario que sera
     * guardada en el archivo comprimido
    *Param: ---
    *Return: void
    */
    public void construirBinarioFinal(){
        String binario = "000";
        StringBuilder binarioFinal;
        String sobranteBinario;
        binario += listaBytes.getHuffman().codificarArbol();
        binario += archivoBinario;
        int sobrante = 0;
        int bytes = 0;
        
        for(int i = 1; i < binario.length()+1; ++i){
            if(i % 8 == 0){
                ++bytes;
            }
            if(i == binario.length() && i % 8 != 0){
                sobrante = (8 - (binario.length() - (bytes * 8)));
                while(i%8 != 0){
                    binario += '0';
                    i++;
                }
            }
        }
        sobranteBinario = Integer.toBinaryString(sobrante);
        binarioFinal = new StringBuilder(binario);
        aplicarSobrante(sobranteBinario,binarioFinal);
        
    }
    
    /* Funcion: guarda el archivo
    *Param: ---
    *Return: void
    */
    public void guardarArchivo(){
        escritor = new EscritorDeBytes(path+".oof");
        String byteTemporal = "";
        for(int i = 0; i < codigoBinario.length(); ++i){
            byteTemporal += codigoBinario.charAt(i);
            if((i+1)%8 == 0){
                escritor.guardar(Integer.parseInt(byteTemporal,2));
                byteTemporal = "";
            }
        }
        escritor.close();
    }
    
    /* Funcion: metodo que comprime los archivos mediante el llamado de otros
     * metodos.
    *Param: ---
    *Return: void
    */
    public void comprimirArchivo(){
        construirListaDeBytes();
        codificarBytes();
        construirBinarioFinal();
        guardarArchivo();
    }
    
    /* Funcion: Toma una archivo .oof y lo descomprime a traves de llamado de distintos metodos
    *Param: ---
    *Return: void
    */
    public void descomprimirArchivo(){
        int byteActual;
        boolean archivoLeido = false;
        int sobrante;
        int indiceDeBinario = 1;
        String decodificado = "";
        String archivoBinario = "";
        String byteBinario = "";
        StringBuilder stringManipulable;
        String sobranteString;
        Arbol arbolDecodificado = new Arbol();

        while(!archivoLeido){
            byteActual = lector.read();
            if(byteActual != -1){
                byteBinario = Integer.toBinaryString(byteActual);
                while(byteBinario.length() != 8){
                    byteBinario = 0 + byteBinario;
                }
                archivoBinario += byteBinario;
            }
            else{
                archivoLeido = true;
            }
        }
        
        String sinSobrante = "";
        for(int i = 3; i<archivoBinario.length(); ++i){
            sinSobrante += archivoBinario.charAt(i);
        }
        
        sobranteString = "" + archivoBinario.charAt(0) + archivoBinario.charAt(1) + archivoBinario.charAt(2);
        sobrante = Integer.parseInt(sobranteString,2);

        indiceDeBinario = arbolDecodificado.construirConBits(sinSobrante);
        
        archivoBinario = sinSobrante;
        sinSobrante = "";
        
        for(int i = indiceDeBinario; i < archivoBinario.length(); ++i){
            sinSobrante += archivoBinario.charAt(i);
        }
        archivoBinario = sinSobrante;
        
        decodificado = arbolDecodificado.decodificar(archivoBinario,sobrante);
        
        String temporal = "";
        for(int i = 0; i < decodificado.length(); ++i){
            if(decodificado.charAt(i) != ' '){
                temporal += decodificado.charAt(i);
            }
            else{
                escritor.guardar(Integer.parseInt(temporal));
                temporal = "";
            }
        }
        escritor.close();
    }

    public int getBytesOriginales(){
        return bytesOriginales;
    }
    
    public int getBytesComprimidos(){
        return bytesComprimidos;
    }
    
    /* Funcion: Quita la extension .oof de un archivo
    *Param: String path que contiene el nombre del archivo con
    *la extension .oof
    *Return: el nombre del archivo sin la extension .oof
    */
    public String quitarExtension(String path){
        String pathSinExtension = path.substring(0, path.lastIndexOf('.'));
        return pathSinExtension;
    }
    
    /* Funcion: 
    *Param: String sobranteBinario
    *Param: StringBuilder binarioFinal
    *Return: void
    */
    public void aplicarSobrante(String sobranteBinario, StringBuilder binarioFinal){
        if(sobranteBinario.length() == 1){
            binarioFinal.setCharAt(2,sobranteBinario.charAt(0));
        }
        if(sobranteBinario.length() == 2){
            binarioFinal.setCharAt(1,sobranteBinario.charAt(0));
            binarioFinal.setCharAt(2,sobranteBinario.charAt(1));
        }
        if(sobranteBinario.length() == 3){
            binarioFinal.setCharAt(0,sobranteBinario.charAt(0));
            binarioFinal.setCharAt(1,sobranteBinario.charAt(1));
            binarioFinal.setCharAt(2,sobranteBinario.charAt(2));
        }
        codigoBinario = binarioFinal.toString();
        this.bytesComprimidos = codigoBinario.length()/8;
    }
}
