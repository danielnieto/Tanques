package Tanques;

import java.util.ArrayList;
import org.niktin.entrada.Teclado;
import org.niktin.juego.Juego;
import org.niktin.recursos.Recurso;
import org.niktin.recursos.Vacio;
import org.niktin.utilidades.Utilidades;

/**
 *
 * @author danielnieto
 */

class Tanque{
    
    static Juego juego;
    static Recurso tanque;
    static Recurso fondo;
    static double velocidad=0;
    static double velocidadAtras=0;
    static double velocidadRotacion=0;
    static Enemigo enemigo1;
    static Recurso[] obstaculos = new Recurso[50];
    static ArrayList<Recurso> aEliminar = new ArrayList<Recurso>();
    static Vacio body = new Vacio(49,79);
    static Vacio canon = new Vacio(8,18);
    public  static double lento=0;
    private static Enemigo enemigo2;
    private static long tiempoActual;
    static Enemigo[] enemigos = new Enemigo[10];
    public static ArrayList<Explosion> ex = new ArrayList<Explosion>();
    private static long tiempoAnterior;
    public static int vida=100;
    
    public static void main(String ar[]){
        
        juego = new Juego(1024,768, 100);
        
        cargarRecursos();
        cargarEscenario();
        
        loopPrincipal();
    
    }

    private static void loopPrincipal() {
        while(true){
        
            escuchaTeclado();
            juego.asignarTitulo(""+juego.obtenerCuadrosPorSegundo());                                  
            
            for(int x=0;x<aEliminar.size();x++){
                Juego.removerRecurso(aEliminar.get(x));
                              
            }
            aEliminar.clear();
                           
            eliminaMuertos();
            juego.actualizar();
            
                ponerExplosiones();
             lanzarMisiles();
        
        }
    }

    private static void cargarRecursos() {
        tanque = new Recurso("imagenes/tank4.png");
        fondo = new Recurso("imagenes/dirt.jpg");
        
        enemigos[0] = new Enemigo();
        enemigos[1] = new Enemigo();
        enemigos[2] = new Enemigo();
        
        
        
        Juego.agregarRecurso(fondo, 0, 0);
        Juego.agregarRecurso(enemigos[0], 200, 200);
        Juego.agregarRecurso(enemigos[1], 800, 800);
        Juego.agregarRecurso(enemigos[2], 1500, 100);
        Juego.agregarRecurso(tanque, juego.obtenerCentro().x, juego.obtenerCentro().y);
        Juego.agregarRecurso(body, tanque.obtenerX()+1, tanque.obtenerY()+1);
        Juego.agregarRecurso(canon, tanque.obtenerX()+tanque.obtenerAnchura()/2 - 4, tanque.obtenerY()+81);
       
       tanque.agregarHijo(body);
       tanque.agregarHijo(canon);
       
  
        
    }

