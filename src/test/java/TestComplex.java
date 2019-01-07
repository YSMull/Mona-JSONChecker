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
                                                "e", 123
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
                                                        "f", Num
                                                ))
                                        )
                                ))
                        )
                )
        );

        var after = chkr.check(object);

        System.out.println(parseError(after.getPath()));

    }
}
