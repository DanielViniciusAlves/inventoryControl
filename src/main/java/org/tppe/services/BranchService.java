package org.tppe.services;

import org.tppe.entities.*;
import org.tppe.exceptions.*;
import java.util.*;

public class BranchService
{
	private List<Branch> branchList;
	
	public BranchService() {
		this.branchList = new ArrayList<>();
	}
	
	public List<Branch> getBranchList() {
		return branchList;
	}

	public Branch addBranch(String branchName, StockServices stockServices) throws BlankDescriptionException {
		if(branchName.length() == 0) {
			throw new BlankDescriptionException(branchName);
		}
		Branch newBranch = new Branch(branchName,stockServices,stockServices.getStock());
		this.branchList.add(newBranch);
		return newBranch;
	}
	public Branch findBranch(String branchName) 
	{
		for(Branch b : this.branchList) {
			if(b.getName().equals(branchName)) {
				return b;
			}
		}
		return null;
	}
	
	
}
