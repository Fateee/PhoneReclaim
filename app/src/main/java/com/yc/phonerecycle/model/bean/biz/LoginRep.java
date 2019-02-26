package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;

/**
 * Describe：用户信息
 */
public class LoginRep extends BaseRep implements Serializable {

    /**
     * code : 0
     * info : 操作完成
     * data : {"token":"Banner eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwZXJmb3JtZXIiLCJVU0VSIjoie1wiaWRcIjpcIjFcIixcImxvY2tlZFwiOnRydWUsXCJwaG9uZVwiOlwiMTU2MDAwMDAwMDBcIixcInJvbGVJZFwiOlwiMVwiLFwidHlwZVwiOlwiMVwiLFwidHlwZU5hbWVcIjpcIueUqOaIt1wiLFwidXNlck5hbWVcIjpcImFkbWluXCJ9IiwiZXhwIjoxNTUxMDE3OTMwLCJpYXQiOjE1NTA5ODE5MzB9.WZ-jwj_8J5XMDg8tH4oLLPgxjGSeCdvlxkBLCrrd4Vg","userInfoVO":{"id":"1","userName":"admin","locked":true,"phone":"15600000000","roleId":"1","type":"1","typeName":"用户"}}
     */
    private static final long serialVersionUID = 4125096758372084309L;

    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = 4125096758372084319L;
        /**
         * token : Banner eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwZXJmb3JtZXIiLCJVU0VSIjoie1wiaWRcIjpcIjFcIixcImxvY2tlZFwiOnRydWUsXCJwaG9uZVwiOlwiMTU2MDAwMDAwMDBcIixcInJvbGVJZFwiOlwiMVwiLFwidHlwZVwiOlwiMVwiLFwidHlwZU5hbWVcIjpcIueUqOaIt1wiLFwidXNlck5hbWVcIjpcImFkbWluXCJ9IiwiZXhwIjoxNTUxMDE3OTMwLCJpYXQiOjE1NTA5ODE5MzB9.WZ-jwj_8J5XMDg8tH4oLLPgxjGSeCdvlxkBLCrrd4Vg
         * userInfoVO : {"id":"1","userName":"admin","locked":true,"phone":"15600000000","roleId":"1","type":"1","typeName":"用户"}
         */

        public String token;
        public UserInfoVOBean userInfoVO;

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
             * id : 1
             * userName : admin
             * locked : true
             * phone : 15600000000
             * roleId : 1
             * type : 1
             * typeName : 用户
             */

            public String id;
            public String userName;
            public boolean locked;
            public String phone;
            public String roleId;
            public String type;//1、用户/2、商家/3、门店/4、店员
            public String typeName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
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
        }
    }
}
