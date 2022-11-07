let log c = fun d -> (fun t -> ("[" ^ c ^ "] " ^ d ^ "\t" ^ t));;
let warn = log("Warn");;
let timeWarn = warn("Today");;
print_string (timeWarn("Hello"));;
    
let map xs f =
  let rec mapHelper xs f acc =
    match xs with
    | [] -> acc
    | h::tl -> mapHelper tl f (acc@[f h]) in
    mapHelper xs f [];;
let sqr x = x * x;;
map [1; 2; 3; 4; 5] sqr;;

let filter xs f =
  let rec filterHelper xs f acc =
    match xs with
    | [] -> acc
    | h::tl -> if f h then filterHelper tl f (acc@[h]) else filterHelper tl f acc in
    filterHelper xs f [];;
filter [1; 2; 3; 4; 5] (fun x -> x < 3);;

let reduce xs f acc =
  let rec reduceHelper xs f acc =
    match xs with
    | [] -> acc
    | h::tl -> reduceHelper tl f (f acc h) in
    reduceHelper xs f acc;;
reduce ["Hello"; " Damian"; ","; " Bye."] ( ^ ) "";;

let average xs =
  let rec averageHelper xs s l =
    match xs with
    | [] -> float_of_int(s) /. float_of_int(l)
    | h::tl -> averageHelper tl (s + h) (l + 1) in
    averageHelper xs 0 0;;
average [1; 2; -2; 2; 7];;

let short s =
  match (String.trim s) with
  | "" -> ""
  | s -> reduce (map (String.split_on_char ' ' s) (fun s -> String.sub s 0 1)) (^) "";;
short "Zakład Ubezpieczeń Społecznych";;

let sqrFilter xs = 
  let sum = reduce xs (+) 0 in
  map (filter xs (fun x -> x * x * x < sum)) (fun x -> x * x);;
sqrFilter [1; 2; 3; 4];;