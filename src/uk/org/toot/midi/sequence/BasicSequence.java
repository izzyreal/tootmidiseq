/* Generated by TooT */

package uk.org.toot.midi.sequence;

import java.util.List;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.InvalidMidiDataException;

public class BasicSequence extends Sequence
{
    public BasicSequence(Sequence sequence) throws InvalidMidiDataException {
        super(sequence.getDivisionType(), sequence.getResolution());
        Track[] inTracks = sequence.getTracks();
        for (int t = 0; t < inTracks.length; t++) {
            tracks.add(inTracks[t]);
        }
    }

    public BasicSequence(float divisionType, int resolution) throws InvalidMidiDataException {
        super(divisionType, resolution);
    }

    public BasicSequence(float divisionType, int resolution, int numTracks) throws InvalidMidiDataException {
        super(divisionType, resolution, numTracks);
    }

    /**
     * Create a Track for potential Recording. Because we potentially may not use this Track we then delete it.
     * If we do use the Track we can add it back with addTrack().
     */
    public Track createRecordingTrack() {
        Track track = super.createTrack();
        super.deleteTrack(track);
        return track;
    }

/*    public void addDeviceName(Track track, String deviceName) {
        try {
            MidiMessage msg = createMeta(DEVICE_NAME, deviceName);
            track.add(new MidiEvent(msg, 0)); // add Track Name at tick 0
        } catch (InvalidMidiDataException imde) {
            imde.printStackTrace();
        }
    } */

    public void addTrack(Track t) {
        tracks.add(t);
    }

    public Track getTrack(int trk) {
        return tracks.get(trk);
    }

    public List<Track> getTrackList() { return (List<Track>) tracks; }
}
