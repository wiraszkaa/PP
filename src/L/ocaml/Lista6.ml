let rec stirling n m =
  match (n, m) with
  | (_, 1) -> 1
  | (n, m) when n = m -> 1 
  | (n, m) -> stirling (n - 1) (m - 1) + n * stirling (n - 1) m;;

let rec memoized_stirling n m =
  let memory = Hashtbl.create 10 in
  match (n, m) with
  | (_, 1) -> 1
  | (n, m) when n = m -> 1 
  | (n, m) -> if Hashtbl.mem memory (n, m) then 
    Hashtbl.find memory (n, m) else 
      let result = memoized_stirling (n - 1) (m - 1) + n * memoized_stirling (n - 1) m in
      Hashtbl.add memory (n, m) result;
      result;;

stirling 10 2;;
memoized_stirling 10 2;;


let make_memoize f =
  let memory = Hashtbl.create 10 in
  let save x r = Hashtbl.add memory x r; r and
  get x = Hashtbl.find memory x and
  contains x = Hashtbl.mem memory x in 
  fun x -> if contains x then get x else save x (f x);;

let rec fib x =
  match x with
  | 0 -> 0
  | 1 -> 1
  | _ -> fib (x - 1) + fib (x - 2);;

let memoized_fib = make_memoize fib;;
memoized_fib 3;;


let lazy_s = lazy (stirling 200 7);;
print_string "Hello, It's working";;
print_int (Lazy.force lazy_s);;


type 'a sequence = Cons of 'a * (unit -> 'a sequence);;
let rec natural n = Cons (n, fun () -> natural (n + 1));;
let rec bell n k = Cons (stirling n k, fun () -> bell (n + 1) k);;

let stream_head (s: ('a sequence)): 'a =
  match s with
  | Cons (h, t) -> h;;
stream_head (bell 4 2);;
let stream_tail (s: ('a sequence)): (unit -> 'a sequence) =
  match s with
  | Cons (h, t) -> t;;
let thunk = stream_tail (bell 4 2);;
thunk ();;

let stream_list (s: 'a sequence) n =
  let rec listHelper s n acc =
    match n with
    | 0 -> acc
    | n -> let thunk = stream_tail s in listHelper (thunk()) (n - 1) (acc@[stream_head s]) in
    listHelper s n [];;
stream_list (bell 2 2) 5;;

let stream_odd_list (s: 'a sequence) n =
  let n = n * 2 in
  let rec listHelper s n acc =
    match n with
    | 0 -> acc
    | n -> let thunk = stream_tail s in if n mod 2 = 0 then 
      listHelper (thunk ()) (n - 1) (acc@[stream_head s]) else 
        listHelper (thunk ()) (n - 1) acc in
    listHelper s n [];;
stream_odd_list (bell 2 2) 5;;

let rec stream_pop (s: 'a sequence) n =
  match n with
  | 0 -> s
  | n -> let thunk = stream_tail s in stream_pop (thunk ()) (n - 1);;
stream_list (stream_pop (bell 2 2) 2) 3;;

let stream_combine (s1: 'a sequence) (s2: 'a sequence) n =
  let rec combineHelper (s1: 'a sequence) (s2: 'a sequence) n acc =
    match n with
    | 0 -> acc
    | n -> let t1 = stream_tail s1 and t2 = stream_tail s2 in 
    combineHelper (t1 ()) (t2 ()) (n - 1) (acc@[(stream_head s1, stream_head s2)]) in
    combineHelper s1 s2 n [];;
stream_combine (natural 1) (bell 2 2) 4;;