/* Generated by TooT */

package uk.org.toot.swingui.midiui.sequenceui;

import javax.swing.JPopupMenu;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import uk.org.toot.swingui.midiui.sequenceui.TrackLabel;
import uk.org.toot.midi.sequence.MidiTrack;

class TopTrackLabel extends TrackLabel
{
    private SequenceView view;

    public TopTrackLabel(SequenceView view) {
        super(view.getSequence(), view.getTopTrack());
		this.view = view;
    	view.addPropertyChangeListener("topTrack",
            new PropertyChangeListener() {
            	public void propertyChange(PropertyChangeEvent ev) {
                	setSelectedTrack(getSelectedTrack());
                	refresh();
            	}
        	}
        );
    }

    protected JPopupMenu createPopupMenu() {
        return new ViewTrackPopup();
    }

    // override
    public MidiTrack getSelectedTrack() {
        return view.getTopTrack();
    }

    public void setSelectedTrack(MidiTrack track) {
        super.setSelectedTrack(track);
  	    view.setTopTrack(track);
        view.setVisibleTrack(track, true); // else can't be on top
    }

    public class ViewTrackPopup extends TrackLabel.TrackPopup
    {
        // override
        protected boolean isValid(MidiTrack track) {
            return view.isValid(track);
        }

    }
}


