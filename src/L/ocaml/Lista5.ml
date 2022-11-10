let point2D (x: float) (y: float) = (x, y);;
let point1 = point2D 1. 1.;;
let point2 = point2D 2. 2.;;
type point2Dr = {x: float; y: float};;
let pointr1 = {x = 1.; y = 1.};;
let pointr2 = {x = 2.; y = 2.};;

let distance a b = let (xa, ya) = a and (xb, yb) = b in sqrt((yb -. ya) ** 2. +. (xb -. xa) ** 2.);;
distance point1 point2;;
let distance a b = sqrt((b.y -. a.y) ** 2. +. (b.x -. a.x) ** 2.);;
distance pointr1 pointr2;;

type pointND = {xs: float list; n: int};;
let point4D1 = {xs = [1.; 1.; 1.; 1.]; n = 4};;
let point4D2 = {xs = [2.; 2.; 2.; 2.]; n = 4};;
let distance a b = if a.n <> b.n then (-1.) else
  let rec distanceHelper xsa xsb acc =
    match (xsa, xsb) with
    | ([], _::_) -> (-1.)
    | (_::_, []) -> (-1.)
    | ([], []) -> sqrt(acc)
    | (h1::tl1, h2::tl2) -> distanceHelper tl1 tl2 (acc +. (h2 -. h1) ** 2.) in
    distanceHelper a.xs b.xs 0.;;
distance point4D1 point4D2;;


let person (n: string) (s: string) (a: int) (g: string) (s: float) = (n, s, a, g, s);;
let xyz = person "Xyz" "Zyx" 20 "male" 42.5;;
let abc = person "Abc" "Cba" 25 "female" 40.;;
let partnership x y = (x, y);;
let partners = partnership xyz abc;;
let younger p = let ((_, _, a1, _, _), (_, _, a2, _, _)) = p in if a1 < a2 then fst p else snd p;;
younger partners;;

type person = {
  name: string; 
  surname: string; 
  age: int; 
  gender: string; 
  shoe: float
};;
let xyz = {
  name = "Xyz";
  surname = "Zyx";
  age = 20;
  gender = "male";
  shoe = 42.5;
};;
let abc = {
  name = "Abc";
  surname = "Cba";
  age = 25;
  gender = "female";
  shoe = 40.;
};;

type partnership = {first: person; second: person};;
let partners = {first = xyz; second = abc};;
partners.first = abc;;

let younger p = if p.first.age < p.second.age then p.first else p.second;;
younger partners;;


type weekDay = Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday;;
let pon = Monday;;

let weekDayToString d = 
  match d with
  | Monday -> "Poniedziałek"
  | Tuesday -> "Wtorek"
  | Wednesday -> "Środa"
  | Thursday -> "Czwartek"
  | Friday -> "Piątek"
  | Saturday -> "Sobota"
  | Sunday -> "Niedziela";;
weekDayToString pon;;

let nextDay d = 
  match d with
  | Monday -> Tuesday
  | Tuesday -> Wednesday
  | Wednesday -> Thursday
  | Thursday -> Friday
  | Friday -> Saturday
  | Saturday -> Sunday
  | Sunday -> Monday;;
nextDay pon;;

type 'a maybe = Just of 'a | Nothing;;
let safeHead xs =
  match xs with
  | [] -> Nothing
  | h::tl -> Just(h);;
safeHead [];;
safeHead [1; 2; 3; 4];;

type cuboid = { x: float; y: float; z: float };;
type cone = { r: float; h: float };;
type sphere = { r: float };;
type cylinder = { r: float; h: float };;
type 'a solidFigure = Cuboid of cuboid | Cone of cone | Sphere of sphere | Cylinder of cylinder;;

let volume f =
  match f with
  | Cuboid c -> c.x *. c.y *. c.z
  | Cone c -> (Float.pi *. c.r ** 2. *. c.h) /. 3.
  | Sphere b -> (4. *. Float.pi *. b.r ** 2.) /. 3.
  | Cylinder c -> Float.pi *. c.r ** 2. *. c.h;;

let cube = Cuboid({x = 1.; y = 1.; z = 1.});;
volume cube;;
let cone = Cone({r = 1.; h = 1.});;
volume cone;;
let sphere = Sphere({r = 1.});;
volume sphere;;
let cylinder = Cylinder({r = 1.; h = 1.});;
volume cylinder;;