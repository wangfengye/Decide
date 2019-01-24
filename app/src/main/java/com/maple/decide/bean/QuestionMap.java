package com.maple.decide.bean;

import com.maple.decide.bean.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maple on 2019/1/21 11:30.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class QuestionMap {
    private List<Question> questions = new ArrayList<>();
    private int lastUsed;

    public int getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(int lastUsed) {
        this.lastUsed = lastUsed;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        if (questions == null) questions = new ArrayList<>();
        questions.add(question);
    }

    public int size() {
        if (questions == null) return 0;
        else return questions.size();
    }

    public Question last() {
        if (lastUsed < size()) {
            return questions.get(lastUsed);
        } else {
            Question question = new Question();
            question.setTitle("创建一个模板");

            question.addOption("今天学什么");
            question.addOption("去哪玩");
            question.addOption("吃什么");
            question.addOption("假期去哪个景点");
            question.addOption("去哪个城市");
            question.addOption("考哪所高校");
            question.addOption("看哪部新出的电影");
            question.addOption("今天学多久");
            return question;
        }
    }
}
