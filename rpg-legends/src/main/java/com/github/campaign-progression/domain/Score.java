package com.github.domain;
public class Score implements ScoreStats{
    private int score = 0;

    public int calculateScore(int goldSpent, int totalLevels, int extraGold) {

        score += totalLevels * 100;
        score += extraGold;
        score += goldSpent/2 * 10;

        return score;
    }

    @Override
    public int getScore() {
        return score;
    }

    
}
