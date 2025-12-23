(ns aoc.day2-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [aoc.day2 :as day2]
            [aoc.test_helper :as helper]))

(deftest part1-example1
  (is (= "1985"
         (day2/solve-part1 "./input/day2.example"))))

(deftest part1-problem
  (helper/is-golden "./output/day2.part1" (day2/solve-part1 "./input/day2.problem")))

(deftest part2-example4
  (is (= "5DB3"
         (day2/solve-part2 "./input/day2.example"))))

(deftest part2-problem
  (helper/is-golden "./output/day2.part2" (day2/solve-part2 "./input/day2.problem")))
