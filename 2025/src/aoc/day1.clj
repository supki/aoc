(ns aoc.day1
  (:require [clojure.java.io :as io]))

(defn read-input-to-vec [path]
  (with-open [r (io/reader path)]
    (vec (line-seq r))))

(defn parse-rotation [s]
  (case (first s)
    \R (Integer/parseInt (subs s 1))
    \L (- (Integer/parseInt (subs s 1)))
    (throw (Exception. (format "invalid rotation line: %s" s)))))

(def starting-dial-position 50)

(def total-positions 100)

(defn rotate-once [x y]
  (mod (+ x y) total-positions))

(defn rotations [x ys]
  (reductions rotate-once x ys))

(defn password-part1 [xs]
  (count (filter #(= % 0) (rotations starting-dial-position xs))))

(defn rotate-once-but-fancy [x0 y0]
  (let [rotate-step
        (fn [x y]
          (let [z (+ x y)]
            [(abs (quot z total-positions)) (mod z total-positions)]))]
    (cond
      (pos? y0)
      (rotate-step x0 y0)
      :else
      ;; for negative (left) rotations, we "flip" the dial position
      ;; and apply the positive (right) rotation of the same
      ;; absolute value instead
      (let [flip #(mod (- total-positions %) total-positions)
            [n p] (rotate-step (flip x0) (abs y0))]
        [n (flip p)]))))

(defn password-part2 [xs]
  (->> xs
       (reductions
         (fn [[n p] r]
           (rotate-once-but-fancy p r))
         [0 starting-dial-position])
       (map first)
       (reduce + 0)))

(defn solve-part1 [path]
  (->> path
       read-input-to-vec
       (map parse-rotation)
       password-part1))

(defn solve-part2 [path]
  (->> path
       read-input-to-vec
       (map parse-rotation)
       password-part2))
