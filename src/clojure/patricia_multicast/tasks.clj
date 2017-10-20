(ns patricia-multicast.tasks
  (:require [patricia-multicast.tools :as tools]))

(def default-port 9)

(defmulti handle-main (fn [[f]] (keyword f)))

(defmethod handle-main :chat
  [[_ nome]]
  (let [ms      (tools/multicast-socket 4000)
        group   (tools/inet-address "224.2.2.3")
        _       (.joinGroup ms group)
        f-true  (future (while true
                          (-> (tools/dp-receive-msg ms 8192) 
                              println)))] 
    (tools/send-message
     ms (str nome " entrou no chat")
     group 4000)
    (loop [in (read-line)]
      (when-not (= in "out!")
        (tools/send-message
         ms (str nome ": " in)
         group 4000)
        (recur (read-line))))
    (future-cancel f-true)
    (tools/send-message
     ms (str nome " está caindo fora")
     group 4000)))

