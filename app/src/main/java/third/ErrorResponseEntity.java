package third;

/**
 * Created by yancongxian on 2017/6/1.
 * 错误返回Entity封装
 */

public class ErrorResponseEntity {
    public static final String ACTION_LOCAL = "local";
    public String action;
    public String errorMsg;

    public ErrorResponseEntity(String action, String errorMsg) {
        this.action = action;
        this.errorMsg = errorMsg;
    }

    public ErrorResponseEntity(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ErrorResponseEntity{" +
                "action='" + action + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
