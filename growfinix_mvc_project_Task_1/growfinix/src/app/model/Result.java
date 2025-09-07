package app.model;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> marks = new HashMap<>(); // subject -> mark
    private int total;
    private double percentage;
    private String grade;

    public Result() {}

    public Result(Map<String, Integer> marks) {
        setMarks(marks);
    }

    public Map<String, Integer> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, Integer> marks) {
        this.marks = new HashMap<>(marks);
        calculate();
    }

    public int getTotal() {
        return total;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getGrade() {
        return grade;
    }

    public void calculate() {
        total = 0;
        for (int m : marks.values()) total += m;
        int subjects = marks.size() == 0 ? 1 : marks.size();
        percentage = (double) total / (subjects); // assuming each subject out of 100
        grade = computeGrade(percentage);
    }

    private String computeGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B+";
        if (percentage >= 60) return "B";
        if (percentage >= 50) return "C";
        if (percentage >= 40) return "D";
        return "F";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Marks: ").append(marks).append(", Total=").append(total)
          .append(", Percentage=").append(String.format("%.2f", percentage))
          .append(", Grade=").append(grade);
        return sb.toString();
    }
}
