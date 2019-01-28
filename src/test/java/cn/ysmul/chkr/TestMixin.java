package cn.ysmul.chkr;

import cn.ysmul.type.CheckedValue;

import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.util.DefUtil.kv;
import static cn.ysmul.util.DefUtil.list;
import static cn.ysmul.util.ChkrUtil.parseError;

public class TestMixin {
    public static void main(String[] args) {
        Object testData = kv(
            "reports", list(
                kv(
                    "id", 1,
                    "title", "1",
                    "relatedDataModelIds", list(1, 2, 3)
                ),
                kv(
                    "id", 1,
                    "title", "1",
                    "relatedDataModelIds", list(1, 2, 3)
                )
            ),
            "a", "123",
            "b", kv(
                "b1", "123a",
                "b2", "false"
            )
        );

        CheckedValue after = Mixin(
            Obj(
                "reports", Arr(Obj(
                    "id", Num,
                    "title", Str,
                    "relatedDataModelIds", Arr(Num)
                ))
            ),
            Or(
                Obj(
                    "a", Num,
                    "b", Num
                ),
                Obj(
                    "a", Str,
                    "b", Obj(
                        "b1", Num,
                        "b2", Str
                    )
                ),
                Obj(
                    "a", Str,
                    "b", Obj(
                        "b1", Num,
                        "b2", Bool
                    )
                )
            )
        ).check(testData);

        System.out.println(parseError(after.getPath()));
    }
}
