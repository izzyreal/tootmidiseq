/* Generated by TooT */

package uk.org.toot.swingui.midiui.sequenceui;

import java.beans.PropertyChangeSupport;
//import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.Dimension;
//import java.awt.Color;
//import java.awt.Component;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.AbstractButton;

import uk.org.toot.swingui.miscui.TootBar;

/**
 * <{ToolPalette}>
 */
class ToolPalette extends TootBar implements MouseInputListener
{
    /**
     * @label current tool
     * @clientCardinality 1 
     */
    private PaletteTool currentTool = null;
    private ButtonGroup buttons;
    private PropertyChangeSupport propertyChangeSupport;

    public ToolPalette(Editor editor) {
        super("Tool Palette");
        setOrientation(TootBar.VERTICAL);
        propertyChangeSupport = new PropertyChangeSupport(this);
        buttons = new ButtonGroup();
        addTool(new SelectTool(editor));
        addTool(new DrawTool(editor));
        addTool(new EraseTool(editor));
        if ( editor.canTruncate() ) {
        	addTool(new TruncateTool(editor));
        }

    }

    public void addTool(PaletteTool tool) {
        AbstractButton button =
            new JToggleButton(//tool.getName().substring(0,2),
            				  tool.getIcon());
        button.addActionListener(new ToolListener(tool));
        button.setToolTipText(tool.getName());
        // ensure we have a current tool, default to the first
        if ( getCurrentTool() == null ) {
            setCurrentTool(tool);
            button.setSelected(true);
        }
        add(button);
        buttons.add(button);
    }

    public PaletteTool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(PaletteTool tool) {
        PaletteTool oldTool = currentTool;
        currentTool = tool;
        propertyChangeSupport.firePropertyChange("currentTool",
            oldTool, currentTool);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public void mouseClicked(MouseEvent e) {
        if ( isToolButton(e) ) currentTool.mouseClicked(e);
    }

    public void mouseEntered(MouseEvent e) {
        currentTool.mouseEntered(e);
    }

    public void mouseExited(MouseEvent e) {
        currentTool.mouseExited(e);
    }

    public void mousePressed(MouseEvent e) {
        if ( isToolButton(e) ) currentTool.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        if ( isToolButton(e) ) currentTool.mouseReleased(e);
    }

    public void mouseDragged(MouseEvent e) {
        currentTool.mouseDragged(e);
    }

    public void mouseMoved(MouseEvent e) {
        currentTool.mouseMoved(e);
    }

    @SuppressWarnings("static-access")
	protected boolean isToolButton(MouseEvent e) {
        return (e.getModifiers() & e.BUTTON1_MASK) == e.BUTTON1_MASK;
    }

    private class ToolListener implements ActionListener
    {
        private PaletteTool tool;

        public ToolListener(PaletteTool tool) {
            this.tool = tool;
        }

       	public void actionPerformed(ActionEvent ev) {
        	setCurrentTool(this.tool);
       	}
    }

}


