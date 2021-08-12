package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.NewsComment;

public class NewsCommentDto {
    public final long id;
    public final long userId;
    public final String text;

    public NewsCommentDto(NewsComment comment) {
        this.id = comment.getId();
        this.userId = 0;
        this.text = comment.getText();
    }
}
