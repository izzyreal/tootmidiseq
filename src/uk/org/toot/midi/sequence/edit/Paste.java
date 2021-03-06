/* Generated by TooT */

package uk.org.toot.midi.sequence.edit;

import uk.org.toot.misc.UndoableCommand;

public class Paste extends CutPasteCommand implements UndoableCommand
{
    public Paste(CutPasteable selection)
    	throws CloneNotSupportedException {
        super(selection);
    }

    public String getName() { return "Paste"; }

    public boolean execute() { return paste(); }

    public boolean unexecute() { return cut(); }
}
