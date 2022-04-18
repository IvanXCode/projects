
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivan
 */
public class Taxi implements Serializable {
    public int id;
    public String address;
    public boolean isFree;
    
    public Taxi(int _id){
        this.id = _id;
        this.address = null;
        this.isFree = true;
    }
}
