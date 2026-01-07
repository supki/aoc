(ns aoc.day10
  (:require [clojure.string :as str]))

(def instr-give-regex
  #"value (\d+) goes to bot (\d+)")
(def instr-distribute-regex
  #"bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)")

(defn parse-instr [s]
  (if-let [[_ value-s bot-s] (re-find instr-give-regex s)]
    {:op :set
     :bot (parse-long bot-s)
     :value (parse-long value-s)}
    (if-let [[_ bot-s low-out-s low-value-s high-out-s high-value-s] (re-find instr-distribute-regex s)]
      {:op :transfer
       :bot (parse-long bot-s)
       :low [(keyword low-out-s) (parse-long low-value-s)]
       :high [(keyword high-out-s) (parse-long high-value-s)]}
      (throw (Exception. (format "unknown instruction: %s" s))))))

(defn parse [s]
  (map parse-instr (str/split-lines s)))

(defn init-bot-state [instrs]
  (reduce
    (fn [acc instr] (update acc (:bot instr) (fnil conj []) (:value instr)))
    {}
    (filter #(= (:op %) :set) instrs)))

(defn init-transfers [instrs]
  (reduce
    (fn [acc {:keys [bot low high]}] (assoc acc bot [low high]))
    {}
    (filter #(= (:op %) :transfer) instrs)))

(defn apply-transfer [[bot-state outputs] [t-type t-out] v]
  (case t-type
    :output [bot-state (assoc outputs t-out v)]
    :bot [(update bot-state t-out conj v) outputs]))

(defn distribute [bot-state0 transfers targets]
  (loop [bot-state bot-state0
         outputs {}
         bot-targets nil]
    ; should probably be a queue, but whatever, doesn't really matter
    ; for the size of the inputs given
    (let [bots-to-transfer-from (filter (fn [[bot vs]] (= (count vs) 2)) bot-state)]
      (if-let [[next-bot vs] (first bots-to-transfer-from)]
        (let [[t-low t-high] (transfers next-bot)
              [v-low v-high] (sort vs)
              [bot-state1 outputs1]
              (apply-transfer
                (apply-transfer
                  [(assoc bot-state next-bot []) outputs]
                  t-low
                  v-low)
                t-high
                v-high)
              bot-targets1 (if (= [v-low v-high] targets) next-bot bot-targets)]
          (recur bot-state1 outputs1 bot-targets1))
        [bot-state outputs bot-targets]))))

(defn run [instrs targets]
  (distribute (init-bot-state instrs) (init-transfers instrs) targets))

(defn solve-part1 [path targets]
  (let [instrs (->> path slurp parse)]
    (nth (run instrs targets) 2)))

(defn solve-part2 [path targets]
  (let [instrs (->> path slurp parse)
        [_ outputs _] (run instrs targets)]
    (apply * (map outputs [0 1 2]))))
