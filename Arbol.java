
public class Arbol{
    public static final int HIJOS = 2;
    public static final int IZQ = 0;
    public static final int DER = 1;
    private class Nodo {
        public int elemento;
        public int vacio; //Si no tiene un elemento, vacio es 0. Si tiene, es 1. Esto es para guardarlo en binario en el .oof
        public Nodo hijo[];

        public Nodo(int elemento){
            this.elemento = elemento;
            this.vacio = 1;
            hijo= new Nodo[HIJOS]; // SON null
        }
        
        public Nodo(){
            this.vacio = 0;
            hijo = new Nodo[HIJOS];
        }
        
        /* Funcion: Intenta ingresar un nodo a un arbol
        *Param: El nodo a insertar
        *Return: Un boolean insertado:
        *Si logra insertar el nodo, true.
        *Si no lo logra, false.
        */
        public boolean insertarNodo(Nodo nodo){
            boolean insertado = false;
            if(this.hijo[IZQ] == null){
                this.hijo[IZQ] = nodo;
                insertado = true;
            }
            else{
                if(this.hijo[IZQ].vacio == 0){
                    insertado = this.hijo[IZQ].insertarNodo(nodo);
                }
            }
            if(!insertado){
                if(this.hijo[DER] == null){
                    this.hijo[DER] = nodo;
                    insertado = true;
                }
                else{
                    if(this.hijo[DER].vacio == 0){
                        insertado = this.hijo[DER].insertarNodo(nodo);
                    }
                }
            }
            
            return insertado;
        }
        
        /* Funcion: Convierte en un String los datos del 
         * nodo(los hijos y el mismo).
        *Param: ---
        *Return: String, tira 
        */
        public String toString(){
            String tira = "";
            if(hijo[IZQ]!=null){
                tira += hijo[IZQ].toString();
            }
            tira+= " " + elemento;
            if(hijo[DER]!=null){
                tira+= hijo[DER].toString();
            }        
            return tira;
        }
        
        /* Funcion: Convierte en String los bits
        *Param: ---
        *Return: String tira
        */
        public String stringBits(){
            String tira = "" + this.vacio;
            if(this.vacio == 1){
                String elByte = Integer.toBinaryString(this.elemento);
                while(elByte.length() < 8){
                    elByte = 0 + elByte;
                }
                tira += " " + elByte;
            }
            if(hijo[IZQ] != null){
                tira += " " + hijo[IZQ].stringBits();
            }
            if(hijo[DER] != null){
                tira += " " + hijo[DER].stringBits();
            }
            return tira;
        }
    }

    private Nodo raiz;
    private int frecuencia;
    private String camino = "";
    private String arbolCodificado = "";
    private StringBuffer bits;
    
    public Arbol(){
        raiz = new Nodo();
    }
    
    public Arbol(int elemento1, int elemento2, int frecuencia){ //La frecuencia de elemento1 es menor o igual que la frecuencia elemento2
        raiz = new Nodo();
        raiz.hijo[0] = new Nodo(elemento1);
        raiz.hijo[1] = new Nodo(elemento2);
        this.frecuencia = frecuencia;
    }
    
    public Arbol(Arbol arbol, int elemento, int frecuencia){ //La frecuencia del arbol es menor que la del elemento
        raiz = new Nodo();
        raiz.hijo[0] = arbol.raiz;
        raiz.hijo[1] = new Nodo(elemento); 
        this.frecuencia = frecuencia;
    }
    
    public Arbol(int elemento, Arbol arbol, int frecuencia){ //La frecuencia del elemento es menor que la del arbol
        raiz = new Nodo();
        raiz.hijo[0] = new Nodo(elemento);
        raiz.hijo[1] = arbol.raiz;
        this.frecuencia = frecuencia;
    }
    
    public Arbol(Arbol arbol1, Arbol arbol2, int frecuencia){ //La frecuencia del arbol1 es menor que la frecuencia del arbol2
        raiz = new Nodo();
        raiz.hijo[0] = arbol1.raiz;
        raiz.hijo[1] = arbol2.raiz;
        this.frecuencia = frecuencia;
    }
    
    /* Funcion: Convierte en String la informacion de todos los nodos a
     * partir de la raiz(mediante los strings de cada nodo)
    *Param: ---
    *Return: String tira
    */
    public String toString(){
        String tira = "";
        if(raiz!=null){      
            tira += raiz.toString();
        }    
        return tira;
    }
    
