package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;
import java.util.List;

/**
 * Describe：用户信息
 */
public class LoginRep extends BaseRep implements Serializable {

    /**
     * data : {"token":"","userInfoVO":{"id":"","locked":true,"permissionsVOS":[{"id":"","name":""}],"phone":"","roleId":"","type":"","typeName":"","userName":""}}
     */

    public DataBean data;

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = 4125096758372084319L;
        /**
         * token :
         * userInfoVO : {"id":"","locked":true,"permissionsVOS":[{"id":"","name":""}],"phone":"","roleId":"","type":"","typeName":"","userName":""}
         */

        private String token;
        private UserInfoVOBean userInfoVO;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserInfoVOBean getUserInfoVO() {
            return userInfoVO;
        }

        public void setUserInfoVO(UserInfoVOBean userInfoVO) {
            this.userInfoVO = userInfoVO;
        }

        public static class UserInfoVOBean implements Serializable{
            private static final long serialVersionUID = 4125096758372084333L;
            /**
             * id :
             * locked : true
             * permissionsVOS : [{"id":"","name":""}]
             * phone :
             * roleId :
             * type :
             * typeName :
             * userName :
             */

            private String id;
            private boolean locked;
            private String phone;
            private String roleId;
            private String type;
            private String typeName;
            private String userName;
            private List<PermissionsVOSBean> permissionsVOS;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isLocked() {
                return locked;
            }

            public void setLocked(boolean locked) {
                this.locked = locked;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRoleId() {
                return roleId;
            }

            public void setRoleId(String roleId) {
                this.roleId = roleId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public List<PermissionsVOSBean> getPermissionsVOS() {
                return permissionsVOS;
            }

            public void setPermissionsVOS(List<PermissionsVOSBean> permissionsVOS) {
                this.permissionsVOS = permissionsVOS;
            }

            public static class PermissionsVOSBean implements Serializable {
                private static final long serialVersionUID = 4125096758375034334L;
                /**
                 * id :
                 * name :
                 */

                private String id;
                private String name;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
