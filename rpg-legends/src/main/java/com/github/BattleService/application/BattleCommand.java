package com.github.BattleService.application;

enum BattleCommand {
    ATTACK,
    DEFEND,
    WAIT,
    CAST,
    UNKNOWN;

    static BattleCommand fromNormalized(String normalized) {
        if (normalized == null) {
            return UNKNOWN;
        }
        switch (normalized) {
            case "attack":
                return ATTACK;
            case "defend":
                return DEFEND;
            case "wait":
                return WAIT;
            case "cast":
                return CAST;
            default:
                return UNKNOWN;
        }
    }
}
