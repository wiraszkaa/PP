type 'a llist = LNil | LCons of 'a * 'a llist Lazy.t;;

let rec lfrom k = LCons (k, lazy (lfrom (k+1)));;

let rec toLazyList = function
 [] -> LNil
 | x :: xs -> LCons(x, lazy (toLazyList xs));;

 let rec ltake = function
 (0, _) -> []
 | (_, LNil) -> []
 | (n, LCons(x, lazy xs)) -> x :: ltake(n-1, xs);;

 let rec lzip (lxs, lys) =
 match (lxs, lys) with
 (LCons(h1, lazy t1), LCons(h2, lazy t2)) -> LCons((h1, h2), lazy (lzip (t1, t2)))
 | _ -> LNil;;

 let rec lunzip plxs =
  match plxs with
  | LCons((h1, h2), lazy t) -> (LCons(h1, lazy (fst(lunzip t))), LCons(h2,lazy (snd(lunzip t))))
  | LNil -> (LNil, LNil);;

(* ZAD 1 *)

let lrepeat n (lxs: 'a llist) =
  let rec repeatHelper c (lxs: 'a llist) =
    match (c, lxs) with
    | (_, LNil) -> LNil
    | (0, LCons(_, lazy tl)) -> repeatHelper n tl
    | (c, LCons(h, lazy tl)) -> LCons(h, lazy (repeatHelper (c - 1) lxs)) in
    repeatHelper n lxs;;
ltake (10, lrepeat 3 (lfrom 0));;

(* ZAD 2 *)

let lfib =
  let rec fibHelper a b =
    LCons(a, lazy (fibHelper b (a + b))) in
    fibHelper 0 1;;
ltake (10, lfib);;

(* ZAD 3 *)

type 'a lBT = LEmpty | LNode of 'a * (unit ->'a lBT) * (unit -> 'a lBT);;

let ltt = LNode(1, 
            (fun () -> LNode(2,
                (fun () -> LNode(4,
                    (fun () -> LEmpty),
                    (fun () -> LEmpty))),
                (fun () -> LEmpty))), 
            (fun () -> LNode(3,
                (fun () -> LNode(5,
                    (fun () -> LEmpty),
                    (fun () -> LNode(6,
                        (fun () -> LEmpty),
                        (fun () -> LEmpty))))),
                (fun () -> LEmpty))));;

let lBreadth (ltt: 'a lBT) =
  let rec breadthHelper q =
    match q with
    | [] -> LNil
    | h::tl -> 
      match h with
      | LEmpty -> breadthHelper tl
      | LNode (v, l, r) -> LCons(v, lazy (breadthHelper (tl@[l(); r()]))) in
      breadthHelper [ltt];;
ltake (10, lBreadth ltt);;

let rec lTree n =
  LNode(n, (fun () -> lTree (2 * n)), (fun () -> lTree (2 * n + 1)));;
ltake (20, lBreadth (lTree 1));;