package com.github.campaign_progression.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void calculateScore_correctCalculation() {
        Score score = new Score();

        int result = score.calculateScore(10, 2, 5);

        // totalLevels * 100 = 200
        // extraGold = 5
        // (10 / 2) * 10 = 50
        // total = 255
        assertEquals(255, result);
    }

    @Test
    void calculateScore_updatesInternalScore() {
        Score score = new Score();

        score.calculateScore(10, 1, 0);

        assertEquals(score.getScore(), score.calculateScore(0, 0, 0));
    }

    @Test
    void calculateScore_multipleCalls_accumulatesScore() {
        Score score = new Score();

        int first = score.calculateScore(10, 1, 0); // 100 + 50 = 150
        int second = score.calculateScore(10, 1, 0); // +150 again

        assertEquals(150, first);
        assertEquals(300, second);
    }

    @Test
    void calculateScore_zeroValues_returnsZero() {
        Score score = new Score();

        int result = score.calculateScore(0, 0, 0);

        assertEquals(0, result);
    }

    @Test
    void calculateScore_handlesOddGoldDivision() {
        Score score = new Score();

        int result = score.calculateScore(9, 0, 0);

        // 9 / 2 = 4 (integer division)
        // 4 * 10 = 40
        assertEquals(40, result);
    }

    @Test
    void getScore_initiallyZero() {
        Score score = new Score();

        assertEquals(0, score.getScore());
    }
}