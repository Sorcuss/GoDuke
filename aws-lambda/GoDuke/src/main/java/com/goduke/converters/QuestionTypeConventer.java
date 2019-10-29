package com.goduke.converters;
import com.goduke.model.Question;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;


public class QuestionTypeConventer implements DynamoDBTypeConverter<String, Question> {

	@Override
	public String convert(Question object) {
		String question = null;
		try {
			if(object != null) {
				question = String.format("%s | %s", object.getId(), object.getContent());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return question;
	}

	@Override
	public Question unconvert(String object) {
		Question question = new Question();
		try {
			if(object != null && object.length() != 0) {
				String[] questionFields = object.split("|");
				question.setId(Integer.parseInt(questionFields[0].trim()));
				question.setContent(questionFields[1].trim());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return question;
	}

}
