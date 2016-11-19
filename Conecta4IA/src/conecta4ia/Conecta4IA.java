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
public class Conecta4IA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Boolean pensando;
    // TODO code application logic here

    /**
     *
     */
     int maximoInteger;
     ClassTablero ta= new ClassTablero(null);
     ta.insertaficha(1, -1);
     ta.insertaficha(1,-1);
     ta.muestra();
     ta.deshacer(1);
     ta.muestra();
     java.awt.EventQueue.invokeLater(new Runnable()
         {
                public void run()
                {

                    new Conecta4().setVisible(true);
                    

                }
         });
    
    
    }
    

    
}
