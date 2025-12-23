(ns aoc.day5
  (:require [clojure.string :as str]))

(defn parse-range [s]
  (let [[lo hi] (str/split s #"-")]
    [(parse-long lo) (parse-long hi)]))

(defn parse-ranges [lines]
  (mapv parse-range lines))

(defn parse-part1 [s]
  (let [[str-ranges str-ids] (str/split s #"\n\n" 2)]
    [(parse-ranges (str/split-lines str-ranges))
     (map parse-long (str/split-lines str-ids))]))

(defn parse-part2 [s]
  (->> s
       str/split-lines
       (take-while seq) ; weirdly, seq is preferred in Clojure over (complement empty?)
       parse-ranges))

(defn in-range? [id [lo hi]]
  (<= lo id hi))

(defn in-any-range? [id ranges]
  (some #(in-range? id %) ranges))

(defn merge-ranges [[lo0 hi0] [lo1 hi1]]
  (when (<= (max lo0 lo1) (min hi0 hi1))
    [(min lo0 lo1) (max hi0 hi1)]))

(defn merge-all-ranges [ranges]
  (reduce
    (fn [acc r]
      (let [r-last (peek acc)]
        (if-let [merged (and r-last (merge-ranges r-last r))]
          (conj (pop acc) merged)
          (conj acc r))))
    []
    (sort-by first ranges)))

(defn range-len [[lo hi]]
  (inc (- hi lo)))

(defn solve-part1 [path]
  (let [[ranges ids] (parse-part1 (slurp path))
        merged-ranges (merge-all-ranges ranges)]
    (transduce
      (filter (fn [id] (in-any-range? id merged-ranges)))
      (completing (fn [x _] (inc x)))
      0
      ids)))

(defn solve-part2 [path]
  (->> path
       slurp
       parse-part2
       merge-all-ranges
       (map range-len)
       (reduce +)))
