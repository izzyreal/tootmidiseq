/* Generated by TooT */

package uk.org.toot.swingui.midiui.sequenceui;

import uk.org.toot.midi.core.MidiSystem;
import javax.swing.JTabbedPane;

public class SidePanel extends JTabbedPane
{
    public SidePanel(OpenSequenceUI openSeqUI, MidiSystem rack) {
        TrackTable trackTable = new TrackTable(openSeqUI.getTrackTableModel(), new TrackPopupMenu(), rack);
        addTab("Tracks", new TrackTableView(trackTable));
        addTab("Events", new SequenceEditor(openSeqUI.getSequence()));
    }
}
