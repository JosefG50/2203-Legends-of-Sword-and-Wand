package com.github.campaign_progression.domain;

/**
 * Represents the scoring system for the campaign.
 *
 * <p>Score is accumulated based on:
 * <ul>
 *     <li>Total levels gained</li>
 *     <li>Extra gold collected</li>
 *     <li>Gold spent</li>
 * </ul>
 *
 * <p><b>Note:</b> This class is stateful. Each call to {@link #calculateScore(int, int, int)}
 * adds to the existing score rather than resetting it.</p>
 */
public class Score implements ScoreStats {

    /** The accumulated score. */
    private int score = 0;

    /**
     * Calculates and adds to the current score based on input values.
     *
     * <p>Formula:</p>
     * <ul>
     *     <li>+100 per level</li>
     *     <li>+extra gold</li>
     *     <li>+(goldSpent / 2) * 10</li>
     * </ul>
     *
     * @param goldSpent the amount of gold spent
     * @param totalLevels the total levels gained
     * @param extraGold additional gold collected
     * @return the updated total score
     */
    public int calculateScore(int goldSpent, int totalLevels, int extraGold) {

        score += totalLevels * 100;
        score += extraGold;
        score += (goldSpent / 2) * 10;

        return score;
    }

    /**
     * Returns the current accumulated score.
     *
     * @return the total score
     */
    @Override
    public int getScore() {
        return score;
    }
}