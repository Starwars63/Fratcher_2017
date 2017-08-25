package com.micromata.webengineering.aya.chat;

import com.micromata.webengineering.aya.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Chat {
    public static final int CHAT_LENGTH = 65536;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private User source;
    @ManyToOne(optional = false)
    private User destination;

    @Column(length = Chat.CHAT_LENGTH)
    private String message;
    private Date createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

       Chat chat = (Chat) o;
        return id != null ? id.equals(chat.id) : chat.id == null;
    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
        return "Chat{" +
                "id=" + id +
                ", source=" + source +
                ", destination=" + destination +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

	public User getSource() {
		return source;
	}

	public void setSource(User source) {
		this.source = source;
	}

	public User getDestination() {
		return destination;
	}

	public void setDestination(User destination) {
		this.destination = destination;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
