package com.sun.data;

import com.sun.utils.DataTable;

/**
 * User Login Details 
 *
 */
public class IllinoisDetails {
	public String State;
	public String ProtectionSol;	
	public String Age;	
	public String ProtectionAmount;
	public String Gender;	
	public String TobaccoUse;
	public String PreferredNonTobacco20;
	public String PreferredNonTobacco30;
	public String StandardNonTobacco20;
	public String StandardNonTobacco30;
	public String StandardTobacco20;
	public String StandardTobacco30;
	
	
	
	public IllinoisDetails(String State,String ProtectionSol,String Age,String ProtectionAmount,String Gender,String TobaccoUse,String PreferredNonTobacco20,String PreferredNonTobacco30,String StandardNonTobacco20,String StandardNonTobacco30,String StandardTobacco20,String StandardTobacco30){
		this.State = State;
		this.ProtectionSol = ProtectionSol;
		this.Age = Age;
		this.ProtectionAmount = ProtectionAmount;
		this.Gender = Gender;
		this.TobaccoUse = TobaccoUse;
		this.PreferredNonTobacco20 = PreferredNonTobacco20;
		this.PreferredNonTobacco30 = PreferredNonTobacco30;
		this.StandardNonTobacco20 = StandardNonTobacco20;
		this.StandardNonTobacco30 = StandardNonTobacco30;
		this.StandardTobacco20 = StandardTobacco20;
		this.StandardTobacco30 = StandardTobacco30;
		
	}
	
	public static IllinoisDetails loadFromDatatable(DataTable dataTable){
		return new IllinoisDetails(dataTable.getValue("State"),
								dataTable.getValue("ProtectionSol"),
								dataTable.getValue("Age"),
								dataTable.getValue("ProtectionAmount"),
								dataTable.getValue("Gender"),
								dataTable.getValue("TobaccoUse"),
								dataTable.getValue("PreferredNonTobacco20"),
								dataTable.getValue("PreferredNonTobacco30"),
								dataTable.getValue("StandardNonTobacco20"),
								dataTable.getValue("StandardNonTobacco30"),
								dataTable.getValue("StandardTobacco20"),
								dataTable.getValue("StandardTobacco30"));
	}	
}

