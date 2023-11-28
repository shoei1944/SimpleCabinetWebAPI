package pro.gravit.simplecabinet.web.model;

import pro.gravit.simplecabinet.web.model.user.User;

import javax.persistence.*;

@Entity(name = "NewsComment")
@Table(name = "news_comments")
public class NewsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_comments_generator")
    @SequenceGenerator(name = "news_comments_generator", sequenceName = "news_comments_seq", allocationSize = 1)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String text;

    public long getId() {
        return id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
