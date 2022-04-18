
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivan
 */
public interface TaxiManager extends Remote {
    public boolean requestTaxi(String address) throws RemoteException;
    public void setTaxiStatus(int id, boolean isFree) throws RemoteException;
    public void Register(int id, TaxiCallback vozac) throws RemoteException;
}
