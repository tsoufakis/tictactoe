(ns tictactoe.core
  (require [clojure.string :as string]
           [tictactoe.board :refer :all]
           [tictactoe.render :refer :all])
  (:gen-class))

(defn get-input
  ([] (get-input ""))

  ([default]
   (let [input (string/trim (read-line))]
     (if (empty? input)
       default
       input))))
       ;(string/lower-case input)))))

(defn parse-input [input]
  (try
    (let [[_ row alpha] (re-matches #"^(\d)([A-Z])$" input)]
      [(- (Integer/parseInt row) 1) (alpha->col alpha)])
    (catch Exception e)))

(def print-msg (comp println (partial str "> ")))

(def toggle-player (comp inc #(mod % 2)))

(defn play-round [config, board, player-to-move]
  (def player-name (get-in config [:names player-to-move]))
  (def player-marker (get-in config [:markers player-to-move]))

  (print-msg (format "%s, enter the row and column you'd like to mark (e.g., 1A)." player-name))

  (def player-input (get-input))
  (def parsed-input (parse-input player-input))

  (println)
  (if (nil? parsed-input)
    (do (print-msg "I didn't understand your move. Try again.")
        (play-round config board player-to-move))
    (do (print-msg (format "You entered %s" player-input))
        (if (square-occupied board parsed-input)
          (do (print-msg "This square is occupied. Pick another.")
              (play-round config board player-to-move))

          (do (def next-board (assoc-in board parsed-input player-marker))
              (println (render-board next-board))
              (if (winner? next-board player-marker)
                (print-msg (format "%s wins!" player-name))
                (play-round config
                            next-board
                            (toggle-player player-to-move))))))))


(defn start-game []
  (print-msg "Welcome to Tic Tac Toe!\n")
  (print-msg "What is your name?")

  (def player-name (get-input "Player 1"))

  (println)
  (print-msg (format "Hello %s! Here is a new board.\n" player-name))

  (def config {:names {1 player-name
                       2 "Player 2"}
               :markers {1 "x"
                         2 "o"}})

  (def board (create-new-board 3 3))
  (println (render-board board))
  (println)
  (play-round config board 1))

(defn -main
  [& args]
  (start-game))
