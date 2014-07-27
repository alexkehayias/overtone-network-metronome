(ns overtone-metronome.state
  (:use overtone.core))


(def system-time
  (atom {}))

(def global-bpm
  (atom 120))

(def global-metronome
  (atom (metronome @global-bpm)))

(defn update-tempo
  [msg]
  (println "OSC msg:" msg)
  (reset! global-bpm (int
                      (Math/floor
                       (/ 60 (/ (- (System/currentTimeMillis) @system-time) 1000.)))))
  (reset! system-time (System/currentTimeMillis)))
