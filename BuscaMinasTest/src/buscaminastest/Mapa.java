package buscaminastest;

public class Mapa {
    //private final int len;//
    private int banderas;//
    private final int[][] mapa;
    private final String[][] visible;

    public Mapa(int len) {
        //this.len = len;//
        this.banderas = (int)(len*len/4);//
        this.mapa = new int[len][len];
        this.visible = new String[len][len];
        
        inicializarMapas();
    }

    
    /*public int getLen() {
        return len;
    }*/
    
    
    public int getBanderas() {
        return banderas;
    }
    
    
    private void inicializarMapas() {
        for (int[] mapa1:mapa) {
            for (int j=0; j<mapa.length; j++) {
                mapa1[j] = -1;
            }
        }
        for(int i=0; i<mapa.length; i++) {
            for(int j=0; j<mapa.length; j++) {
                visible[i][j] = "O"; //alt+219 "\u2588" \u00DB \u0905
            }
        }
    }
    
    
    public void generarTablero(int actY, int actX) {
        int minas = banderas;//(int)(mapa.length*mapa.length/4);
        int x;
        int y;
        
        for (int i=0; i<minas; i++) {
            do {
                x = (int)(Math.random()*(mapa.length));
                y = (int)(Math.random()*(mapa.length));
            } while (mapa[y][x]==0 || enInicial(y, x, actY, actX));
            
            mapa[y][x] = 0;
            generarNum(y, x);
        }
    }
    
    private boolean enInicial(int y, int x, int actY, int actX) {
        return Math.abs(actY-y)<=1 && Math.abs(actX-x)<=1;
    }
    
    private boolean comprobarLimites(int i, int j) {
        return i>=0 && i<mapa.length && j>=0 && j<mapa.length;
    }
    
    private void generarNum(int y, int x) {
        for (int i=y-1; i<=y+1; i++) {
            for (int j=x-1; j<=x+1; j++) {
                if (comprobarLimites(i, j))
                    if (mapa[i][j]!=0)
                       mapa[i][j] = mapa[i][j]==-1 ? 1 : mapa[i][j]+1;
            }
        }
    }
    
    
    public boolean cavar(int actY, int actX) {
        if (!"O".equals(visible[actY][actX]))
            if (visible[actY][actX]!=null) return false;
            else if (mapa[actY][actX]==contarBanderasAd(actY, actX)) {
                    for (int i=actY-1; i<=actY+1; i++) {
                        for (int j=actX-1; j<=actX+1; j++) {
                            if (comprobarLimites(i, j))
                                if ("O".equals(visible[i][j])) {
                                    if (mapa[i][j]==0) return true;
                                    
                                    if (mapa[i][j]==-1) cavar(i, j);
                                    else visible[i][j] = null;
                                }
                        }
                    }
                } else return false;
        
        visible[actY][actX] = mapa[actY][actX]==-1 ? " " : null;
        
        if (mapa[actY][actX]==0) return true;
        
        if (contarMinasAd(actY, actX)==0) {
            for (int i=actY-1; i<=actY+1; i++) {
                for (int j=actX-1; j<=actX+1; j++) {
                    if (comprobarLimites(i, j))
                        cavar(i, j);
                }
            }
        }
        
        return false;
    }
    
    private int contarBanderasAd(int actY, int actX) {
        int banderasAd = 0;
        for (int i=actY-1; i<=actY+1; i++) {
            for (int j=actX-1; j<=actX+1; j++) {
                if (comprobarLimites(i, j))
                    if ("+".equals(visible[i][j])) banderasAd++;
            }
        }
        return banderasAd;
    }
    
    private int contarMinasAd(int actY, int actX) {
        int minasAd = 0;
        for (int i=actY-1; i<=actY+1; i++) {
            for (int j=actX-1; j<=actX+1; j++) {
                if (comprobarLimites(i, j))
                    if (mapa[i][j]==0) minasAd++;
            }
        }
        return minasAd;
    }
    
    
    public void abanderar(int actY, int actX) {
        if (!"O".equals(visible[actY][actX])) {
            if ("+".equals(visible[actY][actX])) {
                visible[actY][actX] = "O";
                banderas++;
            }
            return;
        }
        
        if (banderas>0) {
            visible[actY][actX] = "+";
            banderas--;
        }
    }
    
    
    public boolean ganar(){
        for (int i=0; i<mapa.length; i++) {
            for (int j=0; j<mapa.length; j++) {
                if (mapa[i][j]!=0 && "O".equals(visible[i][j]))
                    return false;
            }
        }
        return true;
    }
    
    
    public void revelarMapa(boolean ganar) {
        for (int i=0; i<mapa.length; i++) {
            for (int j=0; j<mapa.length; j++) {
                if (ganar) {
                    if (mapa[i][j]==0) visible[i][j] = "*";
                } else {
                    if ("+".equals(visible[i][j]) && mapa[i][j]!=0)
                        visible[i][j] = "-";
                    
                    if (mapa[i][j]==0 && !"+".equals(visible[i][j]))
                        visible[i][j] = "X";
                    else if ("O".equals(visible[i][j]))
                        cavar(i, j);
                }
            }
        }
        
        if (ganar) mostrarVisible("B)");
        else mostrarVisible(";(");
    }
    
    
    public void mostrarVisible(String cara) {
        System.out.println("Banderas restantes: "+banderas);
        System.out.print(cara+"|");
        for (int i=1; i<=mapa.length; i++) {
            System.out.printf("%02d|", i);
        }
        System.out.println();

        for (int i=0; i<mapa.length; i++) {
            System.out.printf("%02d|", i+1);
            for (int j=0; j<mapa.length; j++) {
                if (visible[i][j]==null) System.out.print(" "+mapa[i][j]);
                else System.out.print(" "+visible[i][j]);
                
                if (j<mapa.length-1) System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println();
    }
    
    
    private void mostrarTablero() { // debugging purposes
        System.out.print("8O|");
        for (int i=1; i<=mapa.length; i++) {
            System.out.printf("%02d|", i);
        }
        System.out.println();

        for (int i=0; i<mapa.length; i++) {
            System.out.printf("%02d|", i+1);
            for (int j=0; j<mapa.length; j++) {
                if (mapa[i][j]==-1) System.out.print(mapa[i][j]);
                else System.out.print(" "+mapa[i][j]);
                
                if (j<mapa.length-1) System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println();
    }
    
    
}
