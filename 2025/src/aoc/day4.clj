(ns aoc.day4
  (:require [clojure.string :as str]))

(defn parse-rolls [s]
  (into #{}
        (for [[x line] (map-indexed vector (str/split-lines s))
              [y c] (map-indexed vector line)
              :when (= c \@)]
          [x y])))

(defn parse [path]
  (parse-rolls (slurp path)))

(def ^:const neighbors-deltas
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not (and (zero? dx) (zero? dy)))]
    [dx dy]))

(defn neighbors [[x y]]
  (map (fn [[dx dy]] [(+ x dx) (+ y dy)]) neighbors-deltas))

(defn reachable? [grid p]
  (< (count (keep grid (neighbors p))) 4))

(defn reachable-rolls [grid]
  (into #{} (filter #(reachable? grid %) grid)))

(defn prune-all-reachable [grid0]
  (loop [grid grid0
         reached-total 0]
    (let [reached (reachable-rolls grid)]
      (if (empty? reached)
        reached-total
        (recur (reduce disj grid reached) (+ reached-total (count reached)))))))

(defn solve-part1 [path]
  (->> path
       parse
       reachable-rolls
       count))

(defn solve-part2 [path]
  (->> path
       parse
       prune-all-reachable))
