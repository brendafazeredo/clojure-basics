(ns fixtures
  (:require
   [datomic.api :as d]
   [datomock.core :as dm]
   [clojure-basics.db :as db]))

(def conn-name (str "datomic:mem://test"))

(defn start-db []
  (d/create-database conn-name)
  (let [conn (d/connect conn-name)]
    (reset! db/conn  (dm/fork-conn conn))
    (db/load-schema)))

(defn with-db [f]
  (with-redefs [db/conn (atom nil)]
    (start-db)
    (f)))
