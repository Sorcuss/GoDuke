package com.goduke.function.test.services;

import com.goduke.exceptions.DataDuplicatedException;
import com.goduke.function.test.daos.RecruitmentTestDAO;
import com.goduke.model.Test;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentTestService {
	private RecruitmentTestDAO recruitmentTestDAO = new RecruitmentTestDAO();
	
	private List<String> addItemsToList(List<String> itemsToAdd, List<String> listToModify) throws Exception {
		if(listToModify == null) {
			listToModify = new ArrayList<String>();
		}
		for(String item : itemsToAdd) {
			if(listToModify.contains(item)) {
				throw new Exception();
			}
		}
		for(String item : itemsToAdd) {
			listToModify.add(item);
		}
		return listToModify;
	}
	
	public void addRecrutmentTest(Test test) {
		this.recruitmentTestDAO.saveItem(test);
	}
	
	public void deleteRecruitmentTest(Test test) {
		this.recruitmentTestDAO.deleteItem(test.getId());
	}
	
	public void deleteRecruitmentTest(String id) {
		this.recruitmentTestDAO.deleteItem(id);
	}
	
	public void updateRecruitmentTest(Test test) {
		this.recruitmentTestDAO.updateItem(test);
	}
	
	public List<Test> getAllRecruitmentsTests() {
		return this.recruitmentTestDAO.getAllItems();
	}
	
	public Test getRecruitmentTest(String id) {
		return this.recruitmentTestDAO.getItem(id);
	}
	
	public Test getRecruitmentTest(Test test) {
		return this.recruitmentTestDAO.getItem(test.getId());
	}
	
	public void addLanguagesToTest(String testsId, List<String> languagesToAdd) throws DataDuplicatedException, Exception{
		Test test = this.recruitmentTestDAO.getItem(testsId);
		if(test != null) {
		try {
			test.setLanguages(this.addItemsToList(languagesToAdd, test.getLanguages()));
		} catch (Exception e) {
			throw new DataDuplicatedException("language duplicated");
		}
			this.recruitmentTestDAO.updateItem(test);	
		}
		else {
			throw new Exception("test dont exist");
		}
	}
	
	public boolean deleteLanguageFromTest(String testId, String language) throws Exception {
		Test test = this.recruitmentTestDAO.getItem(testId);
		if(test == null) {
			throw new Exception("test dosnt exist");
		}
		boolean removeFlag = test.getLanguages().remove(language);
		this.recruitmentTestDAO.updateItem(test);
		return removeFlag;
	}
	
	public void addCandidatesToTest(String testId, List<String> candidatesIdsToAdd) throws DataDuplicatedException, Exception {
		Test test = this.recruitmentTestDAO.getItem(testId);
		if(test != null) {
			try {
				test.setCandidatesIds(this.addItemsToList(candidatesIdsToAdd, test.getCandidatesIds()));
			} catch (Exception e) {
				throw new DataDuplicatedException("candidate duplicated");
			}
			this.recruitmentTestDAO.updateItem(test);
		}
		else {
			throw new Exception("test dont exist");
		}
		
	}
	
	public boolean deleteCandidateFromTest(String testId, String candidate) throws Exception {
		Test test = this.recruitmentTestDAO.getItem(testId);
		if(test == null) {
			throw new Exception("test dosnt exist");
		}
		boolean removeFlag = test.getCandidatesIds().remove(candidate);
		this.recruitmentTestDAO.updateItem(test);
		return removeFlag;
	}
	
}