    private static void escuchaTeclado() {
        
        if(Teclado.teclaPresionada(Teclado.TECLA_ESPACIO)&&Teclado.obtenerIntervalo()>100){
             new Misil(tanque.obtenerAnguloDeRotacion(), tanque.obtenerCentro().x, tanque.obtenerCentro().y);
        }
        
        if(Teclado.teclaPresionada(Teclado.TECLA_IZQUIERDA)){
            if(velocidadRotacion<2){
                velocidadRotacion+=.02;
            }
            double velAnterior = velocidadRotacion;
            
            tanque.rotarAgregado(Math.toRadians(velocidadRotacion), tanque.obtenerCentro().x, tanque.obtenerCentro().y);
            
            if(body.colisionaConAlguno()||canon.colisionaConAlguno()){
                
            tanque.rotarAgregado(Math.toRadians(-velAnterior), tanque.obtenerCentro().x, tanque.obtenerCentro().y);
            }
        }
        
        
        if(Teclado.teclaPresionada(Teclado.TECLA_ARRIBA)){
                      
            if(velocidad<2){
                velocidad+=.02;
            }
            double velAnt= velocidad;
            tanque.trasladarLocal(0, velocidad);
            
            if((body.colisionaConAlguno())||(canon.colisionaConAlguno())){
                                         
                tanque.trasladarLocal(0, -velocidad);
                velocidad=0;
                
            }
            
        }else{
                        
            if(velocidad>0){
            
                velocidad-=0.08;
                
                    tanque.trasladarLocal(0, velocidad);
                    
                if((body.colisionaConAlguno())||(canon.colisionaConAlguno())){
                                                        
                    tanque.trasladarLocal(0, -velocidad);
                    velocidad=0;
                
            }
            }
            
        }
        
        
        if(Teclado.teclaPresionada(Teclado.TECLA_ABAJO)){
                      
            if(velocidadAtras<2){
                velocidadAtras+=.02;
            }
            double velAnt= velocidadAtras;
            tanque.trasladarLocal(0, -velocidadAtras);
            
            if((body.colisionaConAlguno())||(canon.colisionaConAlguno())){
               
                                   
                tanque.trasladarLocal(0, velocidadAtras);
                
                velocidadAtras = 0;
                
            }
            
        }else{
                        
            if(velocidadAtras>0){
            
                velocidadAtras-=0.08;
                
                    tanque.trasladarLocal(0, -velocidadAtras);
                    
                if((body.colisionaConAlguno())||(canon.colisionaConAlguno())){
                 
                                  
                    tanque.trasladarLocal(0, velocidadAtras);
                    velocidadAtras = 0;
                
            }
            }
            
        }
        
      
        if(Teclado.teclaPresionada(Teclado.TECLA_DERECHA)){
            if(velocidadRotacion<2){
                velocidadRotacion+=.02;
            }
            double velAnterior = velocidadRotacion;
            tanque.rotarAgregado(Math.toRadians(-velocidadRotacion), tanque.obtenerCentro().x, tanque.obtenerCentro().y);
            
             if(body.colisionaConAlguno()||canon.colisionaConAlguno()){
                
                tanque.rotarAgregado(Math.toRadians(velAnterior), tanque.obtenerCentro().x, tanque.obtenerCentro().y);
            }
        }
        
        if(!Teclado.teclaPresionada(Teclado.TECLA_IZQUIERDA)&&!Teclado.teclaPresionada(Teclado.TECLA_DERECHA)){
            if(velocidadRotacion>0.2){
            
                velocidadRotacion-=0.1;
                
              
            }
        }
        
        if(Teclado.teclaPresionada(Teclado.TECLA_ARRIBA)||Teclado.teclaPresionada(Teclado.TECLA_ABAJO)){
            if(lento<150){
            
                lento= lento+ 1;
                              
            }
        }else{
        
            if(lento>0){
                lento= lento -1;
            }
        }
        
        if(Juego.pantalla.p3.x - tanque.obtenerCentro().x<200){
        
            Juego.asignarPosicionVistaX(Juego.obtenerPosicionVistaX()-3);
        }else if(tanque.obtenerCentro().x<Juego.pantalla.p1.x +200){
        
            Juego.asignarPosicionVistaX(Juego.obtenerPosicionVistaX()+3);
        }
        
        if(Juego.pantalla.p3.y - tanque.obtenerCentro().y<200){
        
            Juego.asignarPosicionVistaY(Juego.obtenerPosicionVistaY()-3);
        }else if(tanque.obtenerCentro().y<Juego.pantalla.p1.y +200){
        
            Juego.asignarPosicionVistaY(Juego.obtenerPosicionVistaY()+3);
        }
        
        
        
    }

    private static void cargarEscenario() {
        int cantidad=0;
        while(cantidad<30){
            obstaculos[cantidad] = new Recurso("imagenes/obs.png");
            Juego.agregarRecurso(obstaculos[cantidad], Utilidades.aleatorio(1900, 0), Utilidades.aleatorio(1900, 0));
            
            if(obstaculos[cantidad].colisionaConAlguno()){
                Juego.removerRecurso(obstaculos[cantidad]);              
            }else{
                cantidad++;
            }
        }
    }

    private static void lanzarMisiles() {
        
            for(int x = 0; x<3;x++){
            tiempoActual = Utilidades.obtenerTiempo();
        
            if(tiempoActual-enemigos[x].tiempoAnterior>Utilidades.aleatorio(2000, 80000)){
            enemigos[x].tiempoAnterior = tiempoActual;
                        
                 if(enemigos[x].vida>0)      new Misil(enemigos[x].obtenerAnguloDeRotacion(),enemigos[x].obtenerCentro().x, enemigos[x].obtenerCentro().y);
                 
        
        }
        }
    }

    private static void eliminaMuertos() {
         for(int x = 0; x<3;x++){
             if(enemigos[x].vida<=0){
                 Juego.removerRecurso(enemigos[x]);
                 Juego.removerRecurso(enemigos[x].colision);
             }
        }
    }

    private static void ponerExplosiones() {
        for(int x=0; x<ex.size();x++){
            Juego.agregarRecurso(ex.get(x), ex.get(x).obtenerX(), ex.get(x).obtenerY());
            
        }
        ex.clear();
    }
    
    public static void agregarExplosion(Explosion exp){
    
            ex.add(exp);
           
    }
    
}
