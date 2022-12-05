(* ZAD 1 *)

module type Point_type = sig
  type point3D = FPoint of float point | IPoint of int point and 'a point = {x: 'a; y: 'a; z: 'a};;
  val distance : point3D -> point3D -> float;; 
end;;

module Point : Point_type =
struct
  type point3D = FPoint of float point | IPoint of int point and 'a point = {x: 'a; y: 'a; z: 'a};;
  let distance (a: point3D) (b: point3D) =
    match (a, b) with
    | (FPoint a, FPoint b) -> sqrt ((a.x-.b.x)**2.+.(a.y-.b.y)**2.+.(a.z-.b.z)**2.)
    | (IPoint a, IPoint b) -> sqrt ((float_of_int (a.x-b.x))**2.+.(float_of_int (a.y-b.y))**2.+.(float_of_int (a.z-b.z))**2.)
    | (FPoint a, IPoint b) -> sqrt ((a.x-.(float_of_int b.x))**2.+.(a.y-.(float_of_int b.y))**2.+.(a.z-.(float_of_int b.z))**2.)
    | (IPoint a, FPoint b) -> sqrt (((float_of_int a.x)-.b.x)**2.+.((float_of_int a.y)-.b.y)**2.+.((float_of_int a.z)-.b.z)**2.)
end;;

let point1 = Point.IPoint{x = 1; y = 1 ; z = 1};;
let point2 = Point.IPoint{x = 0; y = 0; z = 0};;
Point.distance point1 point2;;

(* ZAD 2 *)

module type Segment_type = sig
  open Point;;
  type segment = {a: point3D; b: point3D};;
  val length : segment -> float;;
end;;

module Segment : Segment_type =
struct
  open Point;;
  type segment = {a: point3D; b: point3D};;
  let length (s: segment) = distance s.a s.b;;
end;;

Segment.length (Segment.{a = point1; b = point2});;

(* ZAD 3 *)

module type BT_type = sig
  type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
  val add : 'a bt -> 'a -> 'a bt;;
  val remove : 'a bt -> 'a -> 'a bt;;
  val preorder : 'a bt -> 'a list;;
  val inorder : 'a bt -> 'a list;;
  val postorder : 'a bt -> 'a list;;
  val leafList : 'a bt -> 'a bt list;;
end;;

module BT : BT_type =
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
    let rec addHelper tt p =
      match tt with
      | Empty -> tt
      | Node (v, l, r) when v <> p -> Node (v, addHelper l p, addHelper r p)
      | Node (v, Empty, r) -> Node(v,Node(nv,Empty,Empty),r)
      | Node (v, l, _) -> Node(v,l,Node(nv,Empty,Empty)) in
      addHelper tt (findPlace [tt]);;
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
    let lastV = last tt in
    let rec removeHelper (tt: 'a bt) =
    match tt with
    | Empty -> tt
    | Node (value, l, r) -> if value = v then Node (lastV, removeHelper l, removeHelper r) else
      if value = lastV then Empty else Node (value, removeHelper l, removeHelper r) in
      removeHelper tt;;
  let rec preorder (tt: 'a bt) =
    match tt with
    | Empty -> []
    | Node (v, l, r) -> [v] @ preorder l @ preorder r;;
  let rec inorder (tt: 'a bt) = 
    match tt with
    | Empty -> []
    | Node (v, l, r) -> inorder l @ [v] @ inorder r;;
  let rec postorder (tt: 'a bt) = 
    match tt with
    | Empty -> []
    | Node (v, l, r) -> postorder l @ postorder r @ [v];;
  let rec leafList (tt: 'a bt) =
    match tt with
    | Empty -> []
    | Node (v, l, r) ->
        match (l, r) with
        | (Empty, Empty) -> [tt]
        | (Empty, r) -> leafList r
        | (l, Empty) -> leafList l
        | (l, r) -> leafList l@leafList r;;
end;;

let tt = BT.add (BT.add (BT.add (BT.add (BT.Node(1, Empty, Empty)) 2) 3) 4) 5;;
BT.preorder tt;;
BT.inorder tt;;
BT.leafList tt;;
BT.postorder tt;;
let tt = BT.remove tt 2;;
BT.postorder tt;;

(* module type BT_type = sig
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
BT.preorder tt;; *)

(* ZAD 4 *)

module type Comparable = sig
  type t
end;;

module Make_Point (Type : Comparable) = struct
  type t = Type.t;;
  let create (x: t) (y: t) (z: t) : t Point.point = Point.{x;y;z};;
end;;

module IntPoint = 
Make_Point(struct
  type t = int;;
end);;

let point = IntPoint.create 1 0 0;;