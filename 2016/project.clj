(defproject aoc2025 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2025 in Clojure"
  :dependencies [[org.clojure/clojure "1.12.4"]
                 [instaparse "1.5.0"]]

  :profiles
  {:dev
   {:source-paths ["dev"]
    :dependencies [[org.clojure/tools.namespace "1.5.0"]]}})
