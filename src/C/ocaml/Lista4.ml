(* ZAD 1 *)
let f1 x y z = x y z;; 
let f2 x y = function z -> x::y;;

(* ZAD 2 *)
let rec f x = f x;;

(* ZAD 3 *)
type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;

let tt = Node(1,
            Node(2,
              Node(4,
                Empty,
                Empty),
              Empty),
            Node(3,
              Node(5,
              Empty,
              Node(6,
                Empty,
                Empty)),
              Empty));;

let breadthBT (tt: 'a bt) =
  let rec breadthHelper q acc =
    match q with
    | [] -> acc
    | h::tl -> 
      match h with
      | Empty -> breadthHelper tl acc
      | Node (v, l, r) -> breadthHelper (tl@[l; r]) (v::acc) in
      List.rev (breadthHelper [tt] []);;
breadthBT tt;;

let breadth (tt: 'a bt) =
  let rec search queue =
    match queue with
    | [] -> []
    | h::tl -> 
      match h with
      | Node(v, l, r) -> v::search (tl@[l; r])
      | Empty -> search tl in
      search [tt];;
breadth tt;;

(* ZAD 4 *)

let inPath (tt: 'a bt) =
  let rec pathHelper tt n =
    match tt with
    | Empty -> 0
    | Node (_, l, r) -> n + pathHelper l (n + 1) + pathHelper r (n + 1) in
    pathHelper tt 0;;
inPath tt;; 

let outPath (tt: 'a bt) =
  let rec pathHelper tt n =
    match tt with
    | Empty -> n
    | Node (_, l, r) -> pathHelper l (n + 1) + pathHelper r (n + 1) in
    pathHelper tt 0;;
outPath tt;;

(* ZAD 5 *)
type 'a graph = Graph of ('a -> 'a list);;

let g = Graph
(function
0 -> [3]
| 1 -> [0;2;4]
| 2 -> [1]
| 3 -> []
| 4 -> [0;2]
| n -> failwith ("Graph g: node "^string_of_int n^" doesn't exist")
);;

let depthSearch (Graph g) v =
  let rec searchHelper q acc =
    match q with
    | [] -> acc
    | h::tl -> if List.mem h acc then searchHelper tl acc else searchHelper ((g h)@q) (h::acc) in
      List.rev (searchHelper [v] []);;
depthSearch g 4;;