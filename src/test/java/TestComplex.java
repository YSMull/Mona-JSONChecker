import static chkr.C.*;
import static chkr.T.*;
import static util.DefUtil.kv;
import static util.DefUtil.list;
import static util.ObjChkrUtil.parseError;

public class TestComplex {

    public static void main(String[] args) {

        var object = kv(
            "a", kv(
                "b", kv(
                    "c", list(kv(
                        "d", kv(
                            "e", kv(
                                "f", list(1, "2", 3, 4)
                            )
                        )
                    ))
                )
            )
        );

        var chkr = Obj(
            "a", Obj(
                "b", Obj(
                    "c", Arr(Obj(
                        "d", Obj(
                            "e", Or(Bool, Str, Obj(
                                "f", Arr(StrictNum)
                            ))
                        )
                    ))
                )
            )
        );

        var after = chkr.check(object);

        System.out.println(parseError(list(after.getPath().get(0))));
        System.out.println(parseError(after.getPath()));

    }
}
