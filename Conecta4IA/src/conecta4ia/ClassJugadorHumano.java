/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta4ia;

/**
 *
 * @author Karen
 */
import javax.swing.JLabel;


/**
 *
 * @author topitzin
 */
public class ClassJugadorHumano extends ClassJugador{
      @Override
    public ClassTablero juega(ClassTablero tablero, boolean ProducidoClick, int col, JLabel TextLabelmensajes, JLabel area){
        boolean ok = false;
        String[][] colorJugador = new String[2][2];
        
        colorJugador[0][0] = "Amarillo";
        colorJugador[0][1] = "-1";
        colorJugador[1][0] = "Naranja";
        colorJugador[1][1] = "1";
        
        while(!ok){
            if (ProducidoClick ){
                area.setText("Jugador: "+colorJugador[super.numero]+ "Columna: "+ col);
                ok = tablero.insertaficha(col-1,super.numero);
                if(!ok){
                    //Print "Moviemiento ILEGAL"
                    TextLabelmensajes.setText("Movimiento Ilegl");
                    ProducidoClick = false;
                }
                else{
                    ProducidoClick = false;
                    break;
                }
                ProducidoClick = false;
            }
            else{
                try {
                    Thread.sleep(5);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        return tablero;
    }
}
