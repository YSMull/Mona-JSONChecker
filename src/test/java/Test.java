import type.CheckedValue;

import java.util.Arrays;

import static chkr.C.Or;
import static chkr.T.*;

public class Test {
    public static void main(String[] args) {
        Any.check("123");
        Any.check(null);
        Num.check(null);
        Num.check("a");
        Num.check(123);
        Num.check("123");
        Bool.check("true");
        Bool.check("mm");
        CheckedValue a = Or(Bool, Num).check("a");
        System.out.println(Arrays.toString(a.getPath().toArray()));
    }
}
