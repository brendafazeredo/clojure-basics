(ns todo-test
  (:require
   [clojure-basics.todos.model :as model]
   [clojure-basics.db :as db]
   [clojure.test :refer [deftest is run-tests testing]]
   [clojure-basics.fixtures :as fixtures])) ;; Remove unused namespace import

;; Remove use-fixtures block

(deftest create-todo-test
  (testing "Create Todo"
    (let [todo-name "Test Todo"
          todo-id (fixtures/create-todo todo-name)
          todo (fixtures/read-todo todo-id)]
      (is (= todo-name (first todo))))))

(deftest read-todo-test
  (testing "Read Todo"
    (let [todo-name "Test Todo"
          todo-id (fixtures/create-todo todo-name)]
      (is (= todo-name (-> (fixtures/read-todo todo-id) first))))))

(deftest update-todo-test
  (testing "Update Todo"
    (let [todo-name "Test Todo"
          new-name "Updated Todo"
          todo-id (fixtures/create-todo todo-name)]
      (fixtures/update-todo todo-id new-name)
      (is (= new-name (-> (fixtures/read-todo todo-id) first))))))

(deftest delete-todo-test
  (testing "Delete Todo"
    (let [todo-name "Test Todo"
          todo-id (fixtures/create-todo todo-name)]
      (fixtures/delete-todo todo-id)
      (is (nil? (fixtures/read-todo todo-id))))))
