package az.eurodesign.notification.processing.payload;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.ToString;

@JacksonXmlRootElement(localName = "response")
@ToString
public class BulksSmsResponse {

    private Head head;

    @JacksonXmlProperty(localName = "body")
    @JacksonXmlElementWrapper(useWrapping = false)
    private MainSmsResponseBody[] body;

    public BulksSmsResponse() {
    }

    public BulksSmsResponse(Head head, MainSmsResponseBody[] body) {
        this.head = head;
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public MainSmsResponseBody[] getBody() {
        return body;
    }

    public void setBody(MainSmsResponseBody[] body) {
        this.body = body;
    }

    @ToString
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

    }
    @ToString
    public static class MainSmsResponseBody {

        private String taskid;
        public MainSmsResponseBody() {
        }

        public MainSmsResponseBody(String taskid) {
            this.taskid = taskid;
        }

        public String getTaskid() {
            return taskid;
        }

        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }
    }


}
