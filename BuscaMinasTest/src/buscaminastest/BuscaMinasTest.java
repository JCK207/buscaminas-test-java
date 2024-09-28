package buscaminastest;

import java.util.Scanner;

public class BuscaMinasTest {
    static Scanner sc;
    static Mapa mapa;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.println("TÍTULO"); // Por Jacker207
        System.out.println();
        
        boolean terminar = false;
        while (!terminar) {
            System.out.println("1. Jugar");
            //System.out.println("2. Mejores puntuaciones"); To Do
            //decir durante juego cuántas minas quedan
            System.out.println("2. Salir");
            
            int opcion = leerEnRango("Ingrese la opción: ", 1, 2);//
            //int opcion = 1;//
            System.out.println();
            
            switch (opcion) {
                
                case 1 -> {
                    System.out.println("1. Fácil");
                    System.out.println("2. Medio");
                    System.out.println("3. Difícil");
                    
                    int dificultad = leerEnRango("Ingrese la dificultad: ", 1, 3);//
                    //int dificultad = 3;//
                    System.out.println();
                    
                    int len;//= dificultad*5;//
                    //jugar(len);//
                    switch (dificultad) {//por si más diferencias entre dificultad
                        case 1 -> {
                            len = 5;
                            jugar(len);
                        }
                        case 2 -> {
                            len = 10;
                            jugar(len);
                        }
                        case 3 -> {
                            len = 15;
                            jugar(len);
                        }
                    }
                    
                }
                
                /*case 2 -> {
                    
                }*/
                
                case 2 -> {
                    terminar = true;
                }
            }
            
        }
    }

    
    public static int leerEnRango(String texto, int min, int max) {
        int dato;
        do {
            System.out.print(texto);
            //dato = (int)(Math.random()*max+min);//
            //System.out.println(dato);//
            dato = sc.nextInt();//
            if (dato<min || dato>max)
                System.out.println("Dato inválido");
        } while (dato<min || dato>max);
        return dato;
    }
    
    
    public static void jugar(int len) {
        mapa = new Mapa(len);
        mapa.mostrarVisible(":D");
        
        int X;
        int Y;
        boolean repetirX;
        boolean repetirY;
        boolean repetirAcc;
        System.out.println("Ingrese los datos en el orden que se especifique");
        System.out.println();
        System.out.println("Coordenada de inicio");
        do {
            System.out.println("X Y:");
            //X = leerEnRango("X: ",1, len)-1;//
            //Y = leerEnRango("Y: ",1, len)-1;//
            X = sc.nextInt()-1;//
            Y = sc.nextInt()-1;//
            repetirX = repetirInt("X", X+1, 1, len);//
            repetirY = repetirInt("Y", Y+1, 1, len);//
        } while (repetirX || repetirY);//
        //} while (repetirInts("XY", new int[]{X+1,Y+1}, 1, mapa.length));
                            
        mapa.generarTablero(Y, X);
        System.out.println();
        
        char acc;
        boolean accion;
        System.out.println("Teclas de acciones:");
        System.out.println("'-' para cavar");
        System.out.println("'+' para abanderar");
        System.out.println();
        
        mapa.cavar(Y, X);
        boolean acabado = false;
        while(!acabado) {
            mapa.mostrarVisible(":P");
            
            do {
                System.out.println("Acción X Y:");
                //acc = '-';//
                //accion = Math.random()<0.5;//
                //X = leerEnRango("X: ",1, len)-1;//
                //Y = leerEnRango("Y: ",1, len)-1;//
                acc = sc.next().charAt(0);//
                accion = acc=='-';//
                X = sc.nextInt()-1;//
                Y = sc.nextInt()-1;//
                repetirAcc = repetirChar("Acción", acc, "-+");
                repetirX = repetirInt("X", X+1, 1, len);//
                repetirY = repetirInt("Y", Y+1, 1, len);//
            } while (repetirAcc || repetirX || repetirY);//
            //} while (repetirChar("Acción", acc, "-+") || repetirInts("XY", new int[]{X+1,Y+1}, 1, mapa.length));

            System.out.println();
            
            if (accion) {
                if (mapa.cavar(Y, X)){
                    acabado = true;
                    System.out.println("nt"); //modificar para highscore
                    System.out.println();
                    mapa.revelarMapa(false);
                } else if (mapa.ganar()) {
                    acabado = true;
                    System.out.println("gg"); //modificar para highscore
                    System.out.println();
                    mapa.revelarMapa(true);
                }
            } else {
                mapa.abanderar(Y, X);
            }
       
        }
    }
    
    
    public static boolean repetirInt(String nom, int n, int min, int max) {
        if (n<min || n>max) {
            System.out.println("Dato inválido: "+nom+"="+n);
            //System.out.println(min+"<="+nom+"<="+max);
            return true;
        }
        return false;
    }

    
    /*public static boolean repetirInts(String noms, int[] n, int min, int max) {
        boolean distinto = false;
        
        for (int i=0; i<n.length; i++) {
            if (n[i]<min || n[i]>max) {
                System.out.println("Dato inválido: "+noms.charAt(i)+"="+n[i]);
                distinto = true;
            }
        }
        return distinto;
    }*/
    
    
    public static boolean repetirChar(String nom, char c, String pide) {
        for (int i=0; i<pide.length(); i++) {
            if (c==pide.charAt(i)) return false;
        }
        System.out.println("Dato inválido: "+nom+"="+c);
        return true;
    }
    
    
}
