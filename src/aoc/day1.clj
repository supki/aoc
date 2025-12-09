(ns aoc.day1
  (:require [clojure.java.io :as io]))

(defn read-input-to-vec [path]
  (with-open [r (io/reader path)]
    (vec (line-seq r))))

(defn parse-rotation [str]
  (case (first str)
    \R (Integer/parseInt (subs str 1))
    \L (- (Integer/parseInt (subs str 1)))
    :else (throw (Exception. (format "invalid rotation line: %s" str)))))

(def starting-dial-position 50)

(def total-positions 100)

(defn rotate-once [x y]
  (mod (+ x y) total-positions))

(defn rotations [x ys]
  (reductions rotate-once x ys))

(defn password1 [xs]
  (count (filter #(= % 0) (rotations starting-dial-position xs))))

(defn rotate-once2 [x y]
  (cond
    (> y 0)
    (let [z (+ x y)]
      [(abs (quot z total-positions)) (mod z total-positions)])
    :else
    ;; for negative (left) rotations, we "flip" the dial position
    ;; and apply the positive (right) rotation of the same
    ;; absolute value instead
    (let [flip #(mod (- total-positions %) total-positions)
          [n p] (rotate-once2 (flip x) (abs y))]
      [n (flip p)])))

(defn password2 [xs]
  (->> xs
       (reductions
         (fn [[n p] r]
           (rotate-once2 p r))
         [0 starting-dial-position])
       (map first)
       (reduce + 0)))

(defn solution1 [path]
  (->> path
       read-input-to-vec
       (map parse-rotation)
       password1))

(defn solution2 [path]
  (->> path
       read-input-to-vec
       (map parse-rotation)
       password2))
