(ns aoc.day6
  (:require [clojure.string :as str]))

(defn parse-op [s]
  (case s
    \+ +
    \* *
    (throw (Exception. (format "expected + or *, got %s" s)))))

(defn words [s]
  (re-seq #"\S+" s))

(defn transpose [m]
  (apply mapv vector m))

(defn parse-part1 [s]
  (let [lines (str/split-lines s)
        ops (mapv parse-op (map first (words (peek lines))))
        operands (mapv #(mapv parse-long (words %)) (pop lines))]
    [ops (transpose operands)]))

(defn solve-part1 [path]
  (let [[ops operands] (->> path slurp parse-part1)]
    (reduce +
            (map apply ops operands))))

(defn all-spaces? [s]
  (every? #{\space} s))

(defn partition-by+ [p xs]
  (remove (partial every? p) (partition-by p xs)))

(defn parse-transposed-number [s]
  (parse-long (str/trim (if (#{\+ \*} (last s)) (subs s 0 (dec (count s))) s))))

(defn parse-part2 [s]
  (let [lines (str/split-lines s)
        lines-transposed (map (partial apply str) (transpose lines))
        lines-split (partition-by+ all-spaces? lines-transposed)
        ops (map (comp parse-op last first) lines-split)
        numbers (map (partial map parse-transposed-number) lines-split)]
    [ops numbers]))

(defn solve-part2 [path]
  (let [[ops operands] (->> path slurp parse-part2)]
    (reduce +
            (map apply ops operands))))
