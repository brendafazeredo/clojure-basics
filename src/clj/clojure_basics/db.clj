(ns clojure-basics.db
  (:require [datomic.api :as d]
            [datomock.core :as dm]))

(defn create-conn []
  (let [conn (d/connect "datomic:mem://hello-world")]
    (dm/mock-conn (d/db conn))))