    /* Funcion: Busca un elemento especifico
    *Param: int elemento, que es el que se quiere buscar
    *Return: String caminoElemento, que es el camino por el cual se llego
    *a dicho elemento.
    */
    public String buscarElemento(int elemento){
        buscarCamino(raiz,elemento);
        String caminoElemento = this.camino;
        caminoElemento = new StringBuilder(caminoElemento).reverse().toString();
        this.camino = "";
        return caminoElemento;
    }
    
    /* Funcion: metodo para seguir buscando el camino recursivamente mientras 
     * no se haya encontrado
    *Param: Nodo nodo, int elemento 
    *Return: boolean encontrado
    */
    public boolean buscarCamino(Nodo nodo, int elemento){
        boolean encontrado = elemento == nodo.elemento;
        
        if(nodo.hijo[0] != null && !encontrado){
            encontrado = buscarCamino(nodo.hijo[0],elemento);
            if(encontrado){
                camino += "0";
            }
        }
        
        if(nodo.hijo[1] != null && !encontrado){
            encontrado = buscarCamino(nodo.hijo[1],elemento);
            if(encontrado){
                camino += "1";
            }
        }
        
        return encontrado;
    }
    
    /* Funcion: Construye un arbol a partir de una hilera de bytes
    *Param: String bits
    *Return: Entero indice que dice donde se acaba los bits
    *que representan el arbol
    */
    public int construirConBits(String bits){
        StringBuilder bytes = new StringBuilder(bits);
        int indice = 1;
        boolean arbolLeido = false;
        String elementoByte = "";
        while(!arbolLeido){
            if(bytes.charAt(indice) == '0'){
                Nodo nodo = new Nodo();
                this.raiz.insertarNodo(nodo);
                indice++;
            }
            if(bytes.charAt(indice) == '1'){
                for(int i = 0; i < 8 && indice < bits.length(); ++i){
                    indice++;
                    elementoByte += bytes.charAt(indice);
                }
                Nodo nodo = new Nodo(Integer.parseInt(elementoByte,2));
                arbolLeido = !(this.raiz.insertarNodo(nodo));
                if(!arbolLeido){
                    indice++;
                }
                else{
                    for(int i = 0; i < 8; ++i){
                        indice--; 
                    }
                }
            }
            
            elementoByte = ""; 
        }
        return indice;
    }
    
    /*Funcion: Codifica el arbol mediante una tira de bits
    *Param: ---
    *Return: String arbolCodificado
    */
    public String codificarArbol(){
        String arbolCodificado = raiz.stringBits();
        arbolCodificado = quitarEspacios(arbolCodificado);
        return arbolCodificado;
    }
    
    /* Funcion: Quita los espacios del String del arbol que esta 
    * siendo codificado
    *Param: String arbolCodificado
    *Return: llama al .toString y devuelve lo que retorna ese metodo.
    */
    public String quitarEspacios(String arbolCodificado){
        StringBuffer arbol = new StringBuffer(arbolCodificado);
        for(int i = 0; i < arbol.length(); ++i){
            if(arbol.charAt(i) == ' '){
                arbol.deleteCharAt(i);
            }
        }
        return arbol.toString();
    }
    
    /* Funcion: Toma una hilera de bytes codificados y la convierte en los elementos originales
    *Param: String codificado: Es la informacion codificada con Huffman
    *int sobrante: Es la cantidad de  bits desperdiciados en el ultimo byte
    *Return: El string decodificado
    */
    public String decodificar(String codificado, int sobrante){
        String decodificado = "";
        Nodo nodo = raiz;
        for(int i = 0; i < codificado.length()-sobrante; ++i){ 
            if(codificado.charAt(i) == '0' && nodo.hijo[IZQ] != null){
                nodo = nodo.hijo[IZQ];
            }
            if(codificado.charAt(i) == '1'  && nodo.hijo[DER] != null){
                nodo = nodo.hijo[DER];
            }
            if(nodo.vacio == 1){
                decodificado += nodo.elemento + " ";
                nodo = raiz;
            }
        }
        return decodificado;
    }
    
    public int getFrecuencia(){
        return frecuencia;
    }
    
    public String getArbolCodificado(){
        return arbolCodificado;
    }
}
