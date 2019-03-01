package third.wx;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Kale
 * @date 2016/3/30
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({SsoLoginType.WEIXIN, SsoLoginType.WEIBO, SsoLoginType.QQ,SsoLoginType.ALIPAY})
public @interface SsoLoginType {

    String QQ = "QQ", WEIBO = "WEIBO", WEIXIN = "WEIXIN",ALIPAY="ALIPAY";
}
