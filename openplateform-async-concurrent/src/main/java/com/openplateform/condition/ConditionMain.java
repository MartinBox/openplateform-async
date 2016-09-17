package com.openplateform.condition;

/**
 * Created by root on 16-9-17.
 */
public class ConditionMain {
    public static void main(String[] args) throws InterruptedException {
        ConditionTest conditionTest = new ConditionTest(5);
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        conditionTest.add(System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        conditionTest.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
