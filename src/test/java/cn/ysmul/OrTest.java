package cn.ysmul;

import cn.ysmul.chkr.core.Chkr;

import java.util.Map;

import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.util.ChkrUtil.parseError;

public class OrTest {
    public static void main(String[] args) {
        var data = Map.of(
            "a", Map.of(
                "b", "a"
            )
        );
        Chkr StrOne = Chkr.judge(v -> v == "1", Any, "is not One");
        var chkr = Mixin(
            Obj(
                "a", Obj(
                    "b", Or(Or(Or(Or(StrictNum, Or(StrictBool, Obj("c",StrictNum)))), Or(StrOne), Or(Or(Num, Bool))))
                )
            )
        );

        var after = chkr.check(data);
        System.out.println(parseError(after.getPath()));
    }
}
