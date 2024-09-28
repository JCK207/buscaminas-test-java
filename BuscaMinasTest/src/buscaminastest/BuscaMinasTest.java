package buscaminastest;

import java.util.Scanner;
import java.time.LocalTime;

public class BuscaMinasTest {
    static Scanner sc;
    static Puntuacion[] highscore = new Puntuacion[3];
    //static Mapa mapa;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.println("TÍTULO"); // Por Jacker207
        System.out.println();
        
        boolean terminar = false;
        while (!terminar) {
            int opcion = mostrarOpciones("la opción", new String[] {"Jugar", "HighScore", "Salir"});
            System.out.println();
            
            switch (opcion) {
                
                case 1 -> {
                    //String[] dificultades = new String[]{"Fácil", "Medio", "Difícil"};
                    //int dificultad = mostrarOpciones("la dificultad", dificultades);
                    int dificultad = mostrarOpciones("la dificultad", new String[]{"Fácil", "Medio", "Difícil"});
                    System.out.println();
                    
                    //int len;//
                    jugar(dificultad*5);//
                    /*switch (dificultad) {//por si más diferencias entre dificultad
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
                    }*/
                    
                }
                
                case 2 -> {
                    for (int i=0; i<highscore.length; i++) {
                        if (highscore[i]!=null) {
                            System.out.println("Dificultad: "+highscore[i].getDificultad());
                            System.out.println("Tiempo: "+highscore[i].getTiempo()+" segundos");
                            System.out.println("Banderas restantes: "+highscore[i].getBanderas());
                            System.out.println();
                        } else System.out.println("No se ha ganado en: "+nombrarOpcion(i, new String[]{"Fácil","Medio","Difícil"}));
                    }
                    System.out.println();
                }
                
                case 3 -> {
                    terminar = true;
                }
            }
            
        }
    }

    
    public static int mostrarOpciones(String nom, String[] opciones) {
        for (int i=0; i<opciones.length; i++) {
            System.out.println(i+1+". "+opciones[i]);
        }
        
        int eleccion = leerEnRango("Ingrese "+nom+": ", 1, opciones.length);
        System.out.println();
        System.out.println(nombrarOpcion(eleccion-1, opciones));
        return eleccion;
    }
    
    public static String nombrarOpcion(int eleccion, String[] opciones) {
        return opciones[eleccion];
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
        LocalTime inicio = LocalTime.now();
        
        Mapa mapa;//
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
                if (mapa.cavar(Y, X)){ // encuentra mina
                    acabado = true;
                    System.out.println("nt");
                    System.out.println();
                    mapa.revelarMapa(false);
                } else if (mapa.ganar()) {
                    acabado = true;
                    System.out.println("gg");
                    agregarPuntuacion(len/5-1, inicio, mapa.getBanderas());
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
    
    
    public static void agregarPuntuacion(int dificultad, LocalTime inicio, int banderas) {
        LocalTime fin = LocalTime.now();
        LocalTime total = fin.minusMinutes(inicio.getMinute()).minusSeconds(inicio.getSecond());
        
        int segundos = total.getMinute()*60+total.getSecond();
        
        if (highscore[dificultad]!=null)
            if (segundos<=highscore[dificultad].getTiempo()) {
                if (segundos == highscore[dificultad].getTiempo() && banderas<=highscore[dificultad].getBanderas())
                    return;
                highscore[dificultad] = new Puntuacion(dificultad, segundos, banderas);
            } else return;
        
        highscore[dificultad] = new Puntuacion(dificultad, segundos, banderas);
    }
    
    
}
