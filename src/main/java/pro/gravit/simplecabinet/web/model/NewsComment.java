package pro.gravit.simplecabinet.web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pro.gravit.simplecabinet.web.model.user.User;

@Getter
@Entity(name = "NewsComment")
@Table(name = "news_comments")
public class NewsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_comments_generator")
    @SequenceGenerator(name = "news_comments_generator", sequenceName = "news_comments_seq", allocationSize = 1)
    private long id;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Setter
    private String text;

}
