package cn.ysmul;

import cn.ysmul.chkr.core.Chkr;

import java.util.concurrent.TimeUnit;

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

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            var after = chkr.check(object);
            long end = System.nanoTime();
            System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start));
//            System.out.println(parseError(after.getPath()));
        }
//        System.out.println(parseError(list(after.getPath().get(0))));
//        System.out.println(parseError(after.getPath()));

    }
}
