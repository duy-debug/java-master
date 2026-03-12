package org.example.ntucms.models;

public class Page {
  private String id, pageName, keyword, content, parentPage;

  public Page(String id, String pageName, String keyword, String content, String parentPage) {
    this.id = id;
    this.pageName = pageName;
    this.keyword = keyword;
    this.content = content;
    this.parentPage = parentPage;
  }

  public Page() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getParentPage() {
    return parentPage;
  }

  public void setParentPage(String parentPage) {
    this.parentPage = parentPage;
  }
}
