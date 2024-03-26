(ns clojure-basics.prod
  (:require [clojure-basics.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
