import type.CheckedValue;

import static chkr.C.*;
import static chkr.T.*;
import static util.DefUtil.kv;
import static util.DefUtil.list;
import static util.ObjChkrUtil.parseError;

public class Test4 {
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
                        "b1", "123",
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
                                        "b1", StrictNum,
                                        "b2", Str
                                )
                        ),
                        Obj(
                                "a", Str,
                                "b", Obj(
                                        "b1", Num,
                                        "b2", StrictBool
                                )
                        )
                )
        ).check(testData);

        parseError(after.getPath());
    }
}
