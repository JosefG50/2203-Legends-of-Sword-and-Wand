package com.github.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    void calculateScoreShouldReturnCorrectValue() {
        int result = score.calculateScore(100, 5, 50);

        int expected = (5 * 100) + 50 + ((100 / 2) * 10);
        assertEquals(expected, result);
    }

    @Test
    void calculateScoreShouldAccumulateScore() {
        int first = score.calculateScore(100, 1, 0);
        int second = score.calculateScore(100, 1, 0);

        assertTrue(second > first);
    }

    @Test
    void getScoreShouldReturnCurrentScore() {
        score.calculateScore(50, 2, 10);

        assertEquals(score.getScore(), score.calculateScore(0, 0, 0));
    }

    @Test
    void initialScoreShouldBeZero() {
        assertEquals(0, score.getScore());
    }

    @Test
    void calculateScoreWithZeroValues() {
        int result = score.calculateScore(0, 0, 0);
        assertEquals(0, result);
    }
}