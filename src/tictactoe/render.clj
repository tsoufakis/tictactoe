(ns tictactoe.render
  (require [clojure.string :as string]))

(def alpha-start 65)
(def alpha-end 90)

(def col->alpha (comp str char (partial + alpha-start)))

(def alpha->col (comp #(- % alpha-start) int first))

(defn row-separator [len-row]
  ; extra two spaces for row numbers later
  (apply str (cons "  +" (repeatedly len-row (fn [] "---+")))))

(defn render-row
  ([row] (render-row row " "))
  ([row prefix]
    (str prefix " | " (string/join " | " (map (fn [cell] (if (nil? cell) " " cell)) row)) " |")))

(defn render-header [num-cols]
  (assert (< num-cols (- alpha-end alpha-start)))

  (def letters (map col->alpha (range num-cols)))

  (string/join " " (cons "  " (map #(str " " % " ") letters))))

(defn render-board [board]
  (def sep (row-separator (count board)))
  (def header (render-header (count (get board 0))))

  (def str-list 
    (loop [strings [header sep] rows board row-num 1]
      (if (empty? rows)
        strings
        (recur (into strings [(render-row (first rows) row-num) sep])
               (rest rows)
               (inc row-num)))))
  (string/join "\n" str-list))
