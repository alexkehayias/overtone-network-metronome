(ns overtone-metronome.live
  (:use overtone.core
        overtone-metronome.sound
        overtone-metronome.sequencer
        [overtone-metronome.osc-server :only [server]] 
        [overtone-metronome.state :only [global-metronome]]))


;; Register a callback to start the song

(def song-seq
  (atom [[:c4 :major 2]
         [:f4 :major 2]]))

(defn next-cycle [coll]
  (reset! coll (concat (rest @coll) (vector (first @coll)))))

(defn song
  [msg]
  (println msg)
  (when (= (first (:args msg)) 1)
    (play-cadence @global-metronome saw1 (next-cycle song-seq))))

(defn start-song
  []
  (osc-handle @server "/down" #'song))

(defn stop-song
  []
  (osc-rm-handler @server "/down"))
