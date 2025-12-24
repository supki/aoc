(ns aoc.day4-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day4 :as day4]
            [aoc.test_helper :as helper]))

(deftest part1-problem
  (helper/is-golden "./output/day4.part1" (day4/solve-part1 "./input/day4.problem")))

(deftest part2-problem
  (helper/is-golden "./output/day4.part2" (day4/solve-part2 "./input/day4.problem")))
