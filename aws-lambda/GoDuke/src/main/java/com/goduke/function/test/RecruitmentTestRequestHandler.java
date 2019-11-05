package com.goduke.function.test;

import java.util.Map;
import java.util.List;
import com.amazonaws.services.lambda.runtime.Context;
import com.goduke.function.test.daos.RecruitmentTestDAO;
import com.goduke.model.Test;
import com.google.gson.Gson;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


public class RecruitmentTestRequestHandler{
	private RecruitmentTestDAO recruitmentTestDAO = new RecruitmentTestDAO();
	
	 public APIGatewayProxyResponseEvent handleCreateRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 if(request.getBody() == null || request.getBody().isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 else {
			 	Test testToCreate = new Test(request.getBody());
			 	recruitmentTestDAO.saveItem(testToCreate);
		        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		  }
	    }
	 
	 public APIGatewayProxyResponseEvent handleGetRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 Map<String, String> parameters = request.getPathParameters();
		 if(parameters.get("id") == null || parameters.get("id").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 
		 else {
			 Test test = recruitmentTestDAO.getItem(parameters.get("id"));
			 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(test.toJson());
		 }
	 }
	 
	 public APIGatewayProxyResponseEvent handleGetAllRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		List<Test> allTests = this.recruitmentTestDAO.getAllItems();
		Gson gson = new Gson();
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(gson.toJson(allTests));
	 }
	 
	 public APIGatewayProxyResponseEvent handleDeleteRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 Map<String, String> parameters = request.getPathParameters();
		 if(parameters.get("id") == null || parameters.get("id").isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 
		 else {
			 this.recruitmentTestDAO.deleteItem(parameters.get("id"));
			 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		 }
	 }
	 
	 public APIGatewayProxyResponseEvent handleUpdateRecruitmentTest(APIGatewayProxyRequestEvent request, Context context) {
		 if(request.getBody() == null || request.getBody().isEmpty()) {
			 return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("error");
		 }
		 else {
			 	Test testToUpdate = new Test(request.getBody());
			 	recruitmentTestDAO.updateItem(testToUpdate);
		        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("success");
		  }
	 }
}
