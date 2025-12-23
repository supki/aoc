(ns aoc.day11-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day11 :as day11]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 5
         (day11/solve-part1 "./input/day11.example.part1"))))

(deftest part1-problem
  (helper/is-golden "./output/day11.part1" (day11/solve-part1 "./input/day11.problem")))

(deftest part2-example
  (is (= 2
         (day11/solve-part2 "./input/day11.example.part2"))))

(deftest part2-problem
  (helper/is-golden "./output/day11.part2" (day11/solve-part2 "./input/day11.problem")))
