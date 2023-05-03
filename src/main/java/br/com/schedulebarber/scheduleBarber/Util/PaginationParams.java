package br.com.schedulebarber.scheduleBarber.Util;

public class PaginationParams {

    private int page;
    private int size;
    private String sortProperty;
    private String sortOrder;

    public PaginationParams() {
    }

    public PaginationParams(int page, int size, String sortProperty, String sortOrder) {
        this.page = page;
        this.size = size;
        this.sortProperty = sortProperty;
        this.sortOrder = sortOrder;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
