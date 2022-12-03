package webApiClient.contracts.common;

import lombok.Getter;

@Getter
public class PagedContent<T> {
    private int totalElements;
    private int totalPages;
    private int pageSize;
    private int pageNumber;
    private int numberOfElements;
    private T[] content;
}
