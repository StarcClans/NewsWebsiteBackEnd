package lt.ca.javau11.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ArticleEntity {
    @Id
    private String uri;
    private String section;
    private String subsection;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String abstractText;
    private String url;
    private String shortUrl;
    private String byline;
    private String thumbnailStandard;
    private String itemType;
    private String source;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private String materialTypeFacet;
    private String kicker;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String headline;

	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String desFacet;
	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String orgFacet;
	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String perFacet;
	@Column(columnDefinition = "TEXT", length = 4000) //Adjust Length
	private String geoFacet;
    private String blogName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String relatedUrls;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String multimedia;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean favorite;

    private Long userId;



	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getByline() {
		return byline;
	}
	public void setByline(String byline) {
		this.byline = byline;
	}
	public String getThumbnailStandard() {
		return thumbnailStandard;
	}
	public void setThumbnailStandard(String thumbnailStandard) {
		this.thumbnailStandard = thumbnailStandard;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getMaterialTypeFacet() {
		return materialTypeFacet;
	}
	public void setMaterialTypeFacet(String materialTypeFacet) {
		this.materialTypeFacet = materialTypeFacet;
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
	public String getDesFacet() {
		return desFacet;
	}
	public void setDesFacet(String desFacet) {
		this.desFacet = desFacet;
	}
	public String getOrgFacet() {
		return orgFacet;
	}
	public void setOrgFacet(String orgFacet) {
		this.orgFacet = orgFacet;
	}
	public String getPerFacet() {
		return perFacet;
	}
	public void setPerFacet(String perFacet) {
		this.perFacet = perFacet;
	}
	public String getGeoFacet() {
		return geoFacet;
	}
	public void setGeoFacet(String geoFacet) {
		this.geoFacet = geoFacet;
	}
	public String getBlogName() {
		return blogName;
	}
	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}
	public String getRelatedUrls() {
		return relatedUrls;
	}
	public void setRelatedUrls(String relatedUrls) {
		this.relatedUrls = relatedUrls;
	}
	public String getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
}