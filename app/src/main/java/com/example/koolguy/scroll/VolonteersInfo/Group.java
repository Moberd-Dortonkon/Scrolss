package com.example.koolguy.scroll.VolonteersInfo;

public class Group {
    String groupType;
    String leaderName;
    String groupid;
    String groupName;
    String groupdescription;
    String groupcoordinates;

    public Group(String groupType, String leaderName, String groupid, String groupName, String groupdescription,
                 String groupcoordinates) {
        super();
        this.groupType = groupType;
        this.leaderName = leaderName;
        this.groupid = groupid;
        this.groupName = groupName;
        this.groupdescription = groupdescription;
        this.groupcoordinates = groupcoordinates;
    }
    public String getGroupType() {
        return groupType;
    }
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
    public String getLeaderName() {
        return leaderName;
    }
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    public String getGroupid() {
        return groupid;
    }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupdescription() {
        return groupdescription;
    }
    public void setGroupdescription(String groupdescription) {
        this.groupdescription = groupdescription;
    }
    public String getGroupcoordinates() {
        return groupcoordinates;
    }
    public void setGroupcoordinates(String groupcoordinates) {
        this.groupcoordinates = groupcoordinates;
    }


}