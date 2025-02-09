package lt.ca.javau11.models;

 import lombok.Getter;
 import lombok.Setter;

 import java.util.List;

 @Getter
 @Setter
 public class Article {
     private String section;
     private String subsection;
     private String title;
     private String abstractText;
     private String uri;
     private String url;
     private String short_url;
     private String byline;
     private String thumbnail_standard;
     private String item_type;
     private String source;
     private String updated_date;
     private String created_date;
     private String published_date;
     private String material_type_facet;
     private String kicker;
     private String headline;
     private List<String> des_facet;
     private List<String> org_facet;
     private List<String> per_facet;
     private List<String> geo_facet;
     private String blog_name;
     private List<RelatedUrl> related_urls;
     private List<Multimedia> multimedia;


     public String getSection() {
         return section;
     }

     public void setSection(String section) {
         this.section = section;
     }

     public String getSubsection() {
         return subsection;
     }

     public void setSubsection(String subsection) {
         this.subsection = subsection;
     }

     public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public String getAbstractText() {
         return abstractText;
     }

     public void setAbstractText(String abstractText) {
         this.abstractText = abstractText;
     }

     public String getUri() {
         return uri;
     }

     public void setUri(String uri) {
         this.uri = uri;
     }

     public String getUrl() {
         return url;
     }

     public void setUrl(String url) {
         this.url = url;
     }

     public String getShort_url() {
         return short_url;
     }

     public void setShort_url(String short_url) {
         this.short_url = short_url;
     }

     public String getByline() {
         return byline;
     }

     public void setByline(String byline) {
         this.byline = byline;
     }

     public String getThumbnail_standard() {
         return thumbnail_standard;
     }

     public void setThumbnail_standard(String thumbnail_standard) {
         this.thumbnail_standard = thumbnail_standard;
     }

     public String getItem_type() {
         return item_type;
     }

     public void setItem_type(String item_type) {
         this.item_type = item_type;
     }

     public String getSource() {
         return source;
     }

     public void setSource(String source) {
         this.source = source;
     }

     public String getUpdated_date() {
         return updated_date;
     }

     public void setUpdated_date(String updated_date) {
         this.updated_date = updated_date;
     }

     public String getCreated_date() {
         return created_date;
     }

     public void setCreated_date(String created_date) {
         this.created_date = created_date;
     }

     public String getPublished_date() {
         return published_date;
     }

     public void setPublished_date(String published_date) {
         this.published_date = published_date;
     }

     public String getMaterial_type_facet() {
         return material_type_facet;
     }

     public void setMaterial_type_facet(String material_type_facet) {
         this.material_type_facet = material_type_facet;
     }

     public String getKicker() {
         return kicker;
     }

     public void setKicker(String kicker) {
         this.kicker = kicker;
     }

     public String getHeadline() {
         return headline;
     }

     public void setHeadline(String headline) {
         this.headline = headline;
     }

     public List<String> getDes_facet() {
         return des_facet;
     }

     public void setDes_facet(List<String> des_facet) {
         this.des_facet = des_facet;
     }

     public List<String> getOrg_facet() {
         return org_facet;
     }

     public void setOrg_facet(List<String> org_facet) {
         this.org_facet = org_facet;
     }

     public List<String> getPer_facet() {
         return per_facet;
     }

     public void setPer_facet(List<String> per_facet) {
         this.per_facet = per_facet;
     }

     public List<String> getGeo_facet() {
         return geo_facet;
     }

     public void setGeo_facet(List<String> geo_facet) {
         this.geo_facet = geo_facet;
     }

     public String getBlog_name() {
         return blog_name;
     }

     public void setBlog_name(String blog_name) {
         this.blog_name = blog_name;
     }

     public List<RelatedUrl> getRelated_urls() {
         return related_urls;
     }

     public void setRelated_urls(List<RelatedUrl> related_urls) {
         this.related_urls = related_urls;
     }

     public List<Multimedia> getMultimedia() {
         return multimedia;
     }

     public void setMultimedia(List<Multimedia> multimedia) {
         this.multimedia = multimedia;
     }
 }

 @Getter
 @Setter
 class RelatedUrl {
     private String suggested_link_text;
     private String url;

     public String getSuggested_link_text() {
         return suggested_link_text;
     }

     public void setSuggested_link_text(String suggested_link_text) {
         this.suggested_link_text = suggested_link_text;
     }

     public String getUrl() {
         return url;
     }

     public void setUrl(String url) {
         this.url = url;
     }
 }

 @Getter
 @Setter
 class Multimedia {
     private String url;
     private String format;
     private int height;
     private int width;
     private String type;
     private String subtype;
     private String caption;
     private String copyright;

     public String getUrl() {
         return url;
     }

     public void setUrl(String url) {
         this.url = url;
     }

     public String getFormat() {
         return format;
     }

     public void setFormat(String format) {
         this.format = format;
     }

     public int getHeight() {
         return height;
     }

     public void setHeight(int height) {
         this.height = height;
     }

     public int getWidth() {
         return width;
     }

     public void setWidth(int width) {
         this.width = width;
     }

     public String getType() {
         return type;
     }

     public void setType(String type) {
         this.type = type;
     }

     public String getSubtype() {
         return subtype;
     }

     public void setSubtype(String subtype) {
         this.subtype = subtype;
     }

     public String getCaption() {
         return caption;
     }

     public void setCaption(String caption) {
         this.caption = caption;
     }

     public String getCopyright() {
         return copyright;
     }

     public void setCopyright(String copyright) {
         this.copyright = copyright;
     }
 }