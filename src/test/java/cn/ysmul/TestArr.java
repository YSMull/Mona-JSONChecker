package cn.ysmul;

import cn.ysmul.type.CheckedValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.util.ChkrUtil.parseError;

public class TestArr {
    public static void main(String[] args) {

        var data = Map.of(
            "a", Map.of(
                "b", List.of(1, 2, "a")
            )
        );

        var chkr = Obj(
            "a", Obj(
                "b", Arr(Num)
            )
        );

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            var after = chkr.check(data);
            long end = System.nanoTime();
            System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start));
//            System.out.println(parseError(after.getPath()));
        }
    }
}
