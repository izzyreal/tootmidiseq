/* Generated by TooT */

package uk.org.toot.midi.sequence;

import javax.sound.midi.Track;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.MidiMessage;
import static uk.org.toot.midi.message.MetaMsg.*;
import static uk.org.toot.midi.message.ChannelMsg.*;

/**
 * Some Midi utility functions.
 * @author Steve Taylor
 */
public class Midi {
    /** Convert from microseconds per quarter note to beats per minute and vice versa */
    public static float convertTempo(float value) {
        if (value <= 0) {
            value = 0.1f;
        }
        return 60000000.0f / value;
    }

    /** Return position in form mm:ss:?? bug: m:s need zero prefix padding */
    public static String timePosition(long us) {
        float sec = (us) / 1000000L;
        int mm = (int)sec / 60;
        int ss = (int)sec - 60 * mm;
        int t = (int)(10 * (sec - (ss + 60 * mm)));
        String sep1 = ss < 10 ? ":0" : ":";
        return mm + sep1 + ss + "." + t;
    }

    public static Sequence unMix(Sequence mix) throws InvalidMidiDataException {
        // if it already appears to be unmixed Type 1, leave it
        if (mix.getTracks().length > 1) return mix; //Midi.unFasten(mix) ; //!!
        System.out.println("UnMixing because only " + mix.getTracks().length + " track");

        /*
         * Prepare a sequence with the same division type and resolution,
         * to unMix to what is effectively a Type 1 MIDI file representation.
         */

        Sequence unmixed = new Sequence(mix.getDivisionType(), mix.getResolution());

        /* The first track of a Format 1 file is special,
         * and is also known as the 'Tempo Map'.
         * Create it now to ensure it is the first track ;)
         */

        Track ctrlTrack = unmixed.createTrack();
        // !! need to cope with 16 channels per port !!!
        // Room for 16 more tracks, one per MIDI channel
        Track chanTrack[] = new Track[16];
        Track[] mixT = mix.getTracks();
        // for each mixed track
        for (int i = 0; i < mixT.length; i++) {
            Track trk = mixT[i];
            int chan = -1;
            int chanPrefix = -1;
            MidiEvent heldEvent = null; // port hack
            // for each event in the track
            for (int v = 0; v < trk.size(); v++) {
                MidiEvent event = trk.get(v);
                MidiMessage msg = event.getMessage();
                // unmix event to appropriate track based on channel

	    	    /* ctrlTrack gets all meta-events of the types Time Signature
    	    	 * and Set Tempo
	        	 * The meta-events Sequence/Track Name, Sequence Number, Marker,
		         * and SMPTE Offset. should also be on the first track
    		     */

                if (isChannel(msg)) {
                    // well ChannelMsg won't accept 0xF0 so lose the if?
                    if (getCommand(msg) != 0xF0) {
   	                    chan = getChannel(msg);
       	                chanPrefix = -1; // reset as per MIDI spec
           	        }
                } else {
                    chan = chanPrefix; // default route
                }
                if ( isMeta(msg) ) {
                    switch (getType(msg)) {
                        case CHANNEL_PREFIX: // Channel Prefix
                            chan = chanPrefix = getData(msg)[0] & 0x0F; // note: set chan member
                            //						System.out.println("Channel Prefix: "+(1+chan));
                            break;
                        case PORT_PREFIX:
                            // needs routing to the right chan of this port
//                            int port = getData(msg)[0] & 0x0F;
                            // chan isn't known yet ;)
                            heldEvent = event;
                            //						System.out.println("Port Prefix: "+(1+port));
                            continue;
                    }
                }
                // chan is either valid or -1 for ctrlTrack
                if (chan == -1) {
                    ctrlTrack.add(event);
                }
                else {
                    // create track if first event on this channel
                    if (chanTrack[chan] == null)
                        chanTrack[chan] = unmixed.createTrack();
                    if (heldEvent != null) {
                        chanTrack[chan].add(heldEvent);
                        heldEvent = null;
                    }
                    chanTrack[chan].add(event);
                }
            }
        }
        return unmixed;
    }

}
