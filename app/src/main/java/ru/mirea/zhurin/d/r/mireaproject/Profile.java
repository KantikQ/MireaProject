package ru.mirea.zhurin.d.r.mireaproject;

public class Profile {
    private float weight;
    private float headHeight;
    private int age;

    public Profile(float weight, float headHeight, int age) {
        this.weight = weight;
        this.headHeight = headHeight;
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeadHeight() {
        return headHeight;
    }

    public void setHeadHeight(float headHeight) {
        this.headHeight = headHeight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
