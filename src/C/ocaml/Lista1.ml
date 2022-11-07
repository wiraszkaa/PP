let flatten1 xss = 
  let rec flattenHelper xss acc =
    match xss with
    | [] -> acc
    | h::tl -> flattenHelper tl (acc@h) in
    flattenHelper xss [];;
flatten1 [[1; 2; 3;]; [4; 5; 6]];;

let count x xs =
  let rec countHelper x xs n =
    match xs with
    | [] -> n
    | h::tl -> if h == x then countHelper x tl (n + 1) else countHelper x tl n in
    countHelper x xs 0;;
count 1 [1; 2; 1; 3; 5; 6];;

let replicate x n = 
  let rec fillHelper x n acc =
  if n == 0 then acc else fillHelper x (n - 1) (acc @ [x]) in
  fillHelper x n [];;
replicate "Hello World" 5;;

let rec replicate2 x n = if n <= 0 then [] else x::(replicate2 x (n - 1));;
replicate2 "Hello World" 1;;

let sqrList xs = 
  let rec sqrList xs acc =
    match xs with
    | [] -> acc
    | h::tl -> sqrList tl acc@[h * h] in
    sqrList xs [];;
sqrList [1; 2; 3; 4];;

let rec sqrList2 xs =
  match xs with
  | [] -> []
  | h :: tl -> (h * h) :: sqrList2 tl;;
sqrList2 [1; 2; 3; 4];; 

let reverse xs =
  let rec reverseHelper xs acc =
  match xs with
  | [] -> acc
  | h::tl -> reverseHelper tl (h::acc) in
  reverseHelper xs [];;
reverse [1; 2; 3; 4];;

let palindrome xs = reverse xs = xs;;
palindrome ["a"; "b"; "c"; "b"; "a"];;

let listLength xs =
  let rec lengthHelper xs n =
    match xs with
    | [] -> n
    | h::tl -> lengthHelper tl (n + 1) in
  lengthHelper xs 0;;
listLength [1; 2; 3; 4; 5];;

let rec listLength2 xs =
  match xs with
  | [] -> 0
  | h::tl -> listLength2(tl) + 1;;
  listLength2 [1; 2; 3; 4; 5];;