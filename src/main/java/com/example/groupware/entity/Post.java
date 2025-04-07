    package com.example.groupware.entity;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;

    @Entity
    @Getter
    @NoArgsConstructor
    @Table(name = "posts")
    public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String content;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private PostType postType;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id", nullable = false)  // user_id를 author_id로 바꿨습니다.
        private User author; // 작성자 (author)

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Post(String title, String content, PostType postType, User author) {
            this.title = title;
            this.content = content;
            this.postType = postType;
            this.author = author;
        }

        // 수정 메소드
        public void update(String title, String content) {
            this.title = title;
            this.content = content;
            this.updatedAt = LocalDateTime.now();
        }

        @PrePersist
        public void prePersist() {
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        public void preUpdate() {
            this.updatedAt = LocalDateTime.now();
        }
    }


