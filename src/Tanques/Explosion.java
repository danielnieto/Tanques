package Tanques;

import org.niktin.juego.Juego;
import org.niktin.recursos.Recurso;

/**
 *
 * @author danielnieto
 */
public class Explosion extends Recurso{
    
    public Explosion(double x, double y){
        
        super("imagenes/explosion17.png",5,5);
        
        super.asignarX(x);
        super.asignarY(y);
        
        
    }
    
    
    
    @Override
    public void ejecutar(){
    
        this.fotogramaSiguiente(10);
        
        if(this.obtenerFotogramaActual()==24){
        
            Tanque.aEliminar.add(this);
        }
        
    }
    
}
