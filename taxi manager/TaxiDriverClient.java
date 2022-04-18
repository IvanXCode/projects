
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
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
public class TaxiDriverClient {
    private TaxiManager TMngr;
    private TaxiCallback cc;
    
    public TaxiDriverClient(){
        TMngr = null;
        cc = null;
        Scanner s;
        s = new Scanner(System.in);
        int id = 0;
        
        try {
		
            TMngr = (TaxiManager)Naming.lookup("rmi://localhost:1099/TaxiDS");

            System.out.println("Found server");
            
            System.out.println("What's the car's Id:");
            id = s.nextInt();

            cc=new TaxiCallbackImpl();

            TMngr.Register(id, cc);		

            System.out.println("callback registered");

        } 
        catch(RemoteException e)
        {
            System.out.println("Game: " + e.getMessage());
        } 
        catch(Exception e) 
        {
            System.out.println("Lookup: " + e.getMessage());
        }
        
        while(true){
            String dec;
            System.out.println("Give us a call after you finish the ride!");
            dec = s.nextLine();
            if(dec.equals("Yes") || dec.equals("yes")){
                try {
                    TMngr.setTaxiStatus(id, true);
                } catch (RemoteException ex) {
                    Logger.getLogger(TaxiDriverClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public class TaxiCallbackImpl extends UnicastRemoteObject implements TaxiCallback {

        public TaxiCallbackImpl() throws RemoteException{
        
        }
        
        @Override
        public void notifyTaxi(String address) throws RemoteException {
            System.out.println("The address is: " + address);
        }
        
    }
    
    public static void main(String args[]) {
 
	new TaxiDriverClient();
	
	try {  			
		System.in.read();      			 		
	} catch (IOException ioException) {     
	
	}      
	
	System.exit(0);
	
 }
}
