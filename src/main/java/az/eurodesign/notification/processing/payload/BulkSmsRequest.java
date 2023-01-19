package az.eurodesign.notification.processing.payload;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "request")
public class BulkSmsRequest {

    private Head head;

    @JacksonXmlProperty(localName = "body")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Body[] bodies;

    public BulkSmsRequest() {
    }

    public BulkSmsRequest(Head head, Body[] bodies) {
        this.head = head;
        this.bodies = bodies;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body[] getBodies() {
        return bodies;
    }

    public void setBodies(Body[] bodies) {
        this.bodies = bodies;
    }


    public static class Head {
        private String operation;


        private String login;

        private String password;

        private String title;

        private String scheduled;


        private String isbulk;


        private String controlid;
        public Head() {
        }

        public Head(String operation, String login, String password, String title, String scheduled, String isbulk, String controlid) {
            this.operation = operation;
            this.login = login;
            this.password = password;
            this.title = title;
            this.scheduled = scheduled;
            this.isbulk = isbulk;
            this.controlid = controlid;
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
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public String getScheduled() {
            return scheduled;
        }

        public void setScheduled(String scheduled) {
            this.scheduled = scheduled;
        }

        public String getIsbulk() {
            return isbulk;
        }
        public void setIsbulk(String isbulk) {
            this.isbulk = isbulk;
        }
        public String getControlid() {
            return controlid;
        }
        public void setControlid(String controlid) {
            this.controlid = controlid;
        }

    }

    public static class Body {

        private String msisdn;

        private String message;
        public Body() {
        }
        public Body(String msisdn, String message) {
            this.msisdn = msisdn;
            this.message = message;
        }
        public String getMsisdn() {
            return msisdn;
        }
        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }


}