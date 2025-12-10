(ns aoc.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-input-to-string [path]
  (str/trim-newline (slurp path)))

(defrecord Range [lo hi])

(defn parse-range [s]
  (apply ->Range (map parse-long (str/split s #"-"))))

(defn parse-list [s]
  (str/split s #","))

(defn parse [s]
  (map parse-range (parse-list s)))

(defn duplicatee-seq-from [n]
  (let [n-str (str n)
        n-length (count n-str)
        n-part-length (int (/ n-length 2))]
    (if (even? n-length)
      (let [n-part-1 (parse-long (subs n-str 0 n-part-length))
            n-part-2 (parse-long (subs n-str n-part-length))]
        (if (>= n-part-1 n-part-2)
          n-part-1
          (+ n-part-1 1)))
      (long (Math/pow 10 n-part-length)))))

(defn duplicate [n]
  (parse-long (str n n)))

(defn invalid-ids [{:keys [lo hi]}]
  (take-while #(<= % hi) (map duplicate (iterate inc (duplicatee-seq-from lo)))))

(defn solution-part1 [path]
  (->> path
       read-input-to-string
       parse
       (mapcat invalid-ids)
       (reduce +)))
