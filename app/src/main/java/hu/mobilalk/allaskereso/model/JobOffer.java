package hu.mobilalk.allaskereso.model;

public class JobOffer {
    private String id;
    private String name;
    private String employerName;
    private String description;
    private boolean applied = false;

    public JobOffer(String id, String name, String employerName, String description, boolean applied) {
        this.id = id;
        this.name = name;
        this.employerName = employerName;
        this.description = description;
        this.applied = applied;
    }

    public String getName() {
        return name;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isApplied() {
        return applied;
    }

    public String getId() {
        return id;
    }
}
