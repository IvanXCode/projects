
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivan
 */
public class TaxiManagerImpl extends UnicastRemoteObject implements TaxiManager{
    
    public List<Taxi> cars;
    public List<String> addresses;
    public List<TaxiCallback> drivers;
    public int pokTaxi;
    public int waitingList;
    
    public TaxiManagerImpl() throws RemoteException{
        this.pokTaxi = 0;
        this.waitingList = 5;
        this.cars = new ArrayList<Taxi>();
        this.addresses = new ArrayList<String>();
        this.drivers = new ArrayList<TaxiCallback>();
    }

    @Override
    public boolean requestTaxi(String address) throws RemoteException {
        for(int i = 0; i < this.cars.size(); i++){
            if(this.cars.get(this.pokTaxi + i).isFree){
                Taxi t = this.cars.get(this.pokTaxi + i);
                t.address = address;
                t.isFree = false;
                this.cars.set(this.pokTaxi + i, t);
                callback(this.pokTaxi + i, address);
                this.pokTaxi = (this.pokTaxi + 1) % this.cars.size();
                return true;
            }
            else if (waitingList != 0){
                this.addresses.add(address);
                this.waitingList--;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void Register(int id, TaxiCallback vozac) throws RemoteException {
        if(vozac != null){
            this.drivers.add(vozac);
            Taxi tx = new Taxi(id);
            this.cars.add(tx);
        }
    }

    @Override
    public void setTaxiStatus(int id, boolean isFree) throws RemoteException {
        Taxi tx;
        int index = 0;
        tx = this.cars.stream().filter(t -> t.id == id).findAny().orElse(null);
        if(tx != null)
            index = this.cars.indexOf(tx);
        if(isFree == true){
            if(this.addresses.size() != 0){
                tx.address = this.addresses.get(0);
                callback(index, this.addresses.get(0));
                this.addresses.remove(0);
                this.cars.set(index, tx);
            }
            else{
                tx.isFree = isFree;
                this.cars.set(index, tx);
            }
        }
        else{
            tx.isFree = isFree;
            this.cars.set(index, tx);
            System.out.println(this.addresses.get(0));
        }
    }
    
    public void callback(int poz, String adress){
        if(adress != null){
            try {
                this.drivers.get(poz).notifyTaxi(adress);
            } catch (RemoteException ex) {
                Logger.getLogger(TaxiManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
