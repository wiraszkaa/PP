(* 1, bo rekurencja wzajemna optymalizowana przez OCaml *)

let rec fib x =
  match x with
  | 0 -> 0
  | 1 -> 1
  | _ -> fib (x - 1) + fib (x - 2);;
fib 42;; 

let fibTail x =
  let rec fibHelper x a b =
    match x with
    | 0 -> a
    | 1 -> b
    | _ -> fibHelper (x - 1) b (a + b) in
    fibHelper x 0 1;;
fibTail 42;;

let epsilon = 1.0e-15
let root3 x =
  let rec rootHelper (x, a) = if abs_float(x ** 3. -. a) <= epsilon *. abs_float(x) then x
  else rootHelper(x +. (a /. (x ** 2.) -. x) /. 3.0, a) in
  if x > 1.0 then rootHelper (x /. 3.0, x) else rootHelper (x, x);;
root3 27.0;;

(* let [_ ; _ ; x ; _ ; _] = [-2; -1; 0; 1; 2];; *)
(* let [_; (x, _)] = [(1, 2); (0, 1)];; *)

let rec initSegment (x, y) =
  match (x, y) with
  | ([], _) -> true
  | (_, []) -> false
  | (h1::tl1, h2::tl2) -> if h1 = h2 then initSegment (tl1, tl2) else false;;
initSegment ([1; 2; 4], [1; 2; 4; 4; 5]);;

let replaceNth (xs, n, x) =
  let rec replaceHelper (xs, n, x, acc) =
    match n with
    | 0 -> acc@[x]@(List.tl xs)
    | n -> replaceHelper (List.tl xs, n - 1, x, acc@[List.hd xs]) in
    replaceHelper (xs, n, x, []);;
replaceNth ([1; 2; 3; 4], 3, 0);;