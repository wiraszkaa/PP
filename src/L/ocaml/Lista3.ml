let rec last xs = 
    match xs with
    | [] -> None
    | [x] -> Some x
    | h::tl -> last tl;;
last [1; 2; 3; 4; 5];;

let rec lastTwo xs =
  match xs with
  | [] -> None
  | [x] -> None
  | [x; y] -> Some (x, y)
  | h::tl -> lastTwo tl;;
lastTwo [1; 2; 3; 4; 5];;

let length xs =
  let rec lengthHelper xs n =
    match xs with
    | [] -> n
    | h::tl -> lengthHelper tl (n + 1) in
  lengthHelper xs 0;;
length [1; 2; 3; 4; 5];;

let reverse xs =
  let rec reverseHelper xs acc =
  match xs with
  | [] -> acc
  | h::tl -> reverseHelper tl (h::acc) in
  reverseHelper xs [];;
reverse [1; 2; 3; 4];;

let palindrome xs = reverse xs = xs;;
palindrome ["a"; "b"; "c"; "b"; "a"];;

let rec contains xs x =
  match xs with
  | [] -> false
  | h::tl -> if h == x then true else contains tl x;;
contains [1; 2;] 1;;

let distinct xs =
  let rec distinctHelper xss acc =
    match xss with
    | [] -> acc
    | h::tl -> if contains acc h then distinctHelper tl acc else distinctHelper tl (acc@[h]) in
    distinctHelper xs [];;
distinct [1; 1; 2; 3; 4; 3];;

let evenIndex xs =
  let rec evenHelper xss acc n =
    match xss with
    | [] -> acc
    | h::tl -> if n mod 2 == 0 then evenHelper tl (acc@[h]) (n + 1) else evenHelper tl acc (n + 1) in
    evenHelper xs [] 0;;
evenIndex [0; 1; 2; 3; 4; 5];;

let isPrime x =
  match x with
  | 0 -> false
  | 1 -> false
  | _ -> let rec primeHelper n i = 
    match i with
    | 1 -> true
    | _ -> if n mod i == 0 then false else primeHelper n (i - 1) in
    primeHelper x (x - 1);;
isPrime 3;;