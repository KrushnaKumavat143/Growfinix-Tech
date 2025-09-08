package app.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final AtomicInteger idCounter = new AtomicInteger(1000);

    private int id;
    private String name;
    private int age;
    private Result result;

    public Student() {
        this.id = idCounter.getAndIncrement();
    }

    public Student(String name, int age, Result result) {
        this.id = idCounter.getAndIncrement();
        this.name = name;
        this.age = age;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', age=%d, %s}", id, name, age,
                result == null ? "No Result" : result.toString());
    }
}
