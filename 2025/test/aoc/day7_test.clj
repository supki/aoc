(ns aoc.day7-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day7 :as day7]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 21
         (day7/solve-part1 "./input/day7.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day7.part1" (day7/solve-part1 "./input/day7.problem")))

(deftest part2-example
  (is (= 40
         (day7/solve-part2 "./input/day7.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day7.part2" (day7/solve-part2 "./input/day7.problem")))
