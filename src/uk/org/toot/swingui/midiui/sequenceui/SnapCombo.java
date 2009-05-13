/* Generated by TooT */

package uk.org.toot.swingui.midiui.sequenceui;

import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnapCombo extends JComboBox implements ActionListener
{
   	public final static String SNAP = "Snap";

    private SequenceView view;

    public SnapCombo(SequenceView view) {
        this.view = view;
        addItem(" Off ");
        addItem(" Bar ");
        addItem(" 1/2 ");
        addItem(" 1/4 ");
        addItem(" 1/8 ");
        addItem(" 1/16 ");
        addItem(" 1/32 ");
        addItem(" 1/64 ");

        Dimension size = new Dimension(64, 24);
        setPreferredSize(size);
        setMaximumSize(size);
        setSelectedIndex(5); // !!
        setActionCommand(SNAP);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
	   	if ( SNAP.equals(ae.getActionCommand()) ) {
        	view.setSnap(getSelectedIndex());
   		}
    }
}
