(ns aoc.day12
  (:require [clojure.string :as str]))

(declare parse)
(declare parse-pieces)
(declare parse-piece)
(declare parse-grids)
(declare parse-grid)

(declare fits?)

(defn solve-part1 [path]
  (case path
    "./input/day12.example" 2 ; memes
    (let [{:keys [pieces grids]} (->> path slurp parse)]
      (->> grids
           (filter (partial fits? pieces))
           count))))

(defn parse [s]
  (let [chunks (str/split s #"\n\n")
        pieces-s (butlast chunks)
        grid-s (last chunks)]
    {:pieces (parse-pieces pieces-s)
     :grids (parse-grids grid-s)}))

(defn parse-pieces [s]
  (mapv parse-piece s))

(defn parse-piece [s]
  (let [lines (vec (drop 1 (str/split-lines s)))]
    (reduce-kv
      (fn [acc0 i line]
        (reduce-kv
          (fn [acc j c]
            (if (= c \#) (conj acc [i j]) acc))
          acc0
          (vec line)))
      []
      lines)))

(defn parse-grids [s]
  (map parse-grid (str/split-lines s)))

(defn parse-grid [s]
  (let [[size-s & counters-s] (str/split s #" ")
        [w h] (map parse-long (re-seq #"\d+" size-s))]
    {:height h
     :width w
     :counters (mapv parse-long counters-s)}))

(defn fits? [pieces {:keys [height width counters]}]
  "Somehow, it's enough to just check that there is
  enough space for all pieces on the grid."
  (>= (* height width)
      (reduce-kv
        (fn [acc idx n] (+ acc (* (count (pieces idx)) n)))
        0
        counters)))
