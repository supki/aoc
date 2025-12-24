(ns aoc.day6
  (:require [clojure.string :as str]))

(defn- transpose [xs]
  (apply mapv vector xs))

(defn- parse [s]
  (transpose (str/split-lines s)))

(defn- solve-column [sel xs]
  (->> (frequencies xs)
       (apply sel val)
       key))

(defn solve-part1 [path]
  (->> path
       slurp
       parse
       (map (partial solve-column max-key))
       (apply str)))

(defn solve-part2 [path]
  (->> path
       slurp
       parse
       (map (partial solve-column min-key))
       (apply str)))

(->> "./input/day6.example" solve-part1 println)
(->> "./input/day6.example" solve-part2 println)
