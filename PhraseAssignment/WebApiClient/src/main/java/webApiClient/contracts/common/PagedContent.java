package webApiClient.contracts.common;

import lombok.Getter;

import java.util.List;

@Getter
public class PagedContent<T> {
    private int totalElements;
    private int totalPages;
    private int pageSize;
    private int pageNumber;
    private int numberOfElements;
    private List<T> content;
}
