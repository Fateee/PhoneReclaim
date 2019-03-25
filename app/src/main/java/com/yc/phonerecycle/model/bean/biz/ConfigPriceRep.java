package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.util.List;


public class ConfigPriceRep extends BaseRep {
    /**
     * data : {"configPriceSystemVOs":[{"childs":[{"childs":[{}],"code":"","codeName":"","id":"","name":"","price":"","priceConfigId":"","type":""}],"code":"","codeName":"","id":"","name":"","price":"","priceConfigId":"","type":""}],"hardwarePriceSystemVO":{"battery":0,"batteryType":0,"bluetooth":0,"bluetoothType":0,"call":0,"callType":0,"camera":0,"cameraType":0,"compass":0,"compassType":0,"comprehensionAids":0,"comprehensionAidsType":0,"fingerprint":0,"fingerprintType":0,"flashlight":0,"flashlightType":0,"gravitySensor":0,"gravitySensorType":0,"id":"","lightSensor":0,"lightSensorType":0,"location":0,"locationType":0,"loudspeaker":0,"loudspeakerType":0,"microphone":0,"microphoneType":0,"multiTouch":0,"multiTouchType":0,"priceConfig":"","proximitySenso":0,"proximitySensoType":0,"screen":0,"screenType":0,"spiritLevel":0,"spiritLevelType":0,"vibrator":0,"vibratorType":0,"wirelessNetwork":0,"wirelessNetworkType":0},"id":"","name":"","remark":""}
     */

    public DataBean data;


    public static class DataBean extends BaseBean {
        /**
         * configPriceSystemVOs : [{"childs":[{"childs":[{}],"code":"","codeName":"","id":"","name":"","price":"","priceConfigId":"","type":""}],"code":"","codeName":"","id":"","name":"","price":"","priceConfigId":"","type":""}]
         * hardwarePriceSystemVO : {"battery":0,"batteryType":0,"bluetooth":0,"bluetoothType":0,"call":0,"callType":0,"camera":0,"cameraType":0,"compass":0,"compassType":0,"comprehensionAids":0,"comprehensionAidsType":0,"fingerprint":0,"fingerprintType":0,"flashlight":0,"flashlightType":0,"gravitySensor":0,"gravitySensorType":0,"id":"","lightSensor":0,"lightSensorType":0,"location":0,"locationType":0,"loudspeaker":0,"loudspeakerType":0,"microphone":0,"microphoneType":0,"multiTouch":0,"multiTouchType":0,"priceConfig":"","proximitySenso":0,"proximitySensoType":0,"screen":0,"screenType":0,"spiritLevel":0,"spiritLevelType":0,"vibrator":0,"vibratorType":0,"wirelessNetwork":0,"wirelessNetworkType":0}
         * id :
         * name :
         * remark :
         */

        public HardwarePriceSystemVOBean hardwarePriceSystemVO;
        public String id;
        public String name;
        public String remark;
        public List<ConfigPriceSystemVOsBean> configPriceSystemVOs;


        public static class HardwarePriceSystemVOBean extends BaseBean{
            /**
             * battery : 0
             * batteryType : 0
             * bluetooth : 0
             * bluetoothType : 0
             * call : 0
             * callType : 0
             * camera : 0
             * cameraType : 0
             * compass : 0
             * compassType : 0
             * comprehensionAids : 0
             * comprehensionAidsType : 0
             * fingerprint : 0
             * fingerprintType : 0
             * flashlight : 0
             * flashlightType : 0
             * gravitySensor : 0
             * gravitySensorType : 0
             * id :
             * lightSensor : 0
             * lightSensorType : 0
             * location : 0
             * locationType : 0
             * loudspeaker : 0
             * loudspeakerType : 0
             * microphone : 0
             * microphoneType : 0
             * multiTouch : 0
             * multiTouchType : 0
             * priceConfig :
             * proximitySenso : 0
             * proximitySensoType : 0
             * screen : 0
             * screenType : 0
             * spiritLevel : 0
             * spiritLevelType : 0
             * vibrator : 0
             * vibratorType : 0
             * wirelessNetwork : 0
             * wirelessNetworkType : 0
             */

            public int battery;
            public int batteryType;
            public int bluetooth;
            public int bluetoothType;
            public int call;
            public int callType;
            public int camera;
            public int cameraType;
            public int compass;
            public int compassType;
            public int comprehensionAids;
            public int comprehensionAidsType;
            public int fingerprint;
            public int fingerprintType;
            public int flashlight;
            public int flashlightType;
            public int gravitySensor;
            public int gravitySensorType;
            public String id;
            public int lightSensor;
            public int lightSensorType;
            public int location;
            public int locationType;
            public int loudspeaker;
            public int loudspeakerType;
            public int microphone;
            public int microphoneType;
            public int multiTouch;
            public int multiTouchType;
            public String priceConfig;
            public int proximitySenso;
            public int proximitySensoType;
            public int screen;
            public int screenType;
            public int spiritLevel;
            public int spiritLevelType;
            public int vibrator;
            public int vibratorType;
            public int wirelessNetwork;
            public int wirelessNetworkType;

        }

        public static class ConfigPriceSystemVOsBean extends BaseBean{
            /**
             * childs : [{"childs":[{}],"code":"","codeName":"","id":"","name":"","price":"","priceConfigId":"","type":""}]
             * code :
             * codeName :
             * id :
             * name :
             * price :
             * priceConfigId :
             * type :
             */

            
            public String code;
            public String codeName;
            public String id;
            public String name;
            public String price;
            public String priceConfigId;
            public String type;
            public List<ChildsBeanX> childs;


            public static class ChildsBeanX extends BaseBean{
                /**
                 * childs : [{}]
                 * code :
                 * codeName :
                 * id :
                 * name :
                 * price :
                 * priceConfigId :
                 * type :
                 */

                
                public String code;
                public String codeName;
                public String id;
                public String name;
                public String price;
                public String priceConfigId;
                public String type;
                public List<ChildsBean> childs;


                public static class ChildsBean extends BaseBean{
                }
            }
        }
    }
}
