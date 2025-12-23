(ns aoc.day10
  (:require [clojure.string :as str])
  (:import (com.google.ortools Loader)
           (com.google.ortools.linearsolver MPSolver MPSolver$ResultStatus)))

(defn bits->long [bits]
  (reduce-kv (fn [acc idx v] (if (pos? v) (bit-set acc idx) acc)) 0 bits))

(defn bit-positions->long [positions]
  (reduce bit-set 0 positions))

(defn partition-by+ [p xs]
  (remove (partial every? p) (partition-by p xs)))

(defn parse-target [s]
  (bits->long (mapv {\. 0 \# 1} (subs s 1 (dec (count s))))))

(defn parse-button [s]
  (->> (subs s 1 (dec (count s)))
       (partition-by+ #{\,})
       (map #(parse-long (apply str %)))
       bit-positions->long))

(defn parse-joltage [s]
  (->> (subs s 1 (dec (count s)))
       (partition-by+ #{\,})
       (mapv #(parse-long (apply str %)))))

(defn parse-machine [s]
  (let [[target-s & rest-s] (re-seq #"\S+" s)
        buttons-s (take-while #(str/starts-with? % "(") rest-s)
        joltage-s (last rest-s)]
    {:target (parse-target target-s)
     :buttons (map parse-button buttons-s)
     :joltage (parse-joltage joltage-s)}))

(defn parse-machines [s]
  (map parse-machine (str/split-lines s)))

(defn superset [xs]
  (reduce (fn [acc x]
            (into acc (map #(conj % x) acc)))
          [#{}]
          xs))

(defn press-buttons [bs]
  (reduce bit-xor 0 bs))

(defn solve-lights [{:keys [target buttons]}]
  "For part1, the idea is that a button press basically performs a XOR.
  Since XOR is an involution, there's never a reason to press a button more than once.
  This means that each button is either included in the optimal-button-presses set or
  excluded from it, leading to the notion of a superset. We just need to find
  a member of the superset of buttons -- that XORs to the target number -- of minimal size."
  (->> buttons
       superset
       (sort-by count)
       (filter #(= target (press-buttons %)))
       first
       count))

(defn button-bitvector [button joltage]
  (let [max-bit (count joltage)]
    (mapv (fn [bit] (if (pos? (bit-and button (bit-shift-left 1 bit))) 1 0)) (range max-bit))))

(defn solve-by-asking-someone-else [buttons joltage]
  "Gemini totally had nothing to do with this wall of crap."
  (Loader/loadNativeLibraries)
  (let [solver (MPSolver/createSolver "SCIP")
        button-count (count buttons)
        dims (count joltage)
        vars (mapv #(.makeIntVar solver 0.0 Double/POSITIVE_INFINITY (str "v" %))
                   (range button-count))
        objective (.objective solver)]

    (doseq [v vars] (.setCoefficient objective v 1.0))
    (.setMinimization objective)

    (dotimes [dim dims]
      (let [target-joltage (double (nth joltage dim))
            constraint (.makeConstraint solver target-joltage target-joltage)]
        (dotimes [button-idx button-count]
          (let [k (double (nth (nth buttons button-idx) dim))]
            (.setCoefficient constraint (nth vars button-idx) k)))))

    (let [result-status (.solve solver)]
      (if (= result-status MPSolver$ResultStatus/OPTIMAL)
        (map #(long (.solutionValue %)) vars)
        (throw (Exception. "No optimal solution found"))))))

(defn solve-joltage [{:keys [joltage buttons]}]
  (solve-by-asking-someone-else (map #(button-bitvector % joltage) buttons) joltage))

(defn solve-part1 [path]
  (transduce (map solve-lights)
             +
             (parse-machines (slurp path))))

(defn solve-part2 [path]
  (transduce (map #(reduce + (solve-joltage %)))
             +
             (parse-machines (slurp path))))
