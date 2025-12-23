(ns aoc.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-input-to-lines [path]
  (line-seq (io/reader path)))

(defn parse-battery-bank [s]
  (map #(Character/digit % 10) s))

(defn max-joltage-n [n bank]
  (let [step (fn [xs digit]
               (map max xs (map #(+ (* 10 %) digit) (cons 0 xs))))]
    (->> bank
         (reduce step (repeat n 0))
         last)))

(defn solve-part1 [path]
  (->> path
       read-input-to-lines
       (map (comp #(max-joltage-n 2 %) parse-battery-bank))
       (reduce +)))

(defn solve-part2 [path]
  (->> path
       read-input-to-lines
       (map (comp #(max-joltage-n 12 %) parse-battery-bank))
       (reduce +)))
