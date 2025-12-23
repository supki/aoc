(ns aoc.day5-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day5 :as day5]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 3
         (day5/solve-part1 "./input/day5.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day5.part1" (day5/solve-part1 "./input/day5.problem")))

(deftest part2-example
  (is (= 14
         (day5/solve-part2 "./input/day5.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day5.part2" (day5/solve-part2 "./input/day5.problem")))
