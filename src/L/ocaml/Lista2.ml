2 + 2;;
4.5 *. 3.0;;
2 / 3;;
2.0 /. 3.0;;

let x = 10;;
print_int x;;
let y = ref 10;;
!y;;
y := 20;
!y;;

let power2 x = x * x;;
power2 4;;

let ratio ((x: float), (y: float)): float = x /. y;;
ratio(2.0, 4.0);;

let max x y = if x > y then x else y;;
max 1 (-5);;

let test1 x = if x >= 0 then true else false;;
test1 (-2 + 2);;

let test2 x = if x <= 0 then true else false;;
test2 (-2 + 3);;

let test3 x = if x mod 2 == 0 then true else false;;
test3 (-3);;

let func ((test: int -> bool), (x: int), (y: int), (z:int)): int =
  let list = List.filter test [x; y; z] in
  List.fold_left ( + ) 0 list;;

func(test1, 1, 2, 3);;
func(test2, 2, 4, 5);;
func(test3, 3, 1, 4);;

let friendNames = ["Damian"; "Dawid"; "Maja"; "Martyna"; "Gracjan"; "Borys"]

let addFriend name list = name :: list;;

addFriend "Philip" friendNames;;