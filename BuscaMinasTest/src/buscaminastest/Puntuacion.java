package buscaminastest;

public class Puntuacion {
    private final int dificultad;
    private final int tiempo;
    private final int banderas;
    
    public Puntuacion(int dificultad, int tiempo, int banderas) {
        this.dificultad = dificultad;
        this.tiempo = tiempo;
        this.banderas = banderas;
    }
    
    public String getDificultad() {
        String dif = null;
        switch (dificultad) {
            case 0 -> dif = "F�cil";
            case 1 -> dif = "Medio";
            case 2 -> dif = "Dif�cil";
        }
        return dif;
    }
    
    public int getTiempo() {
        return tiempo;
    }
    
    public int getBanderas() {
        return banderas;
    }
    
    
}
