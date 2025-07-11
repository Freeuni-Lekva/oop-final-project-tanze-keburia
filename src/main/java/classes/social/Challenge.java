package classes.social;

public final class Challenge {
    public String sender;
    public String receiver;
    public String quizID;
    public String quizName;
    public double score;

    public Challenge(String sender, String receiver,
                     String quizID, String quizName, double score) {
        this.sender = sender;
        this.receiver = receiver;
        this.quizID= quizID;
        this.quizName = quizName;
        this.score = score;
    }

    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }

    public String getQuizID() {
        return this.quizID;
    }
    public String getQuizName() {
        return this.quizName;
    }
    public double getScore() {
        return this.score;
    }
}
