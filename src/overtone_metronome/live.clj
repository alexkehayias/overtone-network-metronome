(ns overtone-metronome.live
  (:use overtone.core
        overtone-metronome.sound
        overtone-metronome.sequencer
        [overtone-metronome.osc-server :only [server]] 
        [overtone-metronome.state :only [global-metronome]]))


;; Register a callback to start the song

(def song-seq
  (atom [[:D3 :major 2]
         [:A3 :major 2]
         [:B3 :minor 2]
         [:F#3 :minor 2]
         [:G3 :major 2]
         [:D3 :major 2]
         [:G3 :major 2]
         [:A3 :major 2]]))

(defn loop-coll
  [coll]
  (reset! coll (concat (rest @coll) (vector (first @coll)))))

(defn song
  [msg]
  (println msg)
  (when (= (first (:args msg)) 1)
    (play-cadence @global-metronome saw1 (loop-coll song-seq))))

(defn start-song
  []
  (osc-handle @server "/down" #'song))

(defn stop-song
  []
  (osc-rm-handler @server "/down"))
