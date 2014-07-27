(ns overtone-metronome.live
  (:use overtone.core
        overtone-metronome.sound
        overtone-metronome.sequencer
        [overtone-metronome.osc-server :only [server]] 
        [overtone-metronome.state :only [global-metronome global-bpm]]))


;; Register a callback to start the song

(def song-seq
  (atom [[:D3 :major 4]
         [:A3 :major 4]
         [:B3 :minor 4]
         [:F#3 :minor 4]
         [:G3 :major 4]
         [:D3 :major 4]
         [:G3 :major 4]
         [:A3 :major 4]]))

(defn loop-coll
  [coll]
  (reset! coll (concat (rest @coll) (vector (first @coll)))))

(defn song
  [msg]
  (when (= (first (:args msg)) 1)
    (play-cadence @global-metronome saw1 (loop-coll song-seq))))

(defn start-song
  []
  (osc-handle @server "/down" #'song))

(defn stop-song
  []
  (osc-rm-handler @server "/down"))
