package Tanques;

import org.niktin.juego.Juego;
import org.niktin.recursos.Recurso;

/**
 *
 * @author danielnieto
 */
public class Misil extends Recurso{
    
     
    public double posx;
    public double posy;
    
    double velocidad=2.5;
     
       
    public Misil(double anguloDeRotacion,double x, double y){
        
        super("imagenes/bola.png");
        Juego.agregarRecurso(this, x-(super.anchuraOriginal/2), y-(super.alturaOriginal/2));
        rotar(anguloDeRotacion); 
        trasladarLocal(0,80);
        
    }
    
    @Override
    public void ejecutar(){
        
            this.trasladarLocal(0,velocidad);
                 
           
            if(colisionaConAlguno()){
                 Tanque.agregarExplosion(new Explosion(obtenerCentro().x-32, obtenerCentro().y-32));
                for(int x=0; x<3;x++){
                    if(colisionaCon(Tanque.enemigos[x].colision)){
                        
                        Tanque.enemigos[x].vida-=10;
                        
                        
                    }
                }
                
                if(colisionaCon(Tanque.tanque)){
                    Tanque.vida -=10;
                    
                 
                }
                
                
                Tanque.aEliminar.add(this);
                                 
            }
            
          
    }
    
}
