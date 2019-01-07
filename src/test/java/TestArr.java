import java.util.List;
import java.util.Map;

import static chkr.C.*;
import static chkr.T.*;
import static util.ObjChkrUtil.parseError;

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

        var after = chkr.check(data);
        System.out.println(parseError(after.getPath()));
    }
}
