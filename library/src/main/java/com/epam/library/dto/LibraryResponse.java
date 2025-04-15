package com.epam.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponse {
    private Long id;
    private String username ;
    private List<Long> bookId;
}
