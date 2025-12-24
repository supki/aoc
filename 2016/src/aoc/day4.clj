(ns aoc.day4
  (:require [clojure.string :as str]
            [instaparse.core :as p]))

; > This implementation is functional but suffers from over-engineering in the parsing phase.

(def grammar
  "
  <S> = result
  result = name <'-'> id <'['> checksum <']'>
  name = part (<'-'> part)*
  part = #'[a-z]+'
  id = #'[0-9]+'
  checksum = #'[a-z]+'
  ")

(def parser
  (p/parser grammar))

(def parse-transform
  {:result (fn [name id checksum] {:name name :sector-id id :checksum checksum})
   :name (fn [& chunks] (apply str (concat chunks)))
   :part (fn [x] x)
   :id parse-long
   :checksum (fn [x] x)})

(defn parse-room [s]
  (->> (p/parse parser s)
       (p/transform parse-transform)
       first))

(defn parse [s]
  (map parse-room (str/split-lines s)))

(defn compute-checksum [name]
  (->> name
       frequencies
       (sort-by (fn [[c freq]] [(- freq) c]))
       (take 5)
       (map first)
       (apply str)))

(defn real? [{:keys [name checksum]}]
  (= checksum (compute-checksum name)))

(defn shift-char [by c]
  (let [base (int \a)]
    (char (+ (mod (+ (- (int c) base) by) 26) base))))

(defn decrypt-name [{:keys [name sector-id]}]
  (apply str (map (partial shift-char sector-id) name)))

(defn solve-part1 [path]
  (transduce (comp (filter real?) (map :sector-id))
             +
             (parse (slurp path))))

(defn solve-part2 [path]
  (->> (slurp path)
       parse
       (some
         (fn [{:keys [sector-id] :as room}]
           (when (= "northpoleobjectstorage" (decrypt-name room))
             sector-id)))))
