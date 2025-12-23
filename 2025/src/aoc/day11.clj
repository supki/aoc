(ns aoc.day11
  (:require [clojure.string :as str]))

(defn parse-adjacency [s]
  (let [[from & to] (re-seq #"\w+" s)]
    [from to]))

(defn parse-graph [s]
  (into {} (mapv parse-adjacency (str/split-lines s))))

(defn enumerate-paths [start-node finish-node g]
  (let [memo-memes
        (memoize
          (fn [finder-fn node]
            (if (= node finish-node)
              1
              (let [neighbors (g node [])]
                (reduce + (map finder-fn neighbors))))))]
    (letfn [(finder [n] (memo-memes finder n))]
      (finder start-node))))

(defn solve-part1 [path]
  (let [[start-node finish-node] ["you" "out"]]
    (->> path
         slurp
         parse-graph
         (enumerate-paths start-node finish-node))))

(defn solve-part2 [path]
  (let [[start-node finish-node] ["svr" "out"]
        [intermediate-node1 intermediate-node2] ["dac" "fft"]
        g (->> path slurp parse-graph)]
    (+
      (* (enumerate-paths start-node intermediate-node1 g)
         (enumerate-paths intermediate-node1 intermediate-node2 g)
         (enumerate-paths intermediate-node2 finish-node g))
      (* (enumerate-paths start-node intermediate-node2 g)
         (enumerate-paths intermediate-node2 intermediate-node1 g)
         (enumerate-paths intermediate-node1 finish-node g)))))
