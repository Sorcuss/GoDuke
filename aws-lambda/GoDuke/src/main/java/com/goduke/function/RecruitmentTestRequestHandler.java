package com.goduke.function;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.amazonaws.services.lambda.runtime.Context;
import com.goduke.model.RecruitmentTest;
import com.goduke.services.RecruitmentTestService;
import com.google.gson.Gson;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


public class RecruitmentTestRequestHandler{
	private RecruitmentTestService testService = new RecruitmentTestService();
	
	public APIGatewayProxyResponseEvent handleCreateRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 if(request.getBody() == null || request.getBody().isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 else {
			 	RecruitmentTest testToCreate = new RecruitmentTest(request.getBody());
			 	testService.addRecrutmentTest(testToCreate);
		        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		  }
	    }
	 
	 public APIGatewayProxyResponseEvent handleGetRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 Map<String, String> parameters = request.getPathParameters();
		 if(parameters.get("id") == null || parameters.get("id").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 
		 else {
			 RecruitmentTest test = testService.getRecruitmentTest(parameters.get("id"));
			 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(test.toJson());
		 }
	 }
	 
	 public APIGatewayProxyResponseEvent handleGetAllRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		List<RecruitmentTest> allTests = this.testService.getAllRecruitmentsTests();
		Gson gson = new Gson();
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(allTests));
	 }
	 
	 public APIGatewayProxyResponseEvent handleDeleteRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 Map<String, String> parameters = request.getPathParameters();
		 if(parameters.get("id") == null || parameters.get("id").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 
		 else {
			 this.testService.deleteRecruitmentTest(parameters.get("id"));
			 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		 }
	 }
	 
	 public APIGatewayProxyResponseEvent handleUpdateRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 if(request.getBody() == null || request.getBody().isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 else {
			 	RecruitmentTest testToUpdate = new RecruitmentTest(request.getBody());
			 	this.testService.updateRecruitmentTest(testToUpdate);
		        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		  }
	 }
	 
	 public APIGatewayProxyResponseEvent handleAddLanguagesToTest(APIGatewayProxyRequestEvent request, Context context) {
		 Map<String, String> parameters = request.getPathParameters();
		 if(parameters.get("id") == null || parameters.get("id").isEmpty() || request.getBody() == null || request.getBody().isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 	Gson gson = new Gson();
		 	try {
		 		List<String> languages = new ArrayList<>();
		 		languages = Arrays.asList(gson.fromJson(request.getBody(), String[].class));
		 		this.testService.addLanguagesToTest(parameters.get("id"), languages);
		 		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		 	}catch(Exception e) {
				 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody(e.getMessage());

		 	}
	}
	 
	public APIGatewayProxyResponseEvent handleDeleteLanguageFromTest(APIGatewayProxyRequestEvent request, Context context) {
		Map<String, String> parameters = request.getPathParameters();
		if(parameters.get("id") == null || parameters.get("id").isEmpty() || parameters.get("languageId") == null || parameters.get("languageId").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		}
		
		try {
			if(this.testService.deleteLanguageFromTest(parameters.get("id"), parameters.get("languageId"))){
				 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
			}
			else {
				 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("test or language dosnt exist");

			}
		} catch (Exception e) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("test dosnt exist");
		}
	}
	
	public APIGatewayProxyResponseEvent handleAddCandidatesToTest(APIGatewayProxyRequestEvent request, Context context) {
		Map<String, String> parameters = request.getPathParameters();
		 if(parameters.get("id") == null || parameters.get("id").isEmpty() || request.getBody() == null || request.getBody().isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 Gson gson = new Gson();
		 try {
			 List<String> candidates = new ArrayList<String>();
			 candidates = Arrays.asList(gson.fromJson(request.getBody(), String[].class));
			 this.testService.addCandidatesToTest(parameters.get("id"), candidates);
		 	return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		 }catch(Exception e) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody(e.getMessage());
		 }
	}
	
	public APIGatewayProxyResponseEvent handleDeleteCandidateFromTest(APIGatewayProxyRequestEvent request, Context context) {
		Map<String, String> parameters = request.getPathParameters();
		if(parameters.get("id") == null || parameters.get("id").isEmpty() || parameters.get("candidateId") == null || parameters.get("candidateId").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		}
		
		try {
			if(this.testService.deleteCandidateFromTest(parameters.get("id"), parameters.get("candidateId"))){
				 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
			}
			else {
				 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("test or candidate dosnt exist");

			}
		} catch (Exception e) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("test dosnt exist");
		}
	}
}
