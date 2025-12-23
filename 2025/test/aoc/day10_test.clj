(ns aoc.day10-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day10 :as day10]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 7
         (day10/solve-part1 "./input/day10.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day10.part1" (day10/solve-part1 "./input/day10.problem")))

(deftest part2-example
  (is (= 33
         (day10/solve-part2 "./input/day10.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day10.part2" (day10/solve-part2 "./input/day10.problem")))
