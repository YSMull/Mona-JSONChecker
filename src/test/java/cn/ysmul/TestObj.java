package cn.ysmul;

import cn.ysmul.chkr.core.Chkr;
import cn.ysmul.type.CheckedValue;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.util.ChkrUtil.parseError;

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
        long start = System.nanoTime();
        CheckedValue after = c.check(before);
        long end = System.nanoTime();

        System.out.println(TimeUnit.NANOSECONDS.toMillis(end - start));
        System.out.println(parseError(after.getPath()));
    }
}
