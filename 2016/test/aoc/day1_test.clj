(ns aoc.day1-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day1 :as day1]
            [aoc.test_helper :as helper]))

(deftest part1-example1
  (is (= 5
         (day1/solve-part1 "./input/day1.example1"))))

(deftest part1-example2
  (is (= 2
         (day1/solve-part1 "./input/day1.example2"))))

(deftest part1-example3
  (is (= 12
         (day1/solve-part1 "./input/day1.example3"))))

(deftest part1-problem
  (helper/is-golden "./output/day1.part1" (day1/solve-part1 "./input/day1.problem")))

(deftest part2-example4
  (is (= 4
         (day1/solve-part2 "./input/day1.example4"))))

(deftest part2-problem
  (helper/is-golden "./output/day1.part2" (day1/solve-part2 "./input/day1.problem")))
