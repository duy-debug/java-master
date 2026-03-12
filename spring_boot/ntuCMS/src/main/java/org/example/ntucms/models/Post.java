package org.example.ntucms.models;

public class Post {
  private String id, title, content, categoryId;

  public Post(String id, String title, String content, String categoryId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.categoryId = categoryId;
  }

  public Post() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }
}
