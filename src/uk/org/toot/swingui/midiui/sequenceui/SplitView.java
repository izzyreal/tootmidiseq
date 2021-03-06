/* Generated by TooT */

package uk.org.toot.swingui.midiui.sequenceui;

import java.awt.Component;
import javax.swing.JSplitPane;

public class SplitView extends JSplitPane implements Dividable {

    private int bothLocation ;

    public SplitView(Component top, Component bottom) {
        super(JSplitPane.VERTICAL_SPLIT, false, top, bottom);
    }

/*    public void setVisible(boolean visible) {
        adjustDivider();
        super.setVisible(visible);
    } */

    public void adjustDivider() {
        boolean visible = true; // default assumption
        boolean wasVisible = isVisible();
        if ( getTopComponent().isVisible() && getBottomComponent().isVisible() ) {
            // set the divider to show both
            setDividerLocation(bothLocation);
        } else if ( getTopComponent().isVisible() ) {
            // detect transition from both visible !!
            if ( isVisible() ) bothLocation = getDividerLocation();
            setDividerLocation(1.0); // divider to bottom
        } else if ( getBottomComponent().isVisible() ) {
            // detect transition from both visible !!
            if ( isVisible() ) bothLocation = getDividerLocation();
            setDividerLocation(0.0); // divider to top
        } else { // not visible special case
        	visible = false;
        }
        setVisible(visible);
        if ( visible != wasVisible ) {
            if ( getParent() instanceof Dividable ) {
                ((Dividable)getParent()).adjustDivider();
            }
        }
    }
}
