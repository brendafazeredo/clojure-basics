(ns clojure-basics.todos.model
  (:require
   [datomic.api :as d]
   [clojure-basics.db :as db]))

(defn create-todo [name]
  (db/create {:todo/name name }))
