(ns overtone-metronome.sound
  (:use overtone.core))

(defonce sc-server (boot-server))

(definst steel-drum [note 60 amp 0.8 gate 0]
  (let [freq (midicps note)]
    (* amp
       (env-gen (perc 0.01 2.0) 1 1 0 1 :action FREE)
       (+ (sin-osc (/ freq 6))
          (rlpf (saw freq) (* 2.1 freq) 0.4)))))

(definst ding
  [note 60 velocity 100 gate 1]
  (let [freq (midicps note)
        amp  (/ velocity 127.0)
        snd  (sin-osc freq)
        env  (env-gen (adsr 0.001 0.1 0.6 0.3) gate :action FREE)]
    (* amp env snd)))

(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (env-lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

(definst saw1 [freq 60] (saw freq))

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
  "Play a chord with the given synth. Returns a sequence"
  [synth chord-seq]
  (doall (for [note chord-seq]
           (play-note synth note))))

(defn with-duration [n f]
  
  
  )
