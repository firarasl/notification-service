package az.eurodesign.notification.processing.payload;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "request")
public class SmsReportsRequest {
    private Head head;

    public SmsReportsRequest() {
    }

    public SmsReportsRequest(Head head) {
        this.head = head;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }


    public static class Head {
        private String operation;

        private String login;

        private String password;

        private String taskid;
        public Head() {
        }

        public Head(String operation, String login, String password, String taskid) {
            this.operation = operation;
            this.login = login;
            this.password = password;
            this.taskid = taskid;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTaskid() {
            return taskid;
        }

        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }
    }



    }
