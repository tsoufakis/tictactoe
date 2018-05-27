(ns tictactoe.core-test
  (require [clojure.test :refer :all]
           [tictactoe.core :refer :all]
           [tictactoe.render :refer :all]
           [tictactoe.board :refer :all]))

(deftest new-board
  (is (= [[nil nil nil] [nil nil nil] [nil nil nil]]
         (create-new-board 3 3))))

(deftest iterating-lines
  (def my-board [[1 2 3] [4 5 6] [7 8 9]])
  (is (= (iter-lines my-board)
         [[1 2 3] [4 5 6] [7 8 9] ; rows
          [1 4 7] [2 5 8] [3 6 9] ; cols
          [1 5 9] [7 5 3]])) ; diagonals

  (is (= (iter-cols my-board) [[1 4 7] [2 5 8] [3 6 9]]))
  (is (= (iter-diagonals my-board) [[1 5 9] [7 5 3]])))

(deftest winning
  (is (= true (winner? [["x" nil nil] [nil "x" "o"] ["o" "o" "x"]] "x")))
  (is (= true (winner? [["x" nil nil] ["x" "x" "x"] ["o" "o" "x"]] "x")))
  (is (= (not (winner? [["x" nil nil] [nil "x" "o"] ["o" "x" "nil"]]
                       "x")))))
(deftest test-render
  (def my-board [["x"]])
  (is (= (render-board my-board)
         "    A \n  +---+\n1 | x |\n  +---+")))

(deftest test-header
  (is (= (render-header 4) "    A   B   C   D ")))

(deftest test-parse-input
  (is (= [0 1] (parse-input "1B")))
  (is (nil? (parse-input "1slj"))))
