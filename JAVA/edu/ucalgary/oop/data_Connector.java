package edu.ucalgary.oop;

import java.sql.*;
// import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public class data_Connector {
	private Connection dbConnect;
    private ResultSet results;
	private Animal[] animalList = new Animal[15];
	private Task[] taskList = new Task[10];
	private Treatment[] treatmentList = new Treatment[30];
    private int[][] hourList= new int [24][10];
    private HashMap<Integer, TreeSet<Priority1>> hoursMap = new HashMap<Integer, TreeSet<Priority1>>();

	
	public data_Connector(){
	}
    public Animal[] getAnimalList(){
        return this.animalList;
    }
    public Task[] getTaskList(){
        return this.taskList;
    }

public Treatment[] getTreatmentList(){
    return this.treatmentList;
}
	
    public void createConnection(){
                
        try{
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "0953326601");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    public void selectAnimals(){
	
        try {   
		
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM animals");
            int i = 0;
			
            while (results.next()){
				
				this.animalList[i]= new Animal(results.getInt("AnimalID"),results.getString("AnimalNickname"),results.getString("AnimalSpecies"));
				i++;
          
            }
            
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }  

	 public void selectTasks(){

        try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM tasks");
            
			int i=0;
            while (results.next()){
                
                this.taskList[i]= new Task(results.getInt("TaskID"),results.getString("Description"),results.getInt("Duration"),
				results.getInt("MaxWindow"));
				i++;		
                
            }
            
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    public void selectTreatments(){
  
        try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM treatments");
			int i=0;
			
            while (results.next()){
                
				this.treatmentList[i]= new Treatment(results.getInt("AnimalID"),results.getInt("TaskID"),results.getInt("StartHour"));
               
                
                

                i++;
      
            }
            
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    public void setPriority(){

        for(int i = 0 ; i<10;i++){

            for(int j = 0; j<30 ;j++){
          
                
                
                if (this.taskList[i].getTaskID()==this.treatmentList[j].getTaskID() ){

                    
                    Priority1 myPriority = new Priority1(this.taskList[i].getTaskID(),this.treatmentList[j].getAnimalID(),this.taskList[i].getDuration(),taskList[i].getMaxWindow(),this.taskList[i].getDescription(),this.treatmentList[j].getStartHour());

                     if(this.hoursMap.containsKey(treatmentList[j].getStartHour())){
                        TreeSet<Priority1> set = this.hoursMap.get(treatmentList[j].getStartHour());
                        set.add(myPriority);
                        this.hoursMap.put(treatmentList[j].getStartHour(),set); 
                    }
                     else{
                        TreeSet<Priority1> set1 = new TreeSet<>();
                        set1.add(myPriority);
                        this.hoursMap.put(treatmentList[j].getStartHour(),set1);
                   }
                   /*
                    if (this.reservationOfLicenceWithDate.containsKey(licence)) {
        TreeSet<LocalDate> set = this.reservationOfLicenceWithDate.get(licence);
        set.add(date);
        set.add(date.plusDays(1));
        set.add(date.plusDays(2));
        this.reservationOfLicenceWithDate.put(licence, set);
        TreeSet<LocalDate> myset = this.parkingRecord.get(licence);
        TreeSet<LocalDate>mySet2 = this.reservation.get(licence);
        myset.add(date);
        mySet2.add(date);
        TreeSet<LocalDate> reversedSet = (TreeSet<LocalDate>) myset.descendingSet();
        this.parkingRecord.put(licence,reversedSet);
        this.reservation.put(licence, mySet2);


    }
     else {
        TreeSet<LocalDate> set = new TreeSet<>();
        set.add(date);
           set.add(date.plusDays(1));
        set.add(date.plusDays(2));
        this.reservationOfLicenceWithDate.put(licence, set);
        TreeSet<LocalDate> mySet = new TreeSet<>();
        mySet.add(date);
        this.parkingRecord.put(licence,mySet);
        this.reservation.put(licence, mySet);
        if(this.count == 1){
        this.firstDate = date;
        this.count=0;
     }

                    */
                   
                    
                }
            }
        }
       

      

        }

        public HashMap<Integer,TreeSet<Priority1>> getHashmap (){
            return this.hoursMap;
        }


        public static void  main(String [] args){
        data_Connector myJDBC = new data_Connector();


 
        myJDBC.createConnection();
        
        myJDBC.selectAnimals();
        
        myJDBC.selectTasks();
        myJDBC.selectTreatments();
        myJDBC.setPriority();
        HashMap<Integer, TreeSet<Priority1>> hoursMap = myJDBC.getHashmap();
        for (Entry<Integer, TreeSet<Priority1>> entry : hoursMap.entrySet()) {
            Integer key = entry.getKey();
            TreeSet<Priority1> value = entry.getValue();
            for (Priority1 item : value) {
                System.out.println(key + " " + item.getTaskID());
                }
            }
                
        }   
        

        




        }
        

        

    
