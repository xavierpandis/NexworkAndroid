package com.cjx.nexwork.model;

import org.joda.time.LocalDateTime;

import java.io.Serializable;

/**
 * Created by Xavi on 29/03/2017.
 */

public class Friend implements Serializable {

    private Long id;

    private Boolean friendship;

    private LocalDateTime friendship_date;

    private User friend_from;

    private User friend_to;

    public Friend() {
    }

    public Friend(Long id, Boolean friendship, LocalDateTime friendship_date, User friend_from, User friend_to) {
        this.id = id;
        this.friendship = friendship;
        this.friendship_date = friendship_date;
        this.friend_from = friend_from;
        this.friend_to = friend_to;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFriendship() {
        return friendship;
    }

    public void setFriendship(Boolean friendship) {
        this.friendship = friendship;
    }

    public LocalDateTime getFriendship_date() {
        return friendship_date;
    }

    public void setFriendship_date(LocalDateTime friendship_date) {
        this.friendship_date = friendship_date;
    }

    public User getFriend_from() {
        return friend_from;
    }

    public void setFriend_from(User friend_from) {
        this.friend_from = friend_from;
    }

    public User getFriend_to() {
        return friend_to;
    }

    public void setFriend_to(User friend_to) {
        this.friend_to = friend_to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;

        if (id != null ? !id.equals(friend.id) : friend.id != null) return false;
        if (friendship != null ? !friendship.equals(friend.friendship) : friend.friendship != null)
            return false;
        if (friendship_date != null ? !friendship_date.equals(friend.friendship_date) : friend.friendship_date != null)
            return false;
        if (friend_from != null ? !friend_from.equals(friend.friend_from) : friend.friend_from != null)
            return false;
        return friend_to != null ? friend_to.equals(friend.friend_to) : friend.friend_to == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (friendship != null ? friendship.hashCode() : 0);
        result = 31 * result + (friendship_date != null ? friendship_date.hashCode() : 0);
        result = 31 * result + (friend_from != null ? friend_from.hashCode() : 0);
        result = 31 * result + (friend_to != null ? friend_to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", friendship=" + friendship +
                ", friendship_date=" + friendship_date +
                ", friend_from=" + friend_from +
                ", friend_to=" + friend_to +
                '}';
    }
}
