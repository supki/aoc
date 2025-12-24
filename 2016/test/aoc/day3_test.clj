(ns aoc.day3-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day3 :as day3]
            [aoc.test_helper :as helper]))

(deftest part1-problem
  (helper/is-golden "./output/day3.part1" (day3/solve-part1 "./input/day3.problem")))

(deftest part2-problem
  (helper/is-golden "./output/day3.part2" (day3/solve-part2 "./input/day3.problem")))
