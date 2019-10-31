package com.goduke.daos;

import java.util.List;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.goduke.model.RecruitmentTest;

public class RecruitmentTestDAO implements DAO<RecruitmentTest>{

	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;
	
	public RecruitmentTestDAO() {
		this.client =  AmazonDynamoDBClientBuilder.standard().build();
		this.mapper = new DynamoDBMapper(this.client);
	}
	
	@Override
	public RecruitmentTest getItem(String id) {
		return this.mapper.load(RecruitmentTest.class, id);
	}

	@Override
	public void saveItem(RecruitmentTest item) {
		if(item.getId() == null) {
			item.setId(UUID.randomUUID().toString());
		}
		
		this.mapper.save(item);
		
	}

	@Override
	public void deleteItem(String id) {
		RecruitmentTest testToDelete = this.getItem(id);
		if(testToDelete != null) {
			this.mapper.delete(testToDelete);
		}
		
	}

	@Override
	public List<RecruitmentTest> getAllItems() {
		return this.mapper.scan(RecruitmentTest.class, new DynamoDBScanExpression());
	}

}
