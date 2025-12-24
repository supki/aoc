(ns aoc.day7
  (:require [clojure.string :as str]))

(defn parse-ipv7 [s]
  (let [seqs (re-seq #"[a-z]+" s)
        pairs (partition-all 2 seqs)]
    {:supernet (keep first pairs)
     :hypernet (keep second pairs)}))

(defn parse [s]
  (mapv parse-ipv7 (str/split-lines s)))

(defn is-ABBA? [[a0 b0 b1 a1]]
  (and (= a0 a1)
       (= b0 b1)
       (not= a0 b0)))

(defn contains-ABBA? [s]
  (some is-ABBA? (partition 4 1 s)))

(defn supports-TLS? [{:keys [supernet hypernet]}]
  (and (some contains-ABBA? supernet)
       (not (some contains-ABBA? hypernet))))

(defn is-ABA? [[a0 b a1]]
  (and (= a0 a1)
       (not= a0 b)))

(defn collect-ABAs [s]
  (filter is-ABA? (partition 3 1 s)))

(defn collect-all-ABAs [xs]
  (mapcat collect-ABAs xs))

(defn aba->bab [[a b _a]]
  [b a b])

(defn supports-SSL? [{:keys [supernet hypernet]}]
  (let [abas (collect-all-ABAs supernet)
        ; ABAs are no different from BABs at this point,
        ; so no collect-all-BABs
        babs (set (collect-all-ABAs hypernet))]
    (some
      (fn [aba]
        (contains? babs (aba->bab aba)))
      abas)))

(defn solve-part1 [path]
  (->> (slurp path)
       parse
       (filter supports-TLS?)
       count))

(defn solve-part2 [path]
  (->> (slurp path)
       parse
       (filter supports-SSL?)
       count))
