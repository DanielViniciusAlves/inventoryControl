package org.tppe.services;

import org.tppe.entities.*;
import org.tppe.exceptions.*;
import java.util.*;

public class BranchService
{
	private List<Branch> branchList;
	
	public BranchService() {
		
	}
	
	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public void addBranch(String branchName, Stock stock) throws BlankDescriptionException {
		if(branchName.length() == 0) {
			throw new BlankDescriptionException(branchName);
		}
		this.branchList.add(new Branch(branchName,stock));
	}
	public Branch findBranch(String branchName) 
	{
		for(Branch b :this.branchList) {
			if(b.getName() == branchName) {
				return b;
			}
		}
		return null;
	}
	
	
}
