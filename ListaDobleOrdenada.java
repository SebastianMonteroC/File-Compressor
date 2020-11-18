
public class ListaDobleOrdenada {
    private class Celda {
        public int elemento;
        public int frecuencia;
        public Arbol arbol;
        public Celda anterior;
        public Celda siguiente;
        
        public Celda(int elemento){
            frecuencia = 1;
            this.elemento = elemento;
        }
        
        public Celda(Arbol arbol){
            this.arbol = arbol;
            this.frecuencia = arbol.getFrecuencia();
        }
        /* Funcion: Inserta un elemento en la lista
        *Param: int elemento, el que se quiere insertar
        *Return: void
        */
        public void insertarElemento(int elemento){
            if(elemento == this.elemento){
                this.frecuencia++;
                if(!(this == primera && this == ultima)){
                    this.reacomodarCasilla();
                }
            }
            else{
                if(this.siguiente != null){
                    this.siguiente.insertarElemento(elemento);
                }
                else{
                    Celda nueva = new Celda(elemento);
                    primera.anterior = nueva;
                    nueva.siguiente = primera;
                    primera = nueva;
                }
            }
        }
        /* Funcion: Recomoda la casilla de manera ascendente
        *Param: ---
        *Return: void
        */
        public void reacomodarCasilla(){
            boolean mayorQueCelda = false;
            if(this.siguiente != null){
                Celda celdaActual = this.siguiente;
                if(this.frecuencia == celdaActual.frecuencia){
                    mayorQueCelda = true;
                }
                while(!mayorQueCelda){ 
                    if(this.frecuencia > celdaActual.frecuencia){
                        if(celdaActual.siguiente != null){
                            celdaActual = celdaActual.siguiente;
                        }
                        else{
                            mayorQueCelda = true;
                            if(this == primera){
                                primera = this.siguiente;
                                this.siguiente.anterior = null;
                                this.anterior = celdaActual;
                                celdaActual.siguiente = this;
                                this.siguiente = null;
                                ultima = this;
                            }
                            else{
                                this.anterior.siguiente = this.siguiente;
                                this.siguiente.anterior = this.anterior;
                                this.anterior = celdaActual;
                                celdaActual.siguiente = this;
                                this.siguiente = null;
                                ultima = this;
                            }
                        }
                    }
                    else{
                        if(this == primera){
                            primera = this.siguiente;
                            this.siguiente = celdaActual;
                            this.anterior = celdaActual.anterior;
                            celdaActual.anterior.siguiente = this;
                            celdaActual.anterior = this;
                            primera.anterior = null;
                            mayorQueCelda = true;
                        }
                        else{
                            this.anterior.siguiente = this.siguiente;
                            this.siguiente.anterior = this.anterior;
                            this.siguiente = celdaActual;
                            this.anterior = celdaActual.anterior;
                            celdaActual.anterior.siguiente = this;
                            celdaActual.anterior = this;
                            mayorQueCelda = true;
                        }
                    }
                }
            }
        }
        
        /* Funcion: Convierte en String 
        *Param: ---
        *Return: String tira
        */
        public String toString(){
            String tira = "";
            if(this.arbol != null){
                tira += "(Arbol -> " + arbol.toString() + " , " + frecuencia + ") \n";
            }
            else{
                tira+= "( "+elemento+ " , "+frecuencia+" ) \n";
            }
            if(siguiente!=null){
                tira+=siguiente.toString();  
            }
            return tira;
        }

     }
     
     private Celda primera;
     private Celda ultima;
     private Arbol arbolHuffman;
 
     /* Funcion: Metodo para insertar un elemento en la lista
    *Param: int elemento, el que se quiere insertar
    *Return: void
    */
     public void insertar(int elemento){
         if(primera == null){
             primera = ultima = new Celda(elemento);
         }
         else{
             primera.insertarElemento(elemento);
         }
     }
     
     /* Funcion: Convierte en String los datos de la lista
    *Param: ---
    *Return: String tira, con los datos.
    */
     public String toString(){
        String tira="";
        if(primera!=null){
           tira = primera.toString(); 
        }
        return tira;
     }
     
     /* Funcion: metodo para crear un arbol de Huffman
    *Param: ---
    *Return: void
    */
     public void crearArbolHuffman(){
        while(primera.siguiente != null){
             crearArbol();
        }
     }
     
     /* Funcion: Metodo utilizado para crear un arbol
    *Param: ---
    *Return: void
    */
     public void crearArbol(){
         Celda hijo1 = primera;
         Celda hijo2 = primera.siguiente;
         Arbol nuevo;
         Celda celdaDeArbol;
         int frecuencia = hijo1.frecuencia + hijo2.frecuencia;
         if(hijo1.arbol != null && hijo2.arbol != null){
             nuevo = new Arbol(hijo1.arbol, hijo2.arbol,frecuencia);
         }
         else{
             if(hijo1.arbol != null){
                 nuevo = new Arbol(hijo1.arbol,hijo2.elemento,frecuencia);
             }
             else{
                 if(hijo2.arbol != null){
                     nuevo = new Arbol(hijo1.elemento, hijo2.arbol, frecuencia);
                 }
                 else{
                     nuevo = new Arbol(hijo1.elemento, hijo2.elemento, frecuencia);
                 }
             }          
         }
         celdaDeArbol = new Celda(nuevo);
         arbolHuffman = nuevo;
         if(hijo2.siguiente != null){
             primera = hijo2.siguiente;
             primera.anterior = null;
             hijo1 = null;
             hijo2 = null;
             insertarArbol(celdaDeArbol);
         }
         else{
             primera = celdaDeArbol;
             ultima = celdaDeArbol;
         } 
     }
     
     /* Funcion: metodo para insertar un arbol
    *Param: Celda celda
    *Return: void
    */
     public void insertarArbol(Celda celda){
        primera.anterior = celda;
        celda.siguiente = primera;
        primera = celda;
        
        if(celda.frecuencia > celda.siguiente.frecuencia){
            celda.reacomodarCasilla();
        }
     }
     
     public int getFrecuencia(){
        int frecuencia = sacarFrecuencia(primera);
        return frecuencia;
     }
     
     /* Funcion: Saca la frecuencia de una celda
    *Param: Celda Celda
    *Return: int frecuencia
    */
     public int sacarFrecuencia(Celda celda){
         int frecuencia = celda.frecuencia;
         if(celda.siguiente != null){
             frecuencia += sacarFrecuencia(celda.siguiente);
         }
         return frecuencia;
     }
     
     /* Funcion: Metodo para buscar un elemento en un arbol
     *Param: int elemento, el que se quiere buscar
     *Return: String camino, por donde se paso en el arbol
     *hasta encontrar el elemento.
     */
     public String buscarEnArbol(int elemento){
         String camino = arbolHuffman.buscarElemento(elemento);
         return camino;
     }
     
     public Arbol getHuffman(){
         return arbolHuffman;
     }
     
     /* Funcion: Cuenta el largo de la lista
    *Param: String lista
    *Return: void
    */
     public void contarLargo(String lista){
         int largo = 0;
         for(int i = 0; i < lista.length(); i++){
             if(lista.charAt(i) == ')'){
                largo++;
             }
         }
     }
} 
 