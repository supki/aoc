(ns aoc.day1
  (:require [clojure.string :as str]))

(defn parse-instruction [s]
  {:turn (first s)
   :steps (parse-long (subs s 1))})

(defn parse [s]
  (mapv parse-instruction (re-seq #"\w+" s)))

(def direction-rotations
  {:n {\L :w \R :e}
   :e {\L :n \R :s}
   :s {\L :e \R :w}
   :w {\L :s \R :n}})

(defn replace-turn->direction [instrs]
  (rest
    (reductions
      (fn [{:keys [direction]} {:keys [turn steps]}]
        {:direction (get-in direction-rotations [direction turn])
         :steps steps})
      {:direction :n}
      instrs)))

(def deltas
  {:n [1 0]
   :e [0 1]
   :s [-1 0]
   :w [0 -1]})

(defn move-delta [{:keys [direction steps]}]
  (let [[dx dy] (deltas direction)]
    [(* dx steps) (* dy steps)]))

(defn move [[x y] instr]
  (let [[move-dx move-dy] (move-delta instr)]
    [(+ x move-dx) (+ y move-dy)]))

(defn follow [instrs]
  (reduce move [0 0] instrs))

(defn trace [instrs]
  (reductions move [0 0] instrs))

(defn distance [[x y]]
  (+ (abs x) (abs y)))

(defn between? [this that x]
  (<= (min this that) x (max this that)))

(defn intersects? [[[ax0 ay0] [ax1 ay1]] [[bx0 by0] [bx1 by1]]]
  (cond
    (= ay0 ay1) ; (= bx0 bx1)
    (let [x bx0
          y ay0]
      (when (and (between? ax0 ax1 x) (between? by0 by1 y))
        [x y]))
    (= ax0 ax1) ; (= by0 by1)
    (let [x ax0
          y by0]
      (when (and (between? bx0 bx1 x) (between? ay0 ay1 y))
        [x y]))
    :else ; irrelevant for the problem
    nil))

(defn first-intersection [points]
  (loop [intersectable []
         previous nil
         [segment & segments] (partition 2 1 points)]
    (if-not segment
      nil
      (let [intersects-at (some (partial intersects? segment) intersectable)]
        (if intersects-at
          intersects-at
          (recur (cond-> intersectable previous (conj previous)) segment segments))))))

(defn read-input [path]
  (->> path
       slurp
       parse))

(defn solve-part1 [path]
  (->> path
       read-input
       replace-turn->direction
       follow
       distance))

(defn solve-part2 [path]
  (->> path
       read-input
       replace-turn->direction
       trace
       first-intersection
       distance))

(->> "./input/day1.example1" solve-part1 println)
(->> "./input/day1.example2" solve-part1 println)
(->> "./input/day1.example3" solve-part1 println)
(->> "./input/day1.example4" solve-part2 println)
