package com.yc.phonerecycle.model.bean.biz;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ConfigPriceTempRep {

    /**
     * id : 307164482558365696
     * name : 华为估价体系
     * remark : 华为估价体系 涉及到 Meta、荣耀系列和Nova
     * configPriceSystemVOs : [{"id":"","name":"网络制式","priceConfigId":"","childs":[{"id":"307164482575142913","name":"全网通","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"4","codeName":"网络制式","type":"0"},{"id":"307164482604503040","name":"联通","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"4","codeName":"网络制式","type":"0"}],"price":"","code":"4","codeName":"网络制式","type":""},{"id":"","name":"区域","priceConfigId":"","childs":[{"id":"307164482629668864","name":"大陆","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"1","codeName":"区域","type":"0"},{"id":"307164482638057472","name":"美版","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"1","codeName":"区域","type":"0"}],"price":"","code":"1","codeName":"区域","type":""},{"id":"","name":"内存","priceConfigId":"","childs":[{"id":"307164482654834689","name":"64G","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"2","codeName":"内存","type":"0"},{"id":"307164482667417600","name":"128G","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"2","codeName":"内存","type":"0"}],"price":"","code":"2","codeName":"内存","type":""},{"id":"","name":"颜色","priceConfigId":"","childs":[{"id":"307164482680000513","name":"红色","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"5","codeName":"颜色","type":"0"},{"id":"307164482692583424","name":"黑色","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"5","codeName":"颜色","type":"0"}],"price":"","code":"5","codeName":"颜色","type":""},{"id":"","name":"存储空间","priceConfigId":"","childs":[{"id":"307164482705166337","name":"128G","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"3","codeName":"存储空间","type":"0"},{"id":"307164482713554944","name":"256G","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"3","codeName":"存储空间","type":"0"}],"price":"","code":"3","codeName":"存储空间","type":""},{"id":"","name":"进水","priceConfigId":"","childs":[{"id":"307164482839384065","name":"没有进水","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"9","codeName":"进水","type":"0"},{"id":"307164482851966976","name":"有进水","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"9","codeName":"进水","type":"0"}],"price":"","code":"9","codeName":"进水","type":""},{"id":"","name":"账户锁","priceConfigId":"","childs":[{"id":"307164482872938497","name":"有账户锁","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"11","codeName":"账户锁","type":"0"},{"id":"307164482877132800","name":"没有账户锁","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"11","codeName":"账户锁","type":"0"}],"price":"","code":"11","codeName":"账户锁","type":""},{"id":"","name":"开机","priceConfigId":"","childs":[{"id":"307164482893910016","name":"不能开机","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"12","codeName":"开机","type":"0"},{"id":"307164482898104320","name":"可以开机","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"12","codeName":"开机","type":"0"}],"price":"","code":"12","codeName":"开机","type":""}]
     * hardwarePriceSystemVO : {"id":"","priceConfig":"307164482558365696","wirelessNetwork":0,"wirelessNetworkType":0,"gravitySensor":0,"gravitySensorType":0,"bluetooth":0,"bluetoothType":0,"proximitySenso":0,"proximitySensoType":0,"lightSensor":0,"lightSensorType":0,"compass":0,"compassType":0,"spiritLevel":0,"spiritLevelType":0,"location":0,"locationType":0,"call":0,"callType":0,"fingerprint":0,"fingerprintType":0,"comprehensionAids":0,"comprehensionAidsType":0,"loudspeaker":0,"loudspeakerType":0,"vibrator":0,"vibratorType":0,"microphone":0,"microphoneType":0,"camera":0,"cameraType":0,"flashlight":0,"flashlightType":0,"multiTouch":0,"multiTouchType":0,"battery":0,"batteryType":0,"screen":0,"screenType":0}
     */

    public String id;
    public String name;
    public String remark;
    public HardwarePriceSystemVOBean hardwarePriceSystemVO;
    public List<ConfigPriceSystemVOsBean> configPriceSystemVOs;


    public static class HardwarePriceSystemVOBean {
        /**
         * id :
         * priceConfig : 307164482558365696
         * wirelessNetwork : 0
         * wirelessNetworkType : 0
         * gravitySensor : 0
         * gravitySensorType : 0
         * bluetooth : 0
         * bluetoothType : 0
         * proximitySenso : 0
         * proximitySensoType : 0
         * lightSensor : 0
         * lightSensorType : 0
         * compass : 0
         * compassType : 0
         * spiritLevel : 0
         * spiritLevelType : 0
         * location : 0
         * locationType : 0
         * call : 0
         * callType : 0
         * fingerprint : 0
         * fingerprintType : 0
         * comprehensionAids : 0
         * comprehensionAidsType : 0
         * loudspeaker : 0
         * loudspeakerType : 0
         * vibrator : 0
         * vibratorType : 0
         * microphone : 0
         * microphoneType : 0
         * camera : 0
         * cameraType : 0
         * flashlight : 0
         * flashlightType : 0
         * multiTouch : 0
         * multiTouchType : 0
         * battery : 0
         * batteryType : 0
         * screen : 0
         * screenType : 0
         */

        public String id;
        public String priceConfig;
        public int wirelessNetwork;
        public int wirelessNetworkType;
        public int gravitySensor;
        public int gravitySensorType;
        public int bluetooth;
        public int bluetoothType;
        public int proximitySenso;
        public int proximitySensoType;
        public int lightSensor;
        public int lightSensorType;
        public int compass;
        public int compassType;
        public int spiritLevel;
        public int spiritLevelType;
        public int location;
        public int locationType;
        public int call;
        public int callType;
        public int fingerprint;
        public int fingerprintType;
        public int comprehensionAids;
        public int comprehensionAidsType;
        public int loudspeaker;
        public int loudspeakerType;
        public int vibrator;
        public int vibratorType;
        public int microphone;
        public int microphoneType;
        public int camera;
        public int cameraType;
        public int flashlight;
        public int flashlightType;
        public int multiTouch;
        public int multiTouchType;
        public int battery;
        public int batteryType;
        public int screen;
        public int screenType;

    }

    public static class ConfigPriceSystemVOsBean {
        /**
         * id :
         * name : 网络制式
         * priceConfigId :
         * childs : [{"id":"307164482575142913","name":"全网通","priceConfigId":"307164482558365696","childs":"","price":"1290.0","code":"4","codeName":"网络制式","type":"0"},{"id":"307164482604503040","name":"联通","priceConfigId":"307164482558365696","childs":"","price":"1270.0","code":"4","codeName":"网络制式","type":"0"}]
         * price :
         * code : 4
         * codeName : 网络制式
         * type :
         */

        public String id;
        public String name;
        public String priceConfigId;
        public String price;
        
        public String code;
        public String codeName;
        public String type;
        public List<ChildsBean> childs;


        public static class ChildsBean {
            /**
             * id : 307164482575142913
             * name : 全网通
             * priceConfigId : 307164482558365696
             * childs :
             * price : 1290.0
             * code : 4
             * codeName : 网络制式
             * type : 0
             */

            public String id;
            public String name;
            public String priceConfigId;
            public String childs;
            public String price;
            
            public String code;
            public String codeName;
            public String type;

        }
    }
}
