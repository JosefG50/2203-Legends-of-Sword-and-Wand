// With the use of AI
package com.github.ui;

/**
 * The ViewCreator class is an abstract class that follows the Factory Method pattern.
 * It provides a template for rendering locations by creating the appropriate View.
 */
public abstract class ViewCreator {
    /**
     * Renders the location for the specified location type.
     * It uses the createView method to get the appropriate View and then calls its display method.
     *
     * @param locationType the type of location to render
     */
    public void renderLocation(String locationType) {
        View v = createView(locationType);
        v.display();
    }
    
    /**
     * An abstract factory method that must be implemented by concrete subclasses
     * to create the specific View for a given location type.
     *
     * @param locationType the type of location for which to create a view
     * @return the created View instance
     */
    protected abstract View createView(String locationType);
}
