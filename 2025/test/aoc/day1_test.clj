(ns aoc.day1-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day1 :as day1]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 3
         (day1/solve-part1 "./input/day1.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day1.part1" (day1/solve-part1 "./input/day1.problem")))

(deftest part2-example
  (is (= 6
         (day1/solve-part2 "./input/day1.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day1.part2" (day1/solve-part2 "./input/day1.problem")))
