(ns aoc.day8
  (:require [clojure.string :as str]))

(defn parse-rect [s]
  (if-let [[_ columns-s rows-s] (re-matches #"rect (\d+)x(\d+)" s)]
    {:op :rect
     :rows (parse-long rows-s)
     :columns (parse-long columns-s)}))

(defn parse-rotate-row [s]
  (if-let [[_ row-s positions-s] (re-matches #"rotate row y=(\d+) by (\d+)" s)]
    {:op :rotate-row
     :row (parse-long row-s)
     :positions (parse-long positions-s)}))

(defn parse-rotate-column [s]
  (if-let [[_ column-s positions-s] (re-matches #"rotate column x=(\d+) by (\d+)" s)]
    {:op :rotate-column
     :column (parse-long column-s)
     :positions (parse-long positions-s)}))

(defn parse-operation [s]
  (some #(% s) [parse-rect parse-rotate-row parse-rotate-column]))

(defn parse [s]
  (map parse-operation (str/split-lines s)))

(def width 50)
(def height 6)
(def initial-screen (vec (repeat height (vec (repeat width \space)))))

(defn rect [screen rows columns]
  (let [indices
        (for [row (range rows)
              column (range columns)]
          [row column])]
    (reduce #(assoc-in %1 %2 \#) screen indices)))

(defn rotate [row positions]
  (let [[rotated overflow] (split-at (- (count row) positions) row)]
    (vec (concat overflow rotated))))

(defn rotate-row [screen idx positions]
  (update screen idx #(rotate % positions)))

(defn transpose [xs]
  (apply mapv vector xs))

(defn rotate-column [screen idx positions]
  (transpose (update (transpose screen) idx #(rotate % positions))))

(defn apply-operation [screen op]
  (case (:op op)
    :rect
    (rect screen (:rows op) (:columns op))
    :rotate-row
    (rotate-row screen (:row op) (:positions op))
    :rotate-column
    (rotate-column screen (:column op) (:positions op))))

(defn solve-part1 [path]
  (->> (slurp path)
       parse
       (reduce apply-operation initial-screen)
       flatten
       (filter #{\#})
       count))

(defn solve-part2 [path]
  (->> (slurp path)
       parse
       (reduce apply-operation initial-screen)
       (map #(apply str %))
       (str/join "\n")))
