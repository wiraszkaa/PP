let f1 x = x 2 2;; 
let f2 x y z = x ( y ^ z );;

let curry3 f x y z = f (x, y, z);;
let curry23 = fun f -> fun x -> fun y -> fun z -> f (x, y, z);;
let uncurry3 f (x, y, z) = f x y z;;
let uncurry23 = fun f -> fun x -> fun y -> fun z -> f x y z;;

let sumProd xs = List.fold_left (fun (s, p) x -> (s + x, p * x)) (0, 1) xs;;
sumProd [1; 2; 3; 4; 5];;

(* a) najmniejsza liczba na początku listy; b) te same liczby nie będą uwzględnione *)

let insertionsort f xs = 
  let rec insert x xs =
    match xs with
    | [] -> [x]
    | h::tl -> if f x h then x::h::tl else h::insert x tl
  in List.fold_left (fun acc x -> insert x acc) [] xs;;
insertionsort (fun x y -> x <= y) [1; 3; 2; 5; 4; 0; 0];;

let rec mergesort f xs =
  let rec merge xs1 xs2 f =
    match (xs1, xs2) with
    | ([], _) -> xs2
    | (_, []) -> xs1
    | (h1::tl1, h2::tl2) -> if f h1 h2 then h1::(merge tl1 xs2 f) else h2::(merge xs1 tl2 f) and
    split l r m =
    match m with
    | 0 -> (List.rev l, r)
    | m -> split (List.hd r :: l) (List.tl r) (m - 1) in
        match xs with
        | [] -> []
        | [x] -> [x]
        | xs -> let (l, r) = split [] xs (List.length xs / 2) in merge (mergesort f l) (mergesort f r) f;;
mergesort (fun x y -> x <= y) [1; 3; 2; 5; 4; 0; 0];;