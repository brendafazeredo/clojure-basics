(ns clojure-basics.db
  (:import
   (datomic Util))
  (:require [datomic.api :as d]
            [clojure.java.io :as io]
            [config.core :refer [env]]
            [datomock.core :as dm]))

(defonce conn (atom nil))

(def db-name "basics")

(def primary-uri (str (:datomic-uri env)))

(declare load-schema)

(defn migrations []
  (drop 1 (file-seq  (clojure.java.io/file (str  (System/getProperty "user.dir") "/resources/data/migrations/")))))

(defn read-all
  "reads an array of files"
  [f]
  (Util/readAll (io/reader f)))

(defn transact-all
  "takes an array of transactions, and transacts on each"
  [conn f]
  (doseq [txd (read-all f)]
    (d/transact conn txd)))

(defn load-schema
  "iterate on all the migrations, and transact them"
  []
  (doseq [file (migrations)]
    (transact-all @conn file)))

(defn init-conn
  "establishes a database connection, resets the var for global use"
  [& [uri]]
  (let [uri (or uri  primary-uri)
        conn (reset! conn (d/connect  uri))]
    (load-schema)
    conn))

(defn _d
  "returns a database connection"
  []
  (d/db @conn))

(defn by-id
  "take an id, return the instance of it"
  [id & [_db]]
  (let [_db (or _db (_d))]
    (try
      (d/entity _db id)
      (catch Exception _ nil))))

(defn transact
  "takes a single transaction, transacts it
  `(db/transact {:db/id 123, :user/name \"John\"`
  "
  [params]
  (let [res @(d/transact-async
              @conn
              [params])]
    res))

(defn create
  "takes a record, creates a temp-id, inserts it into the database. Returns the newly created entity"
  [params]
  (let [temp-id "id"
        now (new java.util.Date)
        tx (transact  (assoc  params
                              :ent/created_at now
                              :db/id temp-id))
        eid (get (:tempids tx) "id")]
    (by-id eid (_d))))
