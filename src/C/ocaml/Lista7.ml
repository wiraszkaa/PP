(* ZAD 1 *)
(* a) *)

module type QUEUE_FUN = sig
  type 'a t
  exception Empty of string
  val empty: unit -> 'a t
  val enqueue: 'a * 'a t -> 'a t
  val dequeue: 'a t -> 'a t
  val first: 'a t -> 'a
  val isEmpty: 'a t -> bool
end;;

module QUEUE: QUEUE_FUN = struct
  type 'a t = 'a list;;
  exception Empty of string;;
  let empty () = [];;
  let isEmpty (q: 'a t) = if q = [] then true else false;;
  let rec first (q: 'a t) =
    match q with
    | [] -> raise (Empty "Queue is empty")
    | [x] -> x
    | h::tl -> first tl;;
  let enqueue (v, (q: 'a t)) = v::q;;
  let rec dequeue (q: 'a t) =
    match q with
    | [] -> []
    | [x] -> []
    | h::tl -> h::dequeue tl;;
end;;

let q = QUEUE.enqueue (2, (QUEUE.enqueue (1, QUEUE.empty())));;
QUEUE.first q;;
QUEUE.first (QUEUE.dequeue q);;
QUEUE.isEmpty (QUEUE.dequeue (QUEUE.dequeue q));;

(* b) *)

module QUEUE: QUEUE_FUN = struct
  type 'a t = 'a list * 'a list;;
  exception Empty of string;;
  let normalize (q: 'a t) =
    match q with
    | ([], y) -> (List.rev y, [])
    | q -> q;;
  let empty (): ('a t) = ([], []);;
  let isEmpty ((x, y): 'a t) = if (x = []) then true else false;;
  let rec first ((x, _): 'a t) =
    match x with
    | [] -> raise (Empty "Queue is empty")
    | h::tl -> h;;
  let enqueue (v, ((x, y): 'a t)): ('a t) = normalize (x, v::y);;
  let rec dequeue ((x, y): 'a t) = normalize (List.tl x, y);;
end;;

let q = QUEUE.enqueue (2, (QUEUE.enqueue (1, QUEUE.empty())));;
QUEUE.first q;;
QUEUE.first (QUEUE.dequeue q);;
QUEUE.isEmpty (QUEUE.dequeue (QUEUE.dequeue q));;

(* ZAD 2 *)

module type QUEUE_MUT =
sig
 type 'a t
 (* The type of queues containing elements of type ['a]. *)
 exception Empty of string
 (* Raised when [first q] is applied to an empty queue [q]. *)
 exception Full of string
 (* Raised when [enqueue(x,q)] is applied to a full queue [q]. *)
 val empty: int -> 'a t
 (* [empty n] returns a new queue of length [n], initially empty. *)
 val enqueue: 'a * 'a t -> unit
 (* [enqueue (x,q)] adds the element [x] at the end of a queue [q]. *)
 val dequeue: 'a t -> unit
 (* [dequeue q] removes the first element in queue [q] *)
 val first: 'a t -> 'a
 (* [first q] returns the first element in queue [q] without removing
 it from the queue, or raises [Empty] if the queue is empty. *)
 val isEmpty: 'a t -> bool
 (* [isEmpty q] returns [true] if queue [q] is empty,
 otherwise returns [false]. *)
 val isFull: 'a t -> bool
 (* [isFull q] returns [true] if queue [q] is full,
 otherwise returns [false]. *)
end;;

module QUEUE: QUEUE_MUT = struct
  type 'a t = {mutable f: int; mutable r: int; mutable l: int; mutable arr: 'a option array };;
  exception Empty of string;;
  exception Full of string;;
  let empty l = {f=0; r=0; l; arr=(Array.make l None)};;
  let isEmpty q = if q.arr.(q.f) = None then true else false;;
  let isFull q = if (q.f = q.r && q.arr.(q.f) != None) then true else false;;
  let enqueue (v, q) = if isFull q then raise (Full "Queue is full") else
    q.arr.(q.r) <- (Some v); q.r <- (q.r + 1) mod q.l;;
  let dequeue q = q.arr.(q.f) <- None; q.f <- (q.f + 1) mod q.l;;
  let first q = 
    match q.arr.(q.f) with
    | Some v -> v
    | None -> raise (Empty "Queue is empty");;
end;;

let q = QUEUE.empty 4;;
QUEUE.enqueue (1, q);;
QUEUE.enqueue (2, q);;
QUEUE.enqueue (3, q);;
QUEUE.enqueue (4, q);;
QUEUE.isFull q;;
QUEUE.enqueue (5, q);;
QUEUE.first q;;
QUEUE.dequeue q;;
QUEUE.first q;;
QUEUE.dequeue q;;
QUEUE.dequeue q;;
QUEUE.dequeue q;;
QUEUE.isEmpty q;;
QUEUE.first q;;