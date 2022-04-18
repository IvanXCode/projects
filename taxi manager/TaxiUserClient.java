
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
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
public class TaxiUserClient {
    private TaxiManager TMngr;
    private TaxiCallback cc;
    
    public TaxiUserClient(){
        TMngr = null;
        cc = null;
        Scanner s;
        s = new Scanner(System.in);
        
        try {
		
            TMngr = (TaxiManager)Naming.lookup("rmi://localhost:1099/TaxiDS");

            System.out.println("Found server");	

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
            try {
                String ad;
                System.out.println("Please enter an address:");
                ad = s.nextLine();
                
                if(TMngr.requestTaxi(ad)){
                    System.out.println("The taxi will pick you up as soon as possible.");
                }
                else
                   System.out.println("We don't have available drivers at the moment."); 
            } catch (RemoteException ex) {
                Logger.getLogger(TaxiUserClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String args[]) {
 
	new TaxiUserClient();
	
	try {  			
		System.in.read();      			 		
	} catch (IOException ioException) {     
	
	}      
	
	System.exit(0);
	
 }
}
