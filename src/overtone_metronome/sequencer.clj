(ns overtone-metronome.sequencer
  "Provides sequencer functions for describing loops and progressions"
  (:use overtone.core
        [overtone-metronome.state :only [global-metronome]]))


(defn play-note
  "Play the given music-note by triggering synth. Returns an instance 
   of a synth.

   Example:
   (play-note saw-wave :a2)
   ;; Play a sequence of notes
   (-> saw-wave (play-note :a2) (play-note :b2))"
  [synth music-note]
  (synth (midi->hz (note music-note))))

(defn play-chord
  "Play a chord with the given synth. Returns a realized sequence."
  [synth chord-seq]
  (doall (for [note chord-seq]
           (play-note synth note))))

(defmacro play-chord-sync
  "Play something sychronously for the specified time"
  [t & chord]
  `(let [~'result ~@chord
         ~'ids (map :id ~'result)]
     (Thread/sleep (/ ~t 1000000000))
     ;; Kill the sound for the chord
     (apply kill ~'ids)))

(defn play-cadence
  "Takes a metronome, synth, and collection of chord specs.

   Example:
   (play-cadence m my-synth [[:c2 :major 4]
                             [:f2 :major 2
                             [:g2 :major 2"
  [m synth chords]
  (doseq [[root kind beats] chords]
    (println (format "Playing %s %s" (name root) (name kind)))
    (play-chord-sync (m beats) (play-chord synth (chord root kind)))))

