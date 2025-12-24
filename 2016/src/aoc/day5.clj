(ns aoc.day5
  (:require [clojure.string :as str])
  (:import [java.security MessageDigest]
           [java.util Arrays]))


(defn md5 [prefix-bytes n]
  ; can't extract md5 here, in the clown world hashing
  ; strings is a mutable operation and also thread-unsafe
  (let [md5 (MessageDigest/getInstance "MD5")
        _ (.update md5 prefix-bytes)
        _ (.update md5 (.getBytes (str n) "UTF-8"))
        raw-bytes (.digest md5)
        b0 (bit-and (aget raw-bytes 0) 0xff)
        b1 (bit-and (aget raw-bytes 1) 0xff)
        b2 (bit-and (aget raw-bytes 2) 0xff)
        b3 (bit-and (aget raw-bytes 3) 0xff)
        first-five-zero? (zero? (bit-or b0 b1 (bit-shift-right b2 4)))
        sixth (bit-and b2 0x0f)
        seventh (bit-and (bit-shift-right b3 4) 0x0f)]
    [first-five-zero? sixth seventh]))

(defn solve-part1 [s]
  (let [prefix-bytes (.getBytes s "UTF-8")
        trans-f (comp
                  (map #(md5 prefix-bytes (inc %)))
                  (filter (fn [[z? _ _]] z?))
                  (map (fn [[_ sixth _]] (Integer/toHexString sixth)))
                  (take 8))]
    (apply str (sequence trans-f (range)))))

(defn fill-password [xs0]
  (let [password-length 8]
    (loop [password (vec (repeat password-length :hole))
           holes password-length
           xs xs0]
      (if (zero? holes)
        password
        (let [[[_ sixth seventh] & rest-of-xs] xs]
          (if (and (< sixth password-length) (= (password sixth) :hole))
            (recur (assoc password sixth seventh) (dec holes) rest-of-xs)
            (recur password holes rest-of-xs)))))))

(defn solve-part2 [s]
  (let [prefix-bytes (.getBytes s "UTF-8")]
    (->> (range)
         (map #(md5 prefix-bytes (inc %)))
         (filter (fn [[z? _ _]] z?))
         fill-password
         (map #(Integer/toHexString %))
         (apply str))))
