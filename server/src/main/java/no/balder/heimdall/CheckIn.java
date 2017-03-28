package no.balder.heimdall;

/**
 * @author steinar
 *         Date: 26.12.2016
 *         Time: 15.14
 */
public class CheckIn {

    String uuid;
    String latitude;
    String longitude;
    String timeStamp;

    public CheckIn() {
    }

    public CheckIn(String uuid, String latitude, String longitude, String timeStamp) {
        this.uuid = uuid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckIn checkIn = (CheckIn) o;

        if (!uuid.equals(checkIn.uuid)) return false;
        if (!latitude.equals(checkIn.latitude)) return false;
        if (!longitude.equals(checkIn.longitude)) return false;
        return timeStamp.equals(checkIn.timeStamp);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + timeStamp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CheckIn{");
        sb.append("id='").append(uuid).append('\'');
        sb.append(", latitude='").append(latitude).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append(", timeStamp='").append(timeStamp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
