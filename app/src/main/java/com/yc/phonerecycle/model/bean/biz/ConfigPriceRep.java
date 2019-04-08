package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.util.ArrayList;
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
        public String goodsId;
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
            public Integer batteryType;
            public int bluetooth;
            public Integer bluetoothType;
            public int call;
            public Integer callType;
            public int camera;
            public Integer cameraType;
            public int compass;
            public Integer compassType;
            public int comprehensionAids;
            public Integer comprehensionAidsType;
            public int fingerprint;
            public Integer fingerprintType;
            public int flashlight;
            public Integer flashlightType;
            public int gravitySensor;
            public Integer gravitySensorType;
            public String id;
            public int lightSensor;
            public Integer lightSensorType;
            public int location;
            public Integer locationType;
            public int loudspeaker;
            public Integer loudspeakerType;
            public int microphone;
            public Integer microphoneType;
            public int multiTouch;
            public Integer multiTouchType;
            public String priceConfig;
            public int proximitySenso;
            public Integer proximitySensoType;
            public int screen;
            public Integer screenType;
            public int spiritLevel;
            public Integer spiritLevelType;
            public int vibrator;
            public Integer vibratorType;
            public int wirelessNetwork;
            public Integer wirelessNetworkType;

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
            public List<ChildsBeanX> childs = new ArrayList<>();


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

        public void addHardwareToConfigList() {
            if (configPriceSystemVOs == null) configPriceSystemVOs = new ArrayList<ConfigPriceSystemVOsBean>();
            if (hardwarePriceSystemVO != null) {
                if (hardwarePriceSystemVO.bluetoothType != null) {
                    addOneHandCheckOption("蓝牙故障","13");
                }
                if (hardwarePriceSystemVO.callType != null) {
                    addOneHandCheckOption("通话故障","14");
                }
                if (hardwarePriceSystemVO.cameraType != null) {
                    addOneHandCheckOption("摄像头故障","15");
                }
                if (hardwarePriceSystemVO.compassType != null) {
                    addOneHandCheckOption("指南针故障","16");
                }
                if (hardwarePriceSystemVO.comprehensionAidsType != null) {
                    addOneHandCheckOption("语言助手故障","17");
                }
                if (hardwarePriceSystemVO.fingerprintType != null) {
                    addOneHandCheckOption("指纹有无故障","18");
                }
                if (hardwarePriceSystemVO.flashlightType != null) {
                    addOneHandCheckOption("闪光灯故障","19");
                }
                if (hardwarePriceSystemVO.gravitySensorType != null) {
                    addOneHandCheckOption("重力感应器故障","20");
                }
                if (hardwarePriceSystemVO.lightSensorType != null) {
                    addOneHandCheckOption("光线感应器故障","21");
                }
                if (hardwarePriceSystemVO.locationType != null) {
                    addOneHandCheckOption("定位故障","22");
                }
                if (hardwarePriceSystemVO.loudspeakerType != null) {
                    addOneHandCheckOption("扬声器故障","23");
                }
                if (hardwarePriceSystemVO.microphoneType != null) {
                    addOneHandCheckOption("麦克风故障","24");
                }
                if (hardwarePriceSystemVO.multiTouchType != null) {
                    addOneHandCheckOption("屏幕触控故障","25");
                }
                if (hardwarePriceSystemVO.proximitySensoType != null) {
                    addOneHandCheckOption("距离感应器故障","26");
                }
                if (hardwarePriceSystemVO.screenType != null) {
                    addOneHandCheckOption("屏幕显示故障","27");
                }
                if (hardwarePriceSystemVO.spiritLevelType != null) {
                    addOneHandCheckOption("水平仪故障","28");
                }
                if (hardwarePriceSystemVO.vibratorType != null) {
                    addOneHandCheckOption("振动器故障","29");
                }
                if (hardwarePriceSystemVO.wirelessNetworkType != null) {
                    addOneHandCheckOption("WIFI故障","30");
                }
            }
        }

        private void addOneHandCheckOption(String name, String code) {
            ConfigPriceSystemVOsBean tmp = new ConfigPriceSystemVOsBean();
            tmp.name = name;
            tmp.code = code;
            List<ConfigPriceSystemVOsBean.ChildsBeanX> childs = new ArrayList<>();
            ConfigPriceSystemVOsBean.ChildsBeanX tempOne = new ConfigPriceSystemVOsBean.ChildsBeanX();
            tempOne.code=code;
            tempOne.id="0";
            tempOne.name="无故障";
            ConfigPriceSystemVOsBean.ChildsBeanX tempTwo = new ConfigPriceSystemVOsBean.ChildsBeanX();
            tempTwo.code=code;
            tempTwo.id="1";
            tempTwo.name="有故障";
            childs.add(tempOne);
            childs.add(tempTwo);
            tmp.childs.addAll(childs);
            configPriceSystemVOs.add(tmp);
        }
    }
}
