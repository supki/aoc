(ns aoc.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-input-to-string [path]
  (str/trim-newline (slurp path)))

(defrecord Range [lo hi])

(defn parse-range [s]
  (apply ->Range (map parse-long (str/split s #"-"))))

(defn parse [s]
  (map parse-range (str/split s #",")))

; we can obviously compute prime factors,
; but there's just not enough variance in
; lengths of numbers in the problem input
; not to hardcode them
(def prime-factors
  {2 [2]
   3 [3]
   4 [2]
   5 [5]
   6 [2 3]
   7 [7]
   8 [2]
   9 [3]
   10 [2 5]})

(defn range-prime-factors [{:keys [lo hi]}]
  (distinct
    (mapcat prime-factors [(count (str lo)) (count (str hi))])))

(defn split-every [n s]
  (map #(apply str %) (partition n s)))

(defn duplicatee-seq-starting-with [n d]
  (let [n-str (str n)
        n-length (count n-str)
        n-chunk-length (quot n-length d)]
    (if (zero? (mod n-length d))
      ; every number is split in d chunks where d >= 2
      (let [[n-chunk-1 n-chunk-2] (map parse-long (split-every n-chunk-length n-str))]
        (if (>= n-chunk-1 n-chunk-2)
          n-chunk-1
          (inc n-chunk-1)))
      (long (Math/pow 10 n-chunk-length)))))

; cute names never ever bite me in the ass
(defn nplicate [n x]
  (parse-long (apply str (replicate n x))))

(defn invalid-ids-with-prime-factor [d {:keys [lo hi]}]
  (->> (duplicatee-seq-starting-with lo d)
       (iterate inc)
       (map #(nplicate d %))
       (take-while #(<= % hi))))

(defn invalid-ids-part1 [r]
  (invalid-ids-with-prime-factor 2 r))

; this should probably be a k-way merge, but the problem is small enough
(defn invalid-ids-part2 [r]
  (into #{} (mapcat #(invalid-ids-with-prime-factor % r) (range-prime-factors r))))

(defn solve-part1 [path]
  (->> path
       read-input-to-string
       parse
       (mapcat invalid-ids-part1)
       (reduce +)))

(defn solve-part2 [path]
  (->> path
       read-input-to-string
       parse
       (mapcat invalid-ids-part2)
       (reduce +)))
