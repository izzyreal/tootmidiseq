/* Generated by TooT */

package uk.org.toot.midi.sequence.edit;

import uk.org.toot.misc.UndoableCommand;

public class Cut extends CutPasteCommand implements UndoableCommand
{
    public Cut(CutPasteable selection) {
//    	throws CloneNotSupportedException {
        super(selection);
    }

    public String getName() { return "Cut"; }

    public boolean execute() { return cut(); }

    public boolean unexecute() { return paste(); }
}