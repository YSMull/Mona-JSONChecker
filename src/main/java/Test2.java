import chkr.Chkr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static chkr.C.Obj;
import static chkr.C.Or;
import static chkr.T.*;

public class Test2 {
    public static void main(String[] args) throws Exception {
        List<String> msg2 = new ArrayList<>();

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

        // address.person.t is not Bool
        // address.person.t is not Num

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
        c.check(before, msg2);

        System.out.println(Arrays.toString(msg2.toArray()));
    }
}
