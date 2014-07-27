(ns overtone-metronome.osc-server
  (:use overtone.osc
        [overtone-metronome.sound :only [saw-wave]]))

(def HOST "localhost")
(def PORT 9000)

(def server (atom nil))
(def client (atom nil))
(def input-state (atom {}))

(def routes
  "Provides a map of routes of OSC urls to readable keywords."
  {"/down" :down-beat
   "/test" :test})

(defn update-input-state
  "Update the input state with the value from a message."
  [msg]
  (println "OSC msg:" msg)
  ;; (saw-wave 1000)
  (let [input (get routes (:path msg) routes)
        val (first (:args msg))
        update (partial assoc-in @input-state [input])]
    (reset! input-state (update val))))

(defn register-group-handler
  "Reuse the same handler for a group of routes.
   Routes must be a map of keyword url pairs.
   Handler must be a function that takes an OSC message as an arg."
  [server routes f]
  (let [format-route #(clojure.string/replace (str %) ":" "")]
    (doseq [route (map format-route (keys routes))]
      (osc-handle server route f))))

(defn start-server!
  "Bootraps an OSC server and client for testing in the repl."
  []
  (reset! server (osc-server PORT))
  (reset! client (osc-client HOST PORT))
  (register-group-handler @server routes #'update-input-state))

(defn shutdown!
  "Close client and server."  
  []
  (when @client (osc-close @client))
  (when @server (osc-close @server)))

(defn restart!
  "Close client and server and start up again."  
  []
  (shutdown!)
  (start-server!))

;; Send test messages
;; (doseq [val (range 10)]
;;   (osc-send @client "/down" (int val)))
;;
;; Add handler
;; (osc-handle @server uri func)
;;
;; Remove handler
;; (osc-rm-handler @server "/test")
;;
;; List all handlers
;; (println "registered handlers" (osc-handlers @server))

;;
;; Stop listening and deallocate resources
;; (osc-close @server)
