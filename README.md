# overtone-metronome

An overtone sequencer using an osc metronome as the timekeeper. The metronome sends ticks representing the down beat with the current beat number to the subscriber.

## Usage
The project requires that you have `liblo` installed. If you are on a mac `brew install liblo`.

1. Start the metronome. See https://github.com/jordanorelli/metronome.
2. Subscribe to the metronome `oscsend {metronome-ip-address} {port} /subscribe si {your-ip-address} {your-osc-port}`
3. Start the osc server `overtone-metronome.osc_server/start-server!`
4. Start the song `overtone-metronome.live/start-song`

## Notes
* The song is described as a sequence of chords and the number of beats for each chord
* The song sequence is played every measure to prevent the playback from drifting

## License

Copyright © 2014 Alex Kehayias

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
