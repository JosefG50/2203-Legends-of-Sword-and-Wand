package com.github.ui;

public abstract class ViewCreator {
    public void renderLocation(String locationType) {
        View v = createView(locationType);
        v.display();
    }
    
    protected abstract View createView(String locationType);
}
