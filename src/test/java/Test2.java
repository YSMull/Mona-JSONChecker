import chkr.Chkr;
import type.CheckedValue;

import java.util.Arrays;
import java.util.Map;

import static chkr.C.Obj;
import static chkr.C.Or;
import static chkr.T.*;

public class Test2 {
    public static void main(String[] args) throws Exception {

        Object before = Map.of(
                "userId", 123,
                "userName", "haha",
                "phone", "aa",
                "address", Map.of(
                        "id", 123,
                        "person", Map.of(
                                "id", "1",
                                "age", 123,
                                "t", "a"
                        )
                )
        );

        Chkr c = Obj(
                "userId", Num,
                "userName", Str,
                "address", Obj(
                        "id", Num,
                        "person", Obj(
                                "id", Num,
                                "age", Num,
                                "t", Or(Bool, Num)
                        )
                )
        );
        CheckedValue a = c.check(before);

        System.out.println(Arrays.toString(a.getPath().toArray()));
    }
}
