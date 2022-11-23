(* ZAD 1 *)

module type Point_type = sig
  type 'a point3D = {x: 'a; y: 'a; z: 'a};;
  val distance : float point3D -> float point3D -> float;; 
end;;

module Point : Point_type = 
struct
  type 'a point3D = {x: 'a; y: 'a; z: 'a};;
  let distance (a: float point3D) (b: float point3D) =
    sqrt ((a.x-.b.x)**2.+.(a.y-.b.y)**2.+.(a.z-.b.z)**2.);;
end;;

let point1 = Point.{x = 1.; y = 1. ; z = 1.};;
let point2 = Point.{x = 0.; y = 0.; z = 0.};;
Point.distance point1 point2;;

(* ZAD 2 *)

module type Segment_type = sig
  open Point;;
  type segment = {a: float point3D; b: float point3D};;
  val length : segment -> float;;
end;;

module Segment : Segment_type =
struct
  open Point;;
  type segment = {a: float point3D; b: float point3D};;
  let length (s: segment) = distance s.a s.b;;
end;;

Segment.length Segment.{a = point1; b = point2};;

(* ZAD 3 *)

module type BT_type = sig
  type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
  val add : 'a bt -> 'a -> 'a bt;;
  val remove : 'a bt -> 'a -> 'a bt;;
  val list : 'a bt -> 'a list;;
end;;

module BT : BT_type =
struct
  type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
  let rec add (tt: 'a bt) v =
    match tt with
    | Empty -> tt
    | Node (value, l, r) ->
      match (l, r) with
      | (Empty, _) -> Node (value, Node(v, Empty, Empty), r)
      | (_, Empty) -> Node (value, l, Node(v, Empty, Empty))
      | (l, r) -> Node (value, add l v, add r v);;
  let list (tt: 'a bt) =
    let rec listHelper q acc =
      match q with
      | [] -> List.rev acc
      | h::tl->
        match h with
        | Empty -> listHelper tl acc 
        | Node (v, l, r) -> listHelper (tl@[l; r]) (v::acc) in
      listHelper [tt] [];;
  let last (tt: 'a bt) =
    let rec lastHelper q acc =
      match q with
      | [] -> acc
      | h::tl ->
        match h with
        | Node(v, Empty, Empty) -> lastHelper tl v
        | Node(_, l, r) -> lastHelper (tl@[l; r]) acc
        | Empty -> lastHelper tl acc in
    match tt with
    | Empty -> failwith ("Empty tree")
    | Node (v, _, _) -> lastHelper [tt] v;;
  let rec set (tt: 'a bt) o n =
    match tt with
    | Empty -> tt
    | Node (v, l, r) when v <> o -> Node (v, set l o n, set r o n)
    | Node (v, l, r) -> Node (n, l, r);;
  let remove (tt: 'a bt) v =
    let rec removeHelper (n: 'a bt) v =
    match n with
    | Empty -> n
    | Node (value, l, r) when value <> v -> Node (value, removeHelper l v, removeHelper r v)
    | Node (value, l, r) ->
      match (l, r) with
      | (Empty, _) -> r
      | (_, Empty) -> l
      | (l, r) -> let lastVal = (last tt) in set (removeHelper tt lastVal) v lastVal in
      removeHelper tt v;;
end;;

let tt = BT.add (BT.add (BT.add (BT.Node(1, BT.Empty, BT.Empty)) 2) 3) 4;;
BT.list tt;;
let tt = BT.remove tt 1;;
BT.list tt;;