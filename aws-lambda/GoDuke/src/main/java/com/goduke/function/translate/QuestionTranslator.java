package com.goduke.function.translate;

import com.goduke.model.test.Question;
import com.goduke.model.test.Test;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionTranslator {

    private static final String API_KEY = "trnsl.1.1.20200111T204904Z.43d4edbf00b63058.1d4e69badd5ceb033257efa91c3c09fbae92e1a5";
    private String primaryLanguage;
    private String secondLanguage;
    private Test test;

    public QuestionTranslator(Test test){
        this.test = test;
        List<String> languages = test.getLanguages();
        this.primaryLanguage = languages.get(0);
        this.secondLanguage = languages.get(1);
    }

    public void translate(){
        List<Question> questions = new ArrayList<>();
        for(Question question : test.getQuestions()){
            Question translatedQuestion = new Question();
            translatedQuestion.setLanguage(secondLanguage);
            translatedQuestion.setType(question.getType());
            translatedQuestion.setQuestion(getTranslatedText(question.getQuestion()));
            if(question.getOptions() != null){
                List<String> translatedOptions = new ArrayList<>();
                for(String option : question.getOptions())
                    translatedOptions.add(getTranslatedText(option));
                translatedQuestion.setOptions(translatedOptions);
            }
            questions.add(translatedQuestion);
        }
        test.getQuestions().addAll(questions);
    }

    public String getTranslatedText(String text){
        Gson gson = new Gson();
        TranslateModel translateModel = gson.fromJson(jsonPostRequest(buildQueryString(text)), TranslateModel.class);
        return translateModel.getText().get(0);
    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    public  String jsonPostRequest(String urlQueryString) {
        String json = null;
        try {
            InputStream inStream = createConnection(encodeSpace(urlQueryString)).getInputStream();
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public HttpURLConnection createConnection(String urlQueryString) throws IOException {
        URL url = new URL(urlQueryString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        connection.connect();
        return connection;

    }

    public String encodeSpace(String urlQueryString){
        return urlQueryString.replace(" ", "%20");
    }

    public String buildQueryString(String text){
        return "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                + API_KEY +
                 "&lang=" +
                primaryLanguage + "-" + secondLanguage +
                "&text=" + text;
    }

}
