package types

object Types {
  def main(args: Array[String]): Unit = {
    sealed trait Nat {
      type Add[N <: Nat] <: Nat
      type Multi[N <: Nat] <: Nat
    }

    trait _0 extends Nat {
      type Add[N <: Nat] = N
      type Multi[N <: Nat] = _0
    }

    trait Inc[N <: Nat] extends Nat {
      type Add[N2 <: Nat] = Inc[N#Add[N2]]
      type Multi[N2 <: Nat] = N2#Add[N#Multi[N2]]
    }

    type _1 = Inc[_0]
    type _2 = Inc[_1]
    type _3 = Inc[_2]
    type _4 = Inc[_3]
    type _5 = Inc[_4]
    type _6 = Inc[_5]
    type _7 = Inc[_6]
    type _8 = Inc[_7]
    type _9 = Inc[_8]

    implicitly[_2#Add[_3] =:= _5]
    implicitly[_2#Multi[_3] =:= _6]
  }
}


