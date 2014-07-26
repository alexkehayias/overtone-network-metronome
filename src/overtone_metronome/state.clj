(ns overtone-metronome.state
  (:use overtone.core))


(def global-bpm
  (atom 120))

(def global-metronome
  (atom (metronome @global-bpm)))
