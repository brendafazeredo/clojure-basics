(ns todo-test
  (:require
   [clojure-basics.todos.model :as model]
   [clojure-basics.db :as db]
   [clojure.test :refer [deftest is run-tests testing use-fixtures]]
   [fixtures :as fixtures :refer [with-db]]
   ))

(use-fixtures
  :once
  with-db)

(deftest db-connection
  (testing "db connection"
    (let [todo-name "test"
          todo (model/create-todo todo-name)]
      (is (= todo-name
             (:todo/name todo))))))


(run-tests)
