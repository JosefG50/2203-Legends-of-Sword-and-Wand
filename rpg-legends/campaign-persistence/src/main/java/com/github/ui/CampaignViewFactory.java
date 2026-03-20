package com.github.ui;

public class CampaignViewFactory extends ViewCreator {
    @Override
    protected View createView(String locationType) {
        if (locationType.equalsIgnoreCase("Inn")) {
            return new InnView();
        } else {
            return new DungeonCampaignView();
        }
    }
}
