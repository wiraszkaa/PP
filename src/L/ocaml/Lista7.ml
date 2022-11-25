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

(* module type BT_type = sig
  type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
  val add : 'a bt -> 'a -> 'a bt;;
  val remove : 'a bt -> 'a -> 'a bt;;
  val list : 'a bt -> 'a list;;
end;; *)

(* module BT : BT_type =
struct
  type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
  (* let rec add (tt: 'a bt) v =
    match tt with
    | Empty -> Node(v, Empty, Empty)
    | Node (value, l, r) -> if v > value then Node (value, l, add r v) else Node (value, add l v, r);; *)
  let add (tt: 'a bt) nv =
    let rec findPlace q =
      match q with
      | [] -> failwith "Error"
      | h::tl ->
        match h with
        | Node(v, Empty, _) -> v
        | Node(v, _, Empty) -> v
        | Node(_, l, r) -> findPlace (tl@[l; r])
        | Empty -> findPlace tl in
        let rec addHelper tt p nv =
          match tt with
          | Empty -> tt
          | Node (v, l, r) when v <> p -> Node (v, addHelper l p nv, addHelper r p nv)
          | Node (v, Empty, r) -> Node(v,Node(nv,Empty,Empty),r)
          | Node (v, l, _) -> Node(v,l,Node(nv,Empty,Empty)) in
          addHelper tt (findPlace [tt]) nv;;
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
end;; *)

module type BT_type = sig
  type 'a bt = Empty | Node of 'a node and 'a node = { mutable value: 'a; mutable parent: 'a bt;  mutable left: 'a bt; mutable right: 'a bt };;
  val add : 'a bt -> 'a -> unit;;
  val remove : 'a bt -> 'a -> unit;;
  val preorder : 'a bt -> 'a list;;
  val inorder : 'a bt -> 'a list;;
  val postorder : 'a bt -> 'a list;;
  val leafList : 'a bt -> 'a bt list;;
end;;

module BT : BT_type =
struct
type 'a bt = Empty | Node of 'a node and 'a node = { mutable value: 'a; mutable parent: 'a bt; mutable left:  'a bt; mutable right: 'a bt }
  let add (tt: 'a bt) nv =
    let rec findPlace q =
      match q with
      | [] -> failwith "Error"
      | h::tl ->
        match h with
        | Empty -> findPlace tl
        | Node {value; left; right} ->
          match (left, right) with
          | (Empty, _) -> value
          | (_, Empty) -> value
          | (left, right) -> findPlace (tl@[left; right]) and
      addNode (Node n) tt v b =
      if b then n.left <- Node{value=v; parent=tt; left=Empty; right=Empty} else 
        n.right <- Node{value=v; parent=tt; left=Empty; right=Empty} in
      let rec addHelper (tt: 'a bt) p =
        match tt with
        | Empty -> ()
        | Node {value; left; right} when value <> p -> addHelper left p; addHelper right p
        | Node {value; left; right} ->
          match (left, right) with
          | (Empty, _) -> addNode tt tt nv true
          | (_, Empty) -> addNode tt tt nv false
          | (left, right) -> () in
          addHelper tt (findPlace [tt]);;
  let set (tt: 'a bt) o n =
    let setValue (Node n) v =
      n.value <- v in
    let rec setHelper (tt: 'a bt) =
      match tt with
      | Empty -> ()
      | Node {value; left; right} ->
        if value = o then setValue tt n else setHelper left; setHelper right in
    setHelper tt;;
  let last (tt: 'a bt) =
    let rec lastHelper q acc =
      match q with
      | [] -> acc
      | h::tl ->
        match h with
        | Empty -> lastHelper tl h
        | Node{value; left; right} ->
          match (left, right) with
          | (Empty, Empty) -> lastHelper tl h
          | (left, Empty) -> lastHelper (tl@[left]) acc
          | (Empty, right) -> lastHelper (tl@[right]) acc
          | (left, right) -> lastHelper (tl@[left; right]) acc in
          lastHelper [tt] tt;;
  let rec preorder (tt: 'a bt) =
    match tt with
    | Empty -> []
    | Node {value; left; right} -> [value] @ preorder left @ preorder right;;
  let rec inorder (tt: 'a bt) = 
    match tt with
    | Empty -> []
    | Node {value; left; right} -> inorder left @ [value] @ inorder right;;
  let rec postorder (tt: 'a bt) = 
    match tt with
    | Empty -> []
    | Node {value; left; right} -> postorder left @ postorder right @ [value];;
  (* let leafList (tt: 'a bt) =
    let rec leafHelper q acc =
      match q with
      | [] -> List.rev acc
      | h::tl ->
        match h with
        | Empty -> leafHelper tl acc
        | Node{value; left; right} ->
          match (left, right) with
          | (Empty, Empty) -> leafHelper tl (h::acc)
          | (left, right) -> leafHelper (tl@[left; right]) acc in
          leafHelper [tt] [];; *)
  let rec leafList (tt: 'a bt) =
    match tt with
    | Empty -> []
    | Node {value; left; right} ->
      match (left, right) with
      | (Empty, Empty) -> [tt]
      | (Empty, right) -> leafList right
      | (left, Empty) -> leafList left
      | (left, right) -> leafList left@leafList right;;
  let remove (tt: 'a bt) v =
    let getValue (Node n) =
      n.value in
    let deleteChild (Node n) v =
      if (getValue n.left) = v then n.left <- Empty else n.right <- Empty in
    match last tt with
    | Empty -> ()
    | Node {value; parent} ->
    set tt v value; 
    deleteChild parent value;;
end;;

let tt = BT.Node{value=1; parent=Empty; left=Empty; right=Empty};;
BT.add tt 2;;
BT.add tt 3;;
BT.add tt 4;;
BT.add tt 5;;
BT.inorder tt;;
BT.postorder tt;;
BT.leafList tt;;
BT.preorder tt;;
BT.remove tt 2;;
BT.preorder tt;;
