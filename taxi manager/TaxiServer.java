
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivan
 */
public class TaxiServer {
    public static void main(String args[]){
        try 
        { 
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");			

        } catch (RemoteException e) {            
            System.out.println("java RMI registry already exists.");
        }
        try {
 
	//WhiteboardManager WMngr = new WhiteboardManagerImpl();
        TaxiManager TMngr = new TaxiManagerImpl();
	
	Naming.rebind("rmi://localhost:1099/TaxiDS",TMngr); 

	System.out.println("Taxi server ready");
        }
        catch(Exception e) {
               System.out.println("Taxi server main " + e.getMessage());
        }
    }
}
