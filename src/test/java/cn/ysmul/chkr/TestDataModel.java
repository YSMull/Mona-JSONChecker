package cn.ysmul.chkr;

import cn.ysmul.chkr.core.Chkr;
import cn.ysmul.type.CheckedValue;

import java.util.List;
import java.util.Map;

import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.util.ChkrUtil.parseError;

public class TestDataModel {
    public static void main(String[] args) {

        Object data = Map.of(
            "reports", List.of(
                Map.of(
                    "id", 1,
                    "title", "title",
                    "relatedDataModelIds", List.of(1, 2, 3)
                )
            ),
            "dataModels", List.of(
                Map.of(
                    "status", "extract",
                    "relatedDataConnectionIds", List.of(1, 2, "a")
                )
            )
        );

        Chkr checker = Mixin(
            Obj(
                "reports", Arr(Obj(
                    "id", Num,
                    "title", Str,
                    "relatedDataModelIds", Arr(Num)
                ))
            ),
            Or(
                Obj(
                    "dataModels", Arr(Obj(
                        "status", OrVal("extract", "direct"),
                        "relatedDataConnectionIds", Arr(Num)
                    ))
                ),
                Obj(
                    "dataModels", Arr(Obj(
                        "status", OrVal("extract", "direct"),
                        "relatedTableExtractIds", Arr(Num)
                    ))
                )
            )
        );

        CheckedValue after = checker.check(data);
        System.out.println(parseError(after.getPath()));
    }
}
