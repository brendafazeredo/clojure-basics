(ns todo-test
  (:require
   [clojure-basics.todos.model :as model]
   [clojure.test :refer [deftest is run-tests testing use-fixtures]]
   [fixtures :refer [with-db]]));; Remove unused namespace import

;; Remove use-fixtures block

(use-fixtures :each with-db)

(deftest create-todo-test
  (testing "Create Todo"
    (let [todo-name "Test Todo"
          todo-id (model/create-todo todo-name)]
      (is (not (nil? todo-id))))))


(run-tests)