(defproject aoc2025 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2025 in Clojure"
  :dependencies [[org.clojure/clojure "1.12.4"]
                 [engelberg/data.union-find "1.0.0"]
                 [com.google.ortools/ortools-java "9.14.6206"]]

  :profiles
  {:dev
   {:source-paths ["dev"]
    :dependencies [[org.clojure/tools.namespace "1.5.0"]]}})
