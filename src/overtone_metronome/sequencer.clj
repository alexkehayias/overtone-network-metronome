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

(defmacro play-sync
  "Play something sychronously for the specified time"
  [t & body]
  `(deref (future (do ~@body (Thread/sleep ~t))) ~t nil))

(defn play-cadence
  "Takes a metronome, synth, and collection of chord specs.

   Example:
   (play-cadence m my-synth [[:c2 :major 4]
                             [:f2 :major 2
                             [:g2 :major 2"
  [m synth chords]
  (doseq [[root kind beats] chords]
    (play-sync (m beats) (play-chord synth (chord root kind)))))


(comment
  (use 'overtone-metronome.sound)
  (play-cadence @global-metronome
                saw1
                [[:c2 :major 4]
                 [:g2 :major 4]]))

