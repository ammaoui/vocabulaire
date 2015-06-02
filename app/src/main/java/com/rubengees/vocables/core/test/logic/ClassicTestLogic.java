package com.rubengees.vocables.core.test.logic;


import android.content.Context;
import android.os.Bundle;

import com.rubengees.vocables.core.testsettings.ClassicTestSettings;
import com.rubengees.vocables.core.testsettings.Direction;
import com.rubengees.vocables.pojo.Meaning;
import com.rubengees.vocables.pojo.Vocable;

/**
 * Created by Ruben on 04.04.2015.
 */
public class ClassicTestLogic extends TestLogic<ClassicTestSettings> {

    private double random;

    public ClassicTestLogic(Context context, ClassicTestSettings settings) {
        super(context, settings);

        next();
    }

    public ClassicTestLogic(Context context, Bundle savedInstanceState) {
        super(context, savedInstanceState);
    }

    @Override
    public boolean next() {
        super.next();

        random = Math.random();
        return getPosition() < getAmount();
    }

    @Override
    protected void restoreSavedInstanceState(Bundle savedInstanceState) {
        super.restoreSavedInstanceState(savedInstanceState);
        random = savedInstanceState.getDouble("random");
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        super.saveInstanceState(outState);
        outState.putDouble("random", random);
    }

    public Meaning processAnswer(String given) {
        Vocable current = getCurrentVocable();
        Meaning question = getQuestion();
        Meaning answer = getAnswer();
        boolean correct = given != null && getSettings().isCaseSensitive() ? answer.contains(given) : answer.containsIgnoreCase(given);

        processAnswer(current, question, answer, new Meaning(given), correct);

        if (correct) {
            return null;
        } else {
            return answer;
        }
    }

    public Meaning getQuestion() {
        Vocable vocable = getCurrentVocable();
        Direction direction = getSettings().getDirection();

        if (direction == Direction.FIRST) {
            return vocable.getFirstMeaning();
        } else if (direction == Direction.SECOND) {
            return vocable.getSecondMeaning();
        } else {
            if (random < 0.5) {
                return vocable.getFirstMeaning();
            } else {
                return vocable.getSecondMeaning();
            }
        }
    }

    public Meaning getAnswer() {
        Vocable vocable = getCurrentVocable();
        Direction direction = getSettings().getDirection();

        if (direction == Direction.FIRST) {
            return vocable.getSecondMeaning();
        } else if (direction == Direction.SECOND) {
            return vocable.getFirstMeaning();
        } else {
            if (random < 0.5) {
                return vocable.getSecondMeaning();
            } else {
                return vocable.getFirstMeaning();
            }
        }
    }
}