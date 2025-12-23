(ns aoc.day12-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day12 :as day12]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 2
         (day12/solve-part1 "./input/day12.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day12.part1" (day12/solve-part1 "./input/day12.problem")))
