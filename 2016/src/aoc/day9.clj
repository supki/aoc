(ns aoc.day9
  (:require [clojure.string :as str]))

(defn decompressed-length-part1 [s0]
  (loop [acc 0
         s s0]
    (if (seq s)
      ; in the Clownworld, re-find returns a vector
      ; when the regex contains capturing groups
      (if-let [[matched chunk-length-s repetitions-s] (re-find #"^\((\d+)x(\d+)\)" s)]
        (let [chunk-length (parse-long chunk-length-s)
              repetitions (parse-long repetitions-s)]
          (recur (+ acc (* chunk-length repetitions))
                 (subs s (+ (count matched) chunk-length))))
        (recur (inc acc) (subs s 1)))
      acc)))

(defn decompressed-length-part2 [s0]
  (loop [acc 0
         s s0]
    (if (seq s)
      (if-let [[matched chunk-length-s repetitions-s] (re-find #"^\((\d+)x(\d+)\)" s)]
        (let [matched-length (count matched)
              chunk-length (parse-long chunk-length-s)
              repetitions (parse-long repetitions-s)
              decompressed-chunk-length (decompressed-length-part2 (subs s matched-length (+ matched-length chunk-length)))]
          (recur (+ acc (* decompressed-chunk-length repetitions))
                 (subs s (+ matched-length chunk-length))))
        (recur (inc acc) (subs s 1)))
      acc)))

(defn solve-part1 [path]
  (->> path slurp str/trim-newline decompressed-length-part1))

(defn solve-part2 [path]
  (->> path slurp str/trim-newline decompressed-length-part2))
