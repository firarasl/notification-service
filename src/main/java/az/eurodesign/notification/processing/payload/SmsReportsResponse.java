package az.eurodesign.notification.processing.payload;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "response")
public class SmsReportsResponse {
    private Head head;

    //    @JacksonXmlProperty(localName = "body")
//    @JacksonXmlElementWrapper(useWrapping = false)
    private Body body;

    public SmsReportsResponse() {
    }

    public SmsReportsResponse(Head head, Body body) {
        this.head = head;
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Head {

        private String responsecode;

        public Head() {
        }

        public String getResponsecode() {
            return responsecode;
        }

        public void setResponsecode(String responsecode) {
            this.responsecode = responsecode;
        }

        public Head(String responsecode) {
            this.responsecode = responsecode;
        }
    }

    public static class Body {

        private int pending;
        private int delivered;
        private int failed;
        private int removed;
        private int error;

        public Body() {
        }

        public Body(int pending, int delivered, int failed, int removed, int error) {
            this.pending = pending;
            this.delivered = delivered;
            this.failed = failed;
            this.removed = removed;
            this.error = error;
        }

        public int getPending() {
            return pending;
        }

        public void setPending(int pending) {
            this.pending = pending;
        }

        public int getDelivered() {
            return delivered;
        }

        public void setDelivered(int delivered) {
            this.delivered = delivered;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }

        public int getRemoved() {
            return removed;
        }

        public void setRemoved(int removed) {
            this.removed = removed;
        }

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }
    }
}
