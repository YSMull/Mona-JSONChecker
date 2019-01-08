package cn.ysmul;

import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.util.DefUtil.kv;
import static cn.ysmul.util.DefUtil.list;
import static cn.ysmul.util.ChkrUtil.parseError;

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
                            "e", Or(Obj("aa", Num), Obj("bb", Str))
                        )
                    ))
                )
            )
        );

        var after = chkr.check(object);

//        System.out.println(parseError(list(after.getPath().get(0))));
        System.out.println(parseError(after.getPath()));

    }
}
