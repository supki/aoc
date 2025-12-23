(ns aoc.day9-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day9 :as day9]
            [aoc.test_helper :as helper]))

(deftest part1-example
  (is (= 50
         (day9/solve-part1 "./input/day9.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day9.part1" (day9/solve-part1 "./input/day9.problem")))

(deftest part2-example
  (is (= 24
         (day9/solve-part2 "./input/day9.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day9.part2" (day9/solve-part2 "./input/day9.problem")))
