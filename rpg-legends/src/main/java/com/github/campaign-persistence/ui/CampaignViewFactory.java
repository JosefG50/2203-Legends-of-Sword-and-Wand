// With the use of AI
package com.github.ui;

import org.springframework.stereotype.Component;

/**
 * The CampaignViewFactory class is a concrete implementation of ViewCreator.
 * It creates specific View instances based on the provided location type.
 */
@Component
public class CampaignViewFactory extends ViewCreator {
    /**
     * Creates a View for the specified location type.
     *
     * @param locationType the type of location for which to create a view
     * @return an InnView if locationType is "Inn", otherwise a DungeonCampaignView
     */
    @Override
    protected View createView(String locationType) {
        if (locationType.equalsIgnoreCase("Inn")) {
            return new InnView();
        } else {
            return new DungeonCampaignView();
        }
    }
}
