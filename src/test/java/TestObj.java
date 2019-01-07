import chkr.Chkr;
import type.CheckedValue;

import java.util.Map;

import static chkr.C.Obj;
import static chkr.C.Or;
import static chkr.T.*;
import static util.ObjChkrUtil.parseError;

public class TestObj {
    public static void main(String[] args) {

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
        CheckedValue after = c.check(before);

        System.out.println(parseError(after.getPath()));
    }
}
