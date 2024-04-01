(ns todo-test
  (:require
   [clojure-basics.todos.model :as model]
   [clojure-basics.db :as db]
   [clojure.test :refer [deftest is run-tests testing use-fixtures]]
   [clojure-basics.fixtures :as fixtures :refer [with-db create-todo read-todo update-todo delete-todo]]
   ))

(use-fixtures
  :once
  with-db)

(deftest create-todo-test
  (testing "Create Todo"
    (let [todo-name "Test Todo"
          todo-id (create-todo todo-name)
          todo (read-todo todo-id)]
      (is (= todo-name (first todo))))))

(deftest update-todo-test
  (testing "Update Todo"
    (let [todo-name "Test Todo"
          new-name "Updated Todo"
          todo-id (create-todo todo-name)]
      (update-todo todo-id new-name)
      (is (= new-name (-> (read-todo todo-id) first))))))

(deftest delete-todo-test
  (testing "Delete Todo"
    (let [todo-name "Test Todo"
          todo-id (create-todo todo-name)]
      (delete-todo todo-id)
      (is (nil? (read-todo todo-id))))))

(run-tests)
