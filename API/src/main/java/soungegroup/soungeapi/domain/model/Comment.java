package soungegroup.soungeapi.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Comment")
@Table(name = "tb_comment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id") private Long id;
    @Column(name = "comment_text") private String text;
    @Column(name = "comment_media_url") private String mediaUrl;
    @Column(name = "comment_date_time") private LocalDateTime commentDateTime;

    // Many comments belong to one post
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_fk")
    private Post post;

    // Many comments belong to one user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_fk")
    private User user;
}
