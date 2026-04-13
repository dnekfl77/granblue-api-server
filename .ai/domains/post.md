# Domain Context: Post (게시판)

## `Post` Entity
- **Fields**: id, title, content, author (User와 ManyToOne 매핑)

## `PostRepository`
```java
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.author ORDER BY p.createdAt DESC")
    List<Post> findAllWithAuthorOrderByCreatedAtDesc();
}
```

## MapStruct Mapper (`PostMapper`)
```java
@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "author.name", target = "authorName")
    PostResponse toResponse(Post post);
}
```

## `PostController` & `PostService`
- **POST `/api/v1/posts`**: `PostCreateRequest` (title, content) 입력. `Authentication` 객체에서 email(`authentication.getName()`)을 꺼내 작성자로 지정하여 글 생성.
- **GET `/api/v1/posts`**: `PostMapper`를 이용해 엔티티를 `PostResponse` DTO로 변환하여 목록 반환.