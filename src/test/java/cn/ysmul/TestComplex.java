package cn.ysmul;

import cn.ysmul.chkr.core.Chkr;

import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.util.DefUtil.kv;
import static cn.ysmul.util.DefUtil.list;
import static cn.ysmul.util.ChkrUtil.parseError;

public class TestComplex {

    public static void main(String[] args) {

        var object = kv(
            "a", kv(
                "b", list(kv(
                    "c", kv(
                        "d", kv(
                            "e", "2"
                        )
                    )
                ))
            )
        );
        Chkr NumOne = Chkr.judge(v -> v == Integer.valueOf(1), Any, "is not One");
        var chkr = Obj(
            "a", Obj(
                "b", Arr(Obj(
                    "c", Obj(
                        "d", Or(
                            Obj("e", Or(StrictBool, Bool)),
                            Obj("e", Or(NumOne, StrictNum))
                        )
                    )
                ))
            )
        );

        var after = chkr.check(object);

//        System.out.println(parseError(list(after.getPath().get(0))));
        System.out.println(parseError(after.getPath()));

    }
}
