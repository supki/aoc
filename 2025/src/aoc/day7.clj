(ns aoc.day7
  (:require [clojure.string :as str]))

(defn parse-diagram [s]
  (let [lines (str/split-lines s)]
    (reduce-kv
      (fn [acc0 x line]
        (reduce-kv
          (fn [acc y c]
            (case c
              \^ (update acc :splitters conj [x y])
              \S (assoc acc :start [x y])
              acc))
          acc0
          (vec line)))
      {:height (count lines)
       :width (count (first lines))
       :splitters #{}
       :start nil}
      (vec lines))))

(def directions
  {:down [1 0]
   :left [0 -1]
   :right [0 1]})

(defn move [[x y] direction]
  (let [[dx dy] (directions direction)]
    [(+ x dx) (+ y dy)]))

(defn step-beam [diagram [pos0 timelines]]
  (let [pos1 (move pos0 :down)]
    (if ((:splitters diagram) pos1)
      {:splits 1
       :beams {(move pos1 :left) timelines (move pos1 :right) timelines}}
      {:splits 0
       :beams {pos1 timelines}})))

(defn step-beams [diagram beams0]
  (reduce
    (fn [{:keys [splits beams]} beam]
     (let [{s :splits b :beams} (step-beam diagram beam)]
       {:splits (+ splits s) :beams (merge-with + beams b)}))
    {:splits 0 :beams {}}
    beams0))

(defn walk-diagram [diagram]
  (nth
    (iterate
      (fn [{:keys [splits beams]}]
        (let [{s :splits b :beams} (step-beams diagram beams)]
          {:splits (+ splits s)
           :beams b}))
      {:splits 0 :beams {(:start diagram) 1}})
    (dec (:height diagram))))

(defn solve-part1 [path]
  (->> path
       slurp
       parse-diagram
       walk-diagram
       :splits))

(defn solve-part2 [path]
  (let [sum-timelines (fn [m] (reduce + 0 (vals m)))]
    (->> path
         slurp
         parse-diagram
         walk-diagram
         :beams
         sum-timelines)))
