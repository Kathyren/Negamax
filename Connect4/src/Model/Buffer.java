/*    
    Copyright (C) 2012 http://software-talk.org/ (developer@software-talk.org)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package connect4.Model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * thi class provides an buffer with one element space.
 */
public class Buffer<E> {
    
    private E content;
    
    /**
     * creates a new buffer with empty content.
     */
    public Buffer() {
        this.content = null;
    }
     
    /**
     * writes an element in the buffer.
     * WARNING: The old element will be overwrite!
     * Notify a waiting thread who wants to read.
     * @param elem the elem to put on the buffer
     */
    public synchronized void write(E elem){
        this.notify();
        this.content = elem;
    }
    
    /**
     * returns the content of this buffer.
     * If the buffer is empty the thread will be wait.
     * After reading the buffer is empty.
     * @return the content
     */
    public synchronized E read(){
        if(content == null) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        E temp = content;
        content = null;
        return temp;
    }
    
    /**
     * sets the content to null
     */
    public synchronized void clear(){
        this.content = null;
    }
    
    /**
     * notify all sleeping threads inside the buffer
     */
    public synchronized void wakeUp(){
        this.notifyAll();
    }
    
}
