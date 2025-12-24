(ns aoc.day6-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day6 :as day6]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= "easter"
         (day6/solve-part1 "./input/day6.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day6.part1" (day6/solve-part1 "./input/day6.problem")))

(deftest part1-example
  (is (= "advent"
         (day6/solve-part2 "./input/day6.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day6.part2" (day6/solve-part2 "./input/day6.problem")))
