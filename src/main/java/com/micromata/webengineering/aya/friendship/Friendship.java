package com.micromata.webengineering.aya.friendship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.micromata.webengineering.aya.chat.Chat;
import com.micromata.webengineering.aya.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Friendship {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private User source;

    @ManyToOne(optional = false)
    private User destination;
    private int status;
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats;

    public Friendship() {
        // Default constructor for JPA.
        chats = new LinkedList<>();
    }

    /**
     * Constructor for Friendship's CrudRepository (findAll).
     *
     * @param source
     * @param destination
     * @param status
     * @param createdAt
     */
    public Friendship(Long id, User source, User destination, int status, Date createdAt) {
        this.id = id;
        this.source = source;
        this.setDestination(destination);
        this.status = status;
        this.createdAt = createdAt;
        chats = new LinkedList<>();
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public int getStatus() {
        return status;
    }


    public Date getCreatedAt() {
        return createdAt;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }

    /**
     * This method is called before an entity is persisted in the database. This is in contrast to our previous
     * approach where an object's createdAt depends on the date of its instantiation.
     * <p>
     * Information about @PrePersist where found by using the search terms "jpa annotations createdat".
     */
    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", source=" + source +
                 ", destination=" + destination +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

	public User getDestination() {
		return destination;
	}

	public void setDestination(User destination) {
		this.destination = destination;
	}
}
