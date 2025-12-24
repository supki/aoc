(ns aoc.test_helper
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.test :refer :all]))

(defn read-answer [path]
  (->> path
       slurp
       str/trim-newline))

(defn write-answer [path answer]
  (spit path (str answer "\n")))

(defn is-golden [path answer]
  (let [golden-file (io/file path)]
    (if
      (.exists golden-file)
      (let [golden-answer (read-answer golden-file)]
        (is (= (str answer) golden-answer)))
      (do
        (write-answer path answer)
        (is true)))))
