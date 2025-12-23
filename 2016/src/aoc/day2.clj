(ns aoc.day2
  (:require [clojure.string :as str]))

(defn parse [s]
  (map vec (str/split-lines s)))

(def keypad-part1
  [[\1 \2 \3]
   [\4 \5 \6]
   [\7 \8 \9]])

(def keypad-part2
  [[nil nil  \1 nil nil]
   [nil  \2  \3  \4 nil]
   [ \5  \6  \7  \8  \9]
   [nil  \A  \B  \C nil]
   [nil nil  \D nil nil]])

(defn starting-position [keypad]
  (some
    (fn [[x line]]
      (some
        (fn [[y c]]
          (when (= c \5) [x y]))
        (map-indexed vector line)))
    (map-indexed vector keypad)))

(def deltas
  {\U [-1  0]
   \D [ 1  0]
   \R [ 0  1]
   \L [ 0 -1]})

(defn move [keypad position0 direction]
  (let [position1 (mapv + position0 (deltas direction))]
    (if (get-in keypad position1)
      position1
      position0)))

(defn solve-button [keypad position directions]
  (reduce (partial move keypad) position directions))

(defn solve-buttons [keypad buttons]
  (mapv (partial get-in keypad)
        (rest (reductions (partial solve-button keypad) (starting-position keypad) buttons))))

(defn read-input [path]
  (->> path
       slurp
       parse))

(defn solve-part1 [path]
  (->> path
       read-input
       (solve-buttons keypad-part1)
       (apply str)))

(defn solve-part2 [path]
  (->> path
       read-input
       (solve-buttons keypad-part2)
       (apply str)))

(->> "./input/day2.example" solve-part1 println)
(->> "./input/day2.example" solve-part2 println)
