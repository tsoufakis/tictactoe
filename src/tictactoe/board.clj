(ns tictactoe.board)

(def vec-repeat (comp vec repeat))

(defn create-new-board [nrows ncols]
  (vec-repeat nrows (vec-repeat ncols nil)))

(def square-occupied (comp not nil? get-in))

(defn iter-cols [board]
  (for [column-index (range (count board))]
    (map #(% column-index) board)))

(defn iter-diagonals
  ([board]
   (let [row-order (range (count board))]
    [(iter-diagonals board row-order)
     (iter-diagonals board (reverse row-order))]))

  ([board row-order]
    (map #(get-in board [%1 %2]) row-order (range (count board)))))

(defn iter-lines [board]
  (concat board ; iter-rows
          (iter-cols board)
          (iter-diagonals board)))

(defn winner?
  [board, marker]
  (some
    (fn [line] (every? #(= % marker) line))
    (iter-lines board)))
