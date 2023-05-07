package hu.mobilalk.allaskereso.model;

public class JobApplication {
    private String jobId;
    private String seekerId;

    public JobApplication(String jobId, String seekerId) {
        this.jobId = jobId;
        this.seekerId = seekerId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getSeekerId() {
        return seekerId;
    }
}
