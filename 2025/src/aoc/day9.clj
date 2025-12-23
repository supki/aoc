(ns aoc.day9
  (:require [clojure.string :as str]))

(defn parse-points [s]
  (let [lines (str/split-lines s)]
    (mapv #(mapv parse-long (re-seq #"\d+" %)) lines)))

(defn all-pairs [v]
  (let [v-count (count v)]
    (vec
      (for [i (range v-count)
            j (range (inc i) v-count)]
        [(v i) (v j)]))))

(defn rectangle-area [[x0 y0] [x1 y1]]
  (* (inc (abs (- x1 x0))) (inc (abs (- y1 y0)))))

(defn intersects? [[x0 y0] [x1 y1] polygon-edges]
  (let [min-x (min x0 x1)
        max-x (max x0 x1)
        min-y (min y0 y1)
        max-y (max y0 y1)
        across (fn [{:keys [max-ex min-ex max-ey min-ey]}]
                 ; check that polygon edge is below the min-x
                 ; or above the max-x, etc, for each side
                 ; and invert it (very readable, I know)
                 (not
                   (or (<= max-ex min-x)
                       (>= min-ex max-x)
                       (<= max-ey min-y)
                       (>= min-ey max-y))))]
    (some across polygon-edges)))

(defn solve-part1 [path]
  (->> path
       slurp
       parse-points
       all-pairs
       (map (fn [[x y]] (rectangle-area x y)))
       (reduce max)))

(defn cache-minmax [edges]
  (map (fn [[[ex0 ey0] [ex1 ey1]]]
         {:max-ex (max ex0 ex1)
          :min-ex (min ex0 ex1)
          :max-ey (max ey0 ey1)
          :min-ey (min ey0 ey1)}) edges))

(defn solve-part2 [path]
  (let [points (->> path slurp parse-points)
        pairs (all-pairs points)
        polygon-edges (cache-minmax (partition 2 1 [(points 0)] points))]
    (->> pairs
         (keep (fn [[x y]] (when-not (intersects? x y polygon-edges) (rectangle-area x y))))
         (reduce max))))
