package com.mmr.learn.thread.lesson.lesson12.innerClass;

/**
 * Description: 内置类与静态内置类
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 16:42
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PublicClass {
    private String username;
    private String password;
    class PrivateClass{
        private String age;
        private String address;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void printPublicProperty(){
            System.out.println(username + " " + password);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
