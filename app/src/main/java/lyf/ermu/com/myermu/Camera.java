package lyf.ermu.com.myermu;

/**
 * Created by Administrator on 2016/3/30.
 */
public class Camera {
    private String request_id;
    private String count;
    private String deviceid;
    private String stream_id;
    private String status;
    private String description;
    private String thumbnail;

    public Camera(String request_id, String count, String deviceid, String stream_id, String status, String description, String thumbnail) {
        this.stream_id = request_id;
        this.count = count;
        this.deviceid = deviceid;
        this.request_id = request_id;
        this.status = status;
        this.description = description;
        this.thumbnail = thumbnail;

    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStream_id() {
        return stream_id;
    }

    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "request_id='" + request_id + '\'' +
                ", count='" + count + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", stream_id='" + stream_id + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
