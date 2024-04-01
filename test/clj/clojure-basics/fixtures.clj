(ns fixtures
  (:require
   [datomic.api :as d]
   [datomock.core :as dm]
   [clojure-basics.db :as db]))

(def conn-name (str "datomic:mem://test"))

(defn start-db []
  (d/create-database conn-name)
  (let [conn (d/connect conn-name)]
    (reset! db/conn (dm/fork-conn conn))
    (db/load-schema)))

;; Removed with-db function and fixtures namespace import

(defn create-todo [todo-name]
  (let [tx-result @(d/transact @db/conn [{:db/id (d/tempid :db.part/user)
                                          :todo/name todo-name}])]
    (first (:tempids tx-result))))

(defn read-todo [todo-id]
  (d/q '[:find ?name
         :where [?todo :todo/name ?name]
                [?todo :db/id ?id]]
       @db/conn
       todo-id))

(defn update-todo [todo-id new-name]
  (d/transact @db/conn [{:db/id todo-id
                         :todo/name new-name}]))

(defn delete-todo [todo-id]
  (d/transact @db/conn [[:db/retractEntity todo-id]]))
