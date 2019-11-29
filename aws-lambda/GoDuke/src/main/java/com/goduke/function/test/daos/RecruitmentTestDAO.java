package com.goduke.function.test.daos;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.goduke.model.Test;

import java.util.List;
import java.util.UUID;

public class RecruitmentTestDAO implements DAO<Test>{

	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;
	
	public RecruitmentTestDAO() {
		this.client =  AmazonDynamoDBClientBuilder.standard().build();
		this.mapper = new DynamoDBMapper(this.client);
	}
	
	@Override
	public Test getItem(String id) {
		return this.mapper.load(Test.class, id);
	}

	@Override
	public void saveItem(Test item) {
		if(item.getId() == null || item.getId().isEmpty()) {
			item.setId(UUID.randomUUID().toString());
		}
		
		this.mapper.save(item);
		
	}

	@Override
	public void deleteItem(String id) {
		Test testToDelete = this.getItem(id);
		if(testToDelete != null) {
			this.mapper.delete(testToDelete);
		}
		
	}

	@Override
	public List<Test> getAllItems() {
		return this.mapper.scan(Test.class, new DynamoDBScanExpression());
	}

	@Override
	public void updateItem(Test item) {
		this.deleteItem(item.getId());
		this.saveItem(item);
	}

}
