(ns overtone-metronome.core
  (:use [overtone-metronome.osc-server :only [start-server!]]))

(defn -main
  "Starts an osc server"
  []
  (start-server!))


