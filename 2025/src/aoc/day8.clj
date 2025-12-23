(ns aoc.day8
  (:require [clojure.string :as str]
            [clojure.set :as set]
            ; not a real union-find but good enough for this
            [engelberg.data.union-find :as uf]))

(defn parse-junction-boxes [s]
  (let [lines (str/split-lines s)]
    (mapv #(mapv parse-long (re-seq #"\d+" %)) lines)))

(defn all-pairs [v]
  (let [v-count (count v)]
    (vec
      (for [i (range v-count)
            j (range (inc i) v-count)]
        [(v i) (v j)]))))

(defn square [x]
  (* x x))

; sqrt is a meme
(defn distance [[x0 y0 z0] [x1 y1 z1]]
  (+ (square (- x0 x1))
     (square (- y0 y1))
     (square (- z0 z1))))

; a rough approximation of Data.List.sortOn
(defn sort-by-cached [f xs]
  (->> xs
       (map (fn [x] [(f x) x]))
       (sort-by first)
       (mapv second)))

(defn sort-by-distance [pairs]
  (sort-by-cached (fn [[x y]] (distance x y)) pairs))

(defn connect-pair [circuits [x y]]
  (uf/connect circuits x y))

(defn connect-into-single-circuit [circuits0 pairs0]
  (loop [circuits circuits0
         pairs pairs0]
    (let [[p & ps] pairs
          c (connect-pair circuits p)]
      (if (or (nil? p) (= (count (uf/components c)) 1))
        p
        (recur c ps)))))

(defn solve-part1 [n path]
  (let [boxes (->> path slurp parse-junction-boxes)
        circuits (apply uf/union-find boxes)
        pairs (sort-by-distance (all-pairs boxes))]
    (->> (reduce connect-pair circuits (take n pairs))
         uf/components
         (map count)
         (sort >)
         (take 3)
         (reduce *))))

(defn solve-part2 [path]
  (let [boxes (->> path slurp parse-junction-boxes)
        circuits (apply uf/union-find boxes)
        pairs (sort-by-distance (all-pairs boxes))]
    (->> (connect-into-single-circuit circuits pairs)
         (map first)
         (reduce *))))
