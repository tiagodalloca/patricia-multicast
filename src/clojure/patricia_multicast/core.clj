(ns patricia-multicast.core
  (:require [patricia-multicast.tasks :refer [handle-main]])
  (:gen-class))

(defn -main 
  [& args]
  (handle-main (vec args))
  (System/exit 0))
