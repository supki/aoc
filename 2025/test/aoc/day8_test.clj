(ns aoc.day8-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day8 :as day8]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 40
         (day8/solve-part1 10 "./input/day8.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day8.part1" (day8/solve-part1 1000 "./input/day8.problem")))

(deftest part2-example
  (is (= 25272
         (day8/solve-part2 "./input/day8.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day8.part2" (day8/solve-part2 "./input/day8.problem")))
