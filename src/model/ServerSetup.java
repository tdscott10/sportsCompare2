package model;

public class ServerSetup {
	
	public static void main(String[] args) {
		DatabaseCreation db = new DatabaseCreation();
		db.createIndividualDatabase();
	}
	
}
