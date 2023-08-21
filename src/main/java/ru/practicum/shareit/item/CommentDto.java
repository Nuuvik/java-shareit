package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.config.Create;
import ru.practicum.shareit.config.Update;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @NotBlank(groups = {Create.class, Update.class})
    private String text;

    private Item item;

    private User author;

    private LocalDateTime created = LocalDateTime.now();

    private String authorName;
}