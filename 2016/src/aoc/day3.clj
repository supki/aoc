(ns aoc.day3
  (:require [clojure.string :as str]))

(defn parse [s]
  (mapv #(mapv parse-long (re-seq #"\d+" %)) (str/split-lines s)))

(defn triangle? [[a b c]]
  (and (< c (+ a b))
       (< b (+ a c))
       (< a (+ b c))))

(defn transpose [xs]
  (apply mapv vector xs))

(defn read-input [path]
  (->> path slurp parse))

(defn solve-part1 [path]
  (->> path
       read-input
       (filter triangle?)
       count))

(defn solve-part2 [path]
  (->> path
       read-input
       transpose
       flatten
       (partition 3)
       (filter triangle?)
       count))

(->> "./input/day3.example" solve-part2 println)
