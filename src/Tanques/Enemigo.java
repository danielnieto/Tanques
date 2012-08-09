/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tanques;

import org.niktin.juego.Juego;
import org.niktin.recursos.Recurso;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;

/**
 *
 * @author danielnieto
 */
public class Enemigo extends Recurso{
    double anguloARotar;
    double anguloAnterior;
    double angle;
    double anguloTotal;
    double lento=50;
    private int reversa;
    Vacio colision = new Vacio(80,110);
    public long tiempoAnterior, tiempoActual;
    public int vida = 100;
    
    public Enemigo(){
    
        super("imagenes/tankEnemy.png");
         
        Juego.agregarRecurso(colision, this.obtenerCentro().x-colision.obtenerAnchura()/2, this.obtenerCentro().y-colision.obtenerAltura()/2);
        this.agregarHijo(colision);
        tiempoAnterior = Utilidades.obtenerTiempo();
    }
    
    @Override
    public void ejecutar(){
        
        if(vida<=0){
            this.removerHijo(colision);
        }
                
        
        if((Math.abs(obtenerCentro().x-Tanque.tanque.obtenerCentro().x)<400)&&(Math.abs(obtenerCentro().y-Tanque.tanque.obtenerCentro().y)<400)){
              disparar();
          
        }else{
            
            disparar();
            trasladarLocal(0,1);
            
            if(colision.colisionaConAlguno()){
            trasladarLocal(0,-1);
            }
                                             
        }
        
    }
    
    public void disparar(){
        
      
        System.out.println(vida);
        
                
        angle=Math.atan2((Tanque.tanque.obtenerCentro().x-obtenerCentro().x),(Tanque.tanque.obtenerCentro().y-obtenerCentro().y));       
               
        angle=Math.toDegrees(angle);
        
                              
        this.rotar(Math.toRadians(-angle), obtenerCentro().x, obtenerCentro().y);
             
        
    
    }
    
    
    public void disparar2(){
        
        anguloAnterior = angle;
        
        angle=Math.atan2((Tanque.tanque.obtenerCentro().x-obtenerCentro().x),(Tanque.tanque.obtenerCentro().y-obtenerCentro().y));       
               
        angle=Math.toDegrees(angle);
        
        
        anguloARotar = anguloAnterior - angle;
        
        System.out.println(anguloARotar);
       
        this.rotar(Math.toRadians(-angle), obtenerCentro().x, obtenerCentro().y);
       
        if(this.colisionaConAlguno()){
            this.rotarAgregado(Math.toRadians(angle), obtenerCentro().x, obtenerCentro().y);
        }
    
    
    }
}
