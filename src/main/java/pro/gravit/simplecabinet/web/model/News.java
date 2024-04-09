package pro.gravit.simplecabinet.web.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "News")
@NamedEntityGraphs(value = {
        @NamedEntityGraph(
                name = News.WITH_COMMENTS,
                attributeNodes = @NamedAttributeNode("comments")
        )
})
@Table(name = "news")
public class News {
    public static final String WITH_COMMENTS = "News[comments]";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_generator")
    @SequenceGenerator(name = "news_generator", sequenceName = "news_seq", allocationSize = 1)
    private long id;
    private String header;
    @Column(name = "mini_text")
    private String miniText;
    private String text;
    private int commentsCount;
    private String pictureURL;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NewsComment> comments;

    public long getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getPicture () {
        return pictureURL;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setPicture (String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getMiniText() {
        return miniText;
    }

    public void setMiniText(String miniText) {
        this.miniText = miniText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<NewsComment> getComments() {
        return comments;
    }

    public void setComments(List<NewsComment> comments) {
        this.comments = comments;
    }
}
