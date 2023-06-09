package vttp2023.batch3.ssf.frontcontroller.Model;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.validation.constraints.AssertTrue;

public class Captcha implements Serializable{
    
    private int answer;
    private int first;
    private int second;
    private int givenAnswer;
    private char operator;
    private String problem;

    public char getOperator() {
        return operator;
    }
    public void setOperator(char operator) {
        this.operator = operator;
    }

    Random r = new Random();
    
    public int getGivenAnswer() {
        return givenAnswer;
    }
    public void setGivenAnswer(int givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
    public int getAnswer() {
        return answer;
    }
    public int setAnswer(int answer){
        return this.answer = answer;
    }
    public int getFirst() {
        return first;
    }
    public void setFirst(int first) {
        this.first = first;
    }
    public int getSecond() {
        return second;
    }
    public void setSecond(int second) {
        this.second = second;
    }

    public String setProblem() {
        int result;
        char operator;
        switch (r.nextInt(1, 5)) {
            case 1:
                operator = '+';
                result = this.getFirst() + this.getSecond();
                break;
            case 2:
                operator = '-';
                result = this.getFirst() - this.getSecond();
                break;
            case 3:
                operator = '*';
                result = this.getFirst() * this.getSecond();
                break;
            case 4:
                operator = '/';
                result = this.getFirst() / this.getSecond();
                break;
            default:
                operator = '\0';
                result = 0;
        }

        this.setAnswer(result);
        this.setOperator(operator);
        return String.format("%d %c %d = ?", this.getFirst(), operator, this.getSecond());

    }

    public Captcha() {
        this.setFirst(r.nextInt(1, 50));
        this.setSecond(r.nextInt(1, 50));
        this.setProblem();
    }

    public boolean checkAnswer (int a, int b){

        if (a == b) {
            return true;
        }

        return false;
    }

    
    
}
