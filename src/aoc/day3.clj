(ns aoc.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-input-to-lines [path]
  (str/split-lines (slurp path)))

(defn parse-battery-bank [s]
  (map (comp parse-long str) (vec s)))

(defn find-b1 [bank]
  (let [bank (drop-last 1 bank)
        ibank (map-indexed vector bank)
        f (fn [[i-max v-max] [i v]]
            (if (> v v-max)
              [i v]
              [i-max v-max]))]
    (reduce f ibank)))

(defn find-b2 [bank [i b1]]
  (reduce max (drop (inc i) bank)))

(defn max-joltage [bank]
  (let [[i b1] (find-b1 bank)
        b2 (find-b2 bank [i b1])]
    (+ (* b1 10) b2)))

(defn solution-part1 [path]
  (->> path
       read-input-to-lines
       (map parse-battery-bank)
       (map max-joltage)
       (reduce +)))